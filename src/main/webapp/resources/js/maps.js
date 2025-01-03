import regionCode from './data/regionCode.js';
import tourCode from './data/tourCode.js';

// 마커 이미지 정의 (실제 이미지 경로로 대체 필요)
const defaultMarkerImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 기본 마커 이미지
    new kakao.maps.Size(24, 35),
    { offset: new kakao.maps.Point(12, 35) }
);

const selectedMarkerImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png', // 마이리스트 마커 이미지 (노란색 등 다른 색상)
    new kakao.maps.Size(24, 35),
    { offset: new kakao.maps.Point(12, 35) }
);

let map = null;
let selectedArea = null;
let selectedSigungu = null;
let selectedType = null;
let myList = []; // 사용자가 선택한 항목

const searchMarkers = new Map(); // 검색 결과 마커 관리 Map
const myListMarkers = new Map(); // 마이리스트 마커 관리 Map

let currentInfoWindow = null;
let currentSearchItems = []; // 현재 검색 결과

document.addEventListener("DOMContentLoaded", function() {
    initMap();
    initMapEvents();
    initGlobalEventListeners(); // 글로벌 이벤트 리스너 초기화
});

// 지도 초기화 함수
function initMap() {
    const mapContainer = document.getElementById("map");
    if (!mapContainer) {
        console.error("지도 영역이 없습니다.");
        return;
    }

    const options = {
        center: new kakao.maps.LatLng(37.566, 126.978), // 서울시청 근처
        level: 8
    };
    map = new kakao.maps.Map(mapContainer, options);

    // 지도를 클릭하면 열린 인포윈도우 닫기
    kakao.maps.event.addListener(map, "click", function () {
        closeAllInfoWindows();
    });

    console.log("지도 초기화 완료");
}

// 이벤트 리스너 초기화 함수
function initMapEvents() {
    // 지역 버튼 이벤트 설정
    const areaBtns = document.querySelectorAll("[data-area]");
    areaBtns.forEach(btn => {
        btn.addEventListener("click", function() {
            selectedArea = btn.getAttribute("data-area");
            selectedSigungu = null; // 시군구 선택 초기화

            // 시군구 버튼 초기화
            const sigunguContainer = document.getElementById("sigunguButtons");
            if (sigunguContainer) {
                sigunguContainer.innerHTML = "";
            }

            renderSigunguButtons(selectedArea);
            updateSelectedFiltersText();
            console.log(`선택된 지역: ${selectedArea}`);
        });
    });

    // 타입 버튼 이벤트 설정
    const typeBtns = document.querySelectorAll("[data-type]");
    typeBtns.forEach(btn => {
        btn.addEventListener("click", function() {
            selectedType = btn.getAttribute("data-type");
         

            // 시군구 버튼 초기화
            const sigunguContainer = document.getElementById("sigunguButtons");
            if (sigunguContainer) {
            }

            updateSelectedFiltersText();
            console.log(`선택된 타입: ${selectedType}`);
        });
    });

    // 검색 버튼 이벤트 설정
    const searchBtn = document.getElementById("searchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", onSearch);
    }

    // 초기화 버튼 이벤트 설정
    const resetBtn = document.getElementById("resetBtn");
    if (resetBtn) {
        resetBtn.addEventListener("click", onReset);
    }

    // 키워드 검색 이벤트 설정
    const searchKeyword = document.getElementById('searchKeyword');
    const searchButton = document.getElementById('searchButton');

    if (searchButton && searchKeyword) {
        searchButton.addEventListener('click', function () {
            const keyword = searchKeyword.value.trim();
            if (!keyword) {
                alert("검색어를 입력하세요.");
                return;
            }

            const ps = new kakao.maps.services.Places();

            ps.keywordSearch(keyword, function (data, status, pagination) {
                if (status === kakao.maps.services.Status.OK) {
                    let formattedItems = formatter(data, 2);
                    currentSearchItems = formattedItems; // 현재 검색 결과 저장
                    updateSearchList(currentSearchItems);
                    displayMarkers(currentSearchItems, 'keyword'); // 'keyword' 타입 표시
                } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
                    alert("검색 결과가 없습니다.");
                } else if (status === kakao.maps.services.Status.ERROR) {
                    alert("검색 중 오류가 발생했습니다.");
                }
            });
        });
    }

    console.log("버튼 이벤트 초기화 완료");
}

// 글로벌 이벤트 리스너 초기화 함수
function initGlobalEventListeners() {
    document.addEventListener('click', function (e) {
        // '담기' 버튼 클릭 시
        if (e.target && e.target.matches('button.add-to-list')) {
            let contentid = e.target.getAttribute('data-id');
            let marker = searchMarkers.get(contentid);
            if (marker && !myList.some(item => item.contentid === contentid)) {
                // Add to My List
                myList.push(marker.item);

                // Change search marker image to selected
                marker.setImage(selectedMarkerImage);
                marker.isInMyList = true;

                // Create a new marker for myList
                let myMarker = new kakao.maps.Marker({
                    position: marker.getPosition(),
                    map: map,
                    image: selectedMarkerImage
                });
                myListMarkers.set(contentid, myMarker);

                // Add click listener to myListMarker
                kakao.maps.event.addListener(myMarker, 'click', function () {
                    closeAllInfoWindows();

                    let buttonClass = 'remove-from-list';
                    let buttonText = '삭제';

                    let content = `
                        <div style="padding:10px;">
                            <strong>${marker.item.id}</strong><br>
                            <button class="${buttonClass}" data-id="${marker.contentid}">
                                ${buttonText}
                            </button>
                        </div>
                    `;

                    let infowindow = new kakao.maps.InfoWindow({
                        content: content
                    });

                    infowindow.open(map, myMarker);
                    currentInfoWindow = infowindow;
                });

                // Update UI
                updateMyList();
                updateSearchList(currentSearchItems);

                // Close info window
                closeAllInfoWindows();

                console.log(`마이리스트에 추가됨: ${marker.item.id}`);
            }
        }

        // '삭제' 버튼 클릭 시
        if (e.target && e.target.matches('button.remove-from-list')) {
            let contentid = e.target.getAttribute('data-id');
            let marker = myListMarkers.get(contentid); // myListMarkers에서 가져오기
            if (marker) {
                // Remove from My List
                myList = myList.filter(item => item.contentid !== contentid);

                // Remove myList marker from map and delete from myListMarkers
                marker.setMap(null);
                myListMarkers.delete(contentid);

                // Update search marker image back to default if exists
                let searchMarker = searchMarkers.get(contentid);
                if (searchMarker) {
                    searchMarker.setImage(defaultMarkerImage);
                    searchMarker.isInMyList = false;
                }

                // Update UI
                updateMyList();
                updateSearchList(currentSearchItems);

                // Close info window
                closeAllInfoWindows();

                console.log(`마이리스트에서 삭제됨: ${contentid}`);
            }
        }

        // 검색 리스트 항목 클릭 시
        if (e.target && e.target.matches('.search-item-name')) {
            let contentid = e.target.getAttribute('data-id');
            let marker = searchMarkers.get(contentid);
            if (marker) {
                map.setCenter(marker.getPosition());
                map.setLevel(5); // 필요에 따라 확대 레벨 조정

                // Close any open info windows
                closeAllInfoWindows();

                // Determine if the item is in My List
                let isInMyList = myList.some(item => item.contentid === contentid);
                let buttonClass = isInMyList ? 'remove-from-list' : 'add-to-list';
                let buttonText = isInMyList ? '삭제' : '담기';

                let content = `
                    <div style="padding:10px;">
                        <strong>${marker.item.id}</strong><br>
                        <button class="${buttonClass}" data-id="${marker.contentid}">
                            ${buttonText}
                        </button>
                    </div>
                `;

                // Create a new info window
                let infowindow = new kakao.maps.InfoWindow({
                    content: content
                });

                infowindow.open(map, marker);
                currentInfoWindow = infowindow;
            }
        }

        // 마이리스트 항목 클릭 시
        if (e.target && e.target.matches('.mylist-item-name')) {
            let contentid = e.target.getAttribute('data-id');
            let myMarker = myListMarkers.get(contentid);
            let markerItem = myList.find(item => item.contentid === contentid);

            if (myMarker && markerItem) {
                map.setCenter(myMarker.getPosition());
                map.setLevel(5); // 필요에 따라 확대 레벨 조정

                // Close any open info windows
                closeAllInfoWindows();

                // '삭제' 버튼 표시
                let buttonClass = 'remove-from-list';
                let buttonText = '삭제';

                let content = `
                    <div style="padding:10px;">
                        <strong>${markerItem.id}</strong><br>
                        <button class="${buttonClass}" data-id="${contentid}">
                            ${buttonText}
                        </button>
                    </div>
                `;

                // Create a new info window
                let infowindow = new kakao.maps.InfoWindow({
                    content: content
                });

                infowindow.open(map, myMarker);
                currentInfoWindow = infowindow;
            }
        }
    });
}

// 초기화 버튼 핸들러
function onReset() {
    selectedArea = null;
    selectedSigungu = null;
    selectedType = null;

    // 필터 UI 초기화
    const selectedFiltersText = document.getElementById("selectedFilters");
    if (selectedFiltersText) {
        selectedFiltersText.textContent = "선택된 필터: 없음";
    }

    // 시군구 버튼 초기화
    const sigunguContainer = document.getElementById("sigunguButtons");
    if (sigunguContainer) {
        sigunguContainer.innerHTML = "";
    }

    // 마커와 리스트 초기화
    clearMarkers();
    clearMyListMarkers();
    myList = [];

    // UI 업데이트
    updateSearchList([]);
    updateMyList();

    console.log("초기화 완료!");
}

// 시군구 버튼 렌더링 함수
function renderSigunguButtons(areaName){
    const container = document.getElementById("sigunguButtons");
    container.innerHTML = "";

    if(!areaName || !regionCode[areaName]){
        return;
    }
    const sigunguObj = regionCode[areaName].sigungu;

    for(let sgName in sigunguObj){
        if (sigunguObj.hasOwnProperty(sgName)) {
            const btn = document.createElement("button");
            btn.textContent = sgName; 
            btn.className = "sgbtn";           
            btn.addEventListener("click", function(){
                selectedSigungu = this.textContent;
                updateSelectedFiltersText();
                console.log(`선택된 시군구: ${selectedSigungu}`);
            });
            container.appendChild(btn);
        }
    }
}

// 선택된 필터 텍스트 업데이트 함수
function updateSelectedFiltersText() {
    const selectedFiltersEl = document.getElementById("selectedFilters");
    if (!selectedFiltersEl) return;

    if (!selectedArea && !selectedSigungu && !selectedType) {
        selectedFiltersEl.innerText = "선택된 필터: 없음";
        return;
    }

    let msg = "선택된 필터: ";

    if (selectedArea) {
        msg += selectedArea;
        if (selectedSigungu) {
            msg += " " + selectedSigungu;
        }
    }

    if (selectedType) {
        if (selectedArea || selectedSigungu) {
            msg += " / " + selectedType;
        } else {
            msg += selectedType;
        }
    }

    selectedFiltersEl.innerText = msg;
}

// 데이터 포맷팅 함수
function formatter(items, type) {
    let formattedData = [];
    for (let i = 0; i < items.length; i++) {
        let data = {};

        if (type === 1) { // 지역 기반 검색 결과
            data.id = items[i].title;
            data.addr = items[i].addr1 || items[i].addr2;
            data.y = parseFloat(items[i].mapy);
            data.x = parseFloat(items[i].mapx);
            data.img = items[i].firstimage;
            data.contentid = items[i].contentid;
            data.contenttypeid =items[i].contenttypeid;
			data.cat2=items[i].cat2;
			data.cat3=items[i].cat3;

			
        } else { // 키워드 검색 결과
            data.id = items[i].place_name;
            data.addr = items[i].address_name;
            data.y = parseFloat(items[i].y);
            data.x = parseFloat(items[i].x);
            data.img = items[i].image_url || 1;
            data.contentid = items[i].id;
            data.contenttypeid = 1;
			data.cat2=1;
			data.cat3=1;
        }

        formattedData.push(data);
    }

    return formattedData;
}

// 검색 버튼 핸들러 (지역 기반 검색)
function onSearch() {
    if (!selectedArea || !selectedSigungu) {
        alert("지역(시·도)과 시·군·구를 모두 선택하세요!");
        return;
    }

    clearMarkers();

    let areaCode = regionCode[selectedArea].areaCode;
    let sigunguCode = regionCode[selectedArea].sigungu[selectedSigungu];
    let contentTypeId = tourCode[selectedType];

    fetchTourApiAreaBased(areaCode, sigunguCode, contentTypeId, function (items) {
        if (!items || items.length === 0) {
            alert("검색 결과가 없습니다.");
            return;
        }
        
        let formattedItems = formatter(items, 1);
        currentSearchItems = formattedItems; // 현재 검색 결과 저장
        updateSearchList(currentSearchItems);
        displayMarkers(currentSearchItems, 'area'); // 'area' 타입 표시
    });
}

// 모든 검색 마커 제거 함수
function clearMarkers() {
    // 마이리스트에 추가되지 않은 마커만 제거
    searchMarkers.forEach((marker, contentid) => {
        marker.setMap(null);
        searchMarkers.delete(contentid);
    });

    // 열린 인포윈도우 닫기
    if (currentInfoWindow) {
        currentInfoWindow.close();
        currentInfoWindow = null;
    }

    console.log("검색 결과 마커가 모두 제거되었습니다.");
}

// 모든 마이리스트 마커 제거 함수
function clearMyListMarkers() {
    myListMarkers.forEach((marker, contentid) => {
        marker.setMap(null);
        myListMarkers.delete(contentid);
    });

    console.log("마이리스트 마커가 모두 제거되었습니다.");
}

// 데이터 검증 함수
function validateItems(items) {
    return items && Array.isArray(items) && items.length > 0;
}

// 검색 리스트 업데이트 함수
function updateSearchList(items) {
    const searchList = document.getElementById("search-List");
    if (!searchList) return;

    // 현재 검색 리스트 초기화
    searchList.innerHTML = "";

    if (!validateItems(items)) {
        console.warn("유효하지 않은 데이터입니다:", items);
        return;
    }

    items.forEach(item => {
        const listItem = document.createElement("p");
        listItem.style.cursor = "pointer"; // 커서를 포인터로 변경하여 클릭 가능함을 시각적으로 표시

        // 마이리스트에 이미 있는지 확인
        const inMyList = myList.some(myItem => myItem.contentid === item.contentid);

        if (!inMyList) {
            listItem.innerHTML = `
                <span class="search-item-name" data-id="${item.contentid}" style="color: blue; text-decoration: underline;">
                    ${item.id} - ${item.addr}
                </span>
                <button class="add-to-list" data-id="${item.contentid}">담기</button>
            `;
        } else {
            listItem.innerHTML = `
                <span class="search-item-name" data-id="${item.contentid}" style="color: blue; text-decoration: underline;">
                    ${item.id} - ${item.addr}
                </span>
            `;
        }

        searchList.appendChild(listItem);
    });

    console.log("검색 리스트 업데이트 완료");
}

// 마이리스트 업데이트 함수
function updateMyList() {
    const myListContainer = document.getElementById("myList");
    if (!myListContainer) return;

    // 마이리스트 초기화
    myListContainer.innerHTML = "";

    myList.forEach(item => {
        const listItem = document.createElement("p");
        listItem.style.cursor = "pointer"; // 커서를 포인터로 변경하여 클릭 가능함을 시각적으로 표시

        listItem.innerHTML = `
            <span class="mylist-item-name" data-id="${item.contentid}" style="color: green; text-decoration: underline;">
                ${item.id} - ${item.addr}
            </span>
            <button class="remove-from-list" data-id="${item.contentid}">삭제</button>
        `;

        myListContainer.appendChild(listItem);
    });

    console.log("마이리스트 업데이트 완료");
}

// 마커 표시 함수 (검색 결과 마커)
function displayMarkers(items, searchType) {
    console.log("displayMarkers 호출됨, items:", items, "searchType:", searchType);

    if (!validateItems(items)) {
        console.error("displayMarkers: 유효하지 않은 데이터.", items);
        return;
    }

    items.forEach(item => {
        // 마이리스트에 이미 있는 마커는 검색 마커로 추가하지 않음
        if (myListMarkers.has(item.contentid)) {
            return; // 마이리스트 마커에 이미 존재하므로 검색 마커로 추가하지 않음
        }

        // 중복 마커 방지
        if (searchMarkers.has(item.contentid)) {
            return; // 이미 마커가 존재하면 건너뜀
        }

        console.log("마커 생성 중, item:", item);

        const markerPosition = new kakao.maps.LatLng(item.y, item.x);
        const marker = new kakao.maps.Marker({
            position: markerPosition,
            map: map,
            image: defaultMarkerImage // 기본 이미지 설정
        });

        marker.id = item.id;
        marker.contentid = item.contentid;
        marker.item = item;
        marker.isInMyList = false; // 초기 상태는 마이리스트에 없음

        // Add the marker to the searchMarkers Map
        searchMarkers.set(item.contentid, marker);

        // Assign a click listener to the marker
        kakao.maps.event.addListener(marker, 'click', function () {
            closeAllInfoWindows();

            let isInMyList = marker.isInMyList;
            let buttonClass = isInMyList ? 'remove-from-list' : 'add-to-list';
            let buttonText = isInMyList ? '삭제' : '담기';

            let content = `
                <div style="padding:10px;">
                    <strong>${marker.item.id}</strong><br>
                    <button class="${buttonClass}" data-id="${marker.contentid}">
                        ${buttonText}
                    </button>
                </div>
            `;

            // Create a new info window
            let infowindow = new kakao.maps.InfoWindow({
                content: content
            });

            infowindow.open(map, marker);
            currentInfoWindow = infowindow;
        });

        console.log("마커 생성 완료, marker:", marker);
    });

    fitMarkersToBounds();
}

// 모든 인포윈도우 닫기 함수
function closeAllInfoWindows() {
    if (currentInfoWindow) {
        currentInfoWindow.close();
        currentInfoWindow = null;
    }
}

// 마커의 위치에 맞게 지도 범위 조정 함수
function fitMarkersToBounds() {
    // 지도에 표시된 모든 마커의 위치를 포함하는 경계 설정
    const bounds = new kakao.maps.LatLngBounds();

    searchMarkers.forEach(marker => {
        bounds.extend(marker.getPosition());
    });

    myListMarkers.forEach(marker => {
        bounds.extend(marker.getPosition());
    });

    if (searchMarkers.size > 0 || myListMarkers.size > 0) {
        map.setBounds(bounds);
    }
}

const saveBtn = document.getElementById("myList-submit");
if (saveBtn) {
    saveBtn.addEventListener("click", function() {
        // myList 데이터를 JSON 문자열로 변환하여 세션에 저장

        sessionStorage.setItem('myList', JSON.stringify(myList));
        console.log("마이리스트가 세션에 저장되었습니다:", myList);
		const prevUrl = document.referrer;
		
		if(prevUrl.includes("update")){
			const num=prevUrl.split('num=')[1];
			window.location.href='/TripPlanner/postview/update?num='+num;
			
		}else{
        // 폼 페이지로 이동 (예: formpage.html)
        window.location.href = '/TripPlanner/postform';}
    });
}
