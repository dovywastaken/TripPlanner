import regionCode from './data/regionCode.js';
import tourCode from './data/tourCode.js';

let map = null;          
let markers = [];         
let selectedArea = null;  
let selectedSigungu = null;  
let selectedType = null;  
let infowindows = [];
let myList = [];
let myListMarkers = [];
let myListMarkersVisible = true; 
const markerlists = {
    List: [],      
    copyList: [],  
    myList: [],   
    reList: []       
};


document.addEventListener("DOMContentLoaded", function() {
    initMap();
    initMapEvents();
});

//지도 초기화
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
    console.log("지도 생성 완료");
}

//버튼 이벤트 추가
function initMapEvents() {
    const areaBtns = document.querySelectorAll("[data-area]");
    for (let i = 0; i < areaBtns.length; i++) {
        (function(btn) {
            btn.addEventListener("click", function() {
                selectedArea = btn.getAttribute("data-area"); // 예: "서울"
                renderSigunguButtons(selectedArea);
                updateSelectedFiltersText();
            });
        })(areaBtns[i]);
    }

    // 타입 버튼 이벤트
    const typeBtns = document.querySelectorAll("[data-type]");
    for (let j = 0; j < typeBtns.length; j++) {
        (function(btn) {
            btn.addEventListener("click", function() {
                selectedType = btn.getAttribute("data-type"); // 예: "관광지"
                updateSelectedFiltersText();
            });
        })(typeBtns[j]);
    }

    // 검색 버튼 이벤트
    const searchBtn = document.getElementById("searchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", onSearch);
    }

    // 초기화 버튼 이벤트
    const resetBtn = document.getElementById("resetBtn");
    if (resetBtn) {
        resetBtn.addEventListener("click", onReset);
    } 
}




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
			btn.className="sgbtn"           
            btn.addEventListener("click", function(){
                selectedSigungu = this.textContent;
                updateSelectedFiltersText();
            });
            container.appendChild(btn);
        }
    }
}

//버튼 눌렀을때 선택 텍스트 변경
function updateSelectedFiltersText() {
    const selectedFiltersEl = document.getElementById("selectedFilters");
    if (!selectedFiltersEl) return;

    // 시·도, 시·군·구, 타입 중 아무것도 선택 안 됐으면
    if (!selectedArea && !selectedSigungu && !selectedType) {
        selectedFiltersEl.innerText = "선택된 필터: 없음";
        return;
    }

    // 메시지 시작
    let msg = "선택된 필터: ";

    // (A) 시·도(예: "경상남도")
    if (selectedArea) {
        msg += selectedArea;

        // (B) 시·군·구(예: "김해시")
        if (selectedSigungu) {
            msg += " " + selectedSigungu;  
            // '경상남도 김해시' 처럼 이어붙이기
        }
    }

    // (C) 타입(예: "관광지", "맛집" 등)
    if (selectedType) {
        // 시·도/시군구와 타입을 구분하려면 " / " 같은 구분자를 사용할 수도 있음
        if (selectedArea || selectedSigungu) {
            msg += " / " + selectedType;
        } else {
            // 지역이 전혀 없고 타입만 있을 수도 있으므로
            msg += selectedType;
        }
    }

    selectedFiltersEl.innerText = msg;
}

function formetter(item, type) {
  let formattedData = []; // 여러 데이터를 저장할 배열로 변경

  for (var i = 0; i < item.length; i++) {
    let data = {}; // 각 항목을 담을 객체

    if (type === 1) {
      data.id = item[i].title;
      data.addr = item[i].addr1 || item[i].addr2;
      data.y = item[i].mapy;
      data.x = item[i].mapx;
      data.img = item[i].firstimage2;
      data.data = item[i];
      data.contentid = item[i].contentid;
      data.contenttypeid = item[i].contenttypeid;
    } else {
      data.id = item[i].place_name;
      data.addr = item[i].address_name;
      data.y = item[i].y;
      data.x = item[i].x;
      data.img = 1;
      data.data = item[i];
      data.contentid = item[i].id;
      data.contenttypeid = null;
    }

    formattedData.push(data); // 각 항목을 배열에 추가
  }

  return formattedData; // 배열 반환
}

function onSearch() {
    if (!selectedArea || !selectedSigungu) {
        alert("지역(시·도)과 시·군·구를 모두 선택하세요!");
        return;
    }

    clearMarkers();
    closeAllInfoWindows();

    let areaCode = regionCode[selectedArea].areaCode;
    let sigunguCode = regionCode[selectedArea].sigungu[selectedSigungu] ;
    let contentTypeId = tourCode[selectedType] ;
	
	
	//api호출 tourAPI.js에 있음
    fetchTourApiAreaBased(areaCode, sigunguCode, contentTypeId, function(items) {
        if (!items || items.length === 0) {
            alert("검색 결과가 없습니다.");
            return;
        }
        displayMarkers(formetter(items,1));
    });
}

function clearMarkers() {
    // markerlists의 List, copyList, reList에 있는 모든 마커 제거
    for (let n = 0; n < markerlists.List.length; n++) {
        markerlists.List[n].setMap(null); // 지도에서 제거
    }
    markerlists.List = [];  // List 초기화

    for (let n = 0; n < markerlists.copyList.length; n++) {
        markerlists.copyList[n].setMap(null); // 지도에서 제거
    }
    markerlists.copyList = [];  // copyList 초기화

    for (let n = 0; n < markerlists.reList.length; n++) {
        markerlists.reList[n].setMap(null); // 지도에서 제거
    }
    markerlists.reList = [];  // reList 초기화
}

// 인포 전체 닫기
function closeAllInfoWindows() {
    for (let m = 0; m < infowindows.length; m++) {
        infowindows[m].close();
    }
    infowindows = []; // 배열 초기화
}   


let currentInfoWindow = null;

function displayMarkers(items) {
	console.log(items)
    closeAllInfoWindows();
    for (let i = 0; i < items.length; i++) {
        const item = items[i];
        // 경도 위도가 없을경우 스킵
        if (!item.x || !item.y) continue;

        // 필요한 데이터(addr1, firstimage2)가 모두 존재하는 경우에만 처리
        if (item.addr && item.img) {
            const position = new kakao.maps.LatLng(item.y, item.x);

            // 마커 생성
            const marker = new kakao.maps.Marker({ position: position });
            marker.setMap(map);
            markerlists.List.push(marker);
            markerlists.copyList.push(marker);

            // 마커에 contentid를 속성으로 추가하여 추후 참조 가능하게 함
            marker.contentid = item.contentid;

            if(item.contentid!=null&&item.contenttypeid!=null){
            detailintro(item.contentid, item.contenttypeid, function (detailitems) {
                if (!detailitems || detailitems.length === 0) {
                    console.warn("세부 정보가 없습니다:", item.contentid);
                    return;
                }

                // 세부 정보에서 firstmenu 가져오기
                const firstmenu = detailitems[0].firstmenu ? detailitems[0].firstmenu : "";
                const infocenterfood = detailitems[0].infocenterfood ? detailitems[0].infocenterfood : "";
				});
				}
                // 인포윈도우 생성
                const infowindow = new kakao.maps.InfoWindow({
                    content: `
                        <div data-x="${item.x}" data-y="${item.y}" style="padding:5px; text-align:center;">
                            <p>${item.id}</p>
                            <button id="myListbtn-${item.contentid}">담기</button>
                        </div>
                    `
                });

                // 마커 클릭 이벤트에 인포윈도우 연결
                kakao.maps.event.addListener(marker, "click", function () {
                    if (currentInfoWindow) {
                        currentInfoWindow.close();  // 기존에 열린 인포윈도우 닫기
                    }
                    // 새로운 인포윈도우 열기
                    infowindow.open(map, marker);
                    currentInfoWindow = infowindow;  // 현재 열린 인포윈도우 설정

                    // 인포윈도우가 열릴 때 버튼에 이벤트 리스너 추가
					var button = document.getElementById('myListbtn-' + item.contentid);
					                    if (button) {
					                        button.addEventListener('click', function() {
					                            addToMyList(item);
					                            infowindow.close();  // '담기' 클릭 시 인포윈도우 닫기
					                            currentInfoWindow = null;  // 열린 인포윈도우 초기화
					                        });
					                    }
					                });

                // 인포윈도우를 배열에 추가 (모든 인포윈도우를 관리하기 위함)
                infowindows.push(infowindow);
            
        }
    }

    fitMapBounds();
}



//마커 생성이 주위에 보이게 생성함
function fitMapBounds() {
    if (markerlists.copyList.length === 0) return;
    const bounds = new kakao.maps.LatLngBounds();
    for (let k = 0; k < markerlists.copyList.length; k++) {
        bounds.extend(markerlists.copyList[k].getPosition());
    }
    map.setBounds(bounds);
}

//마이리스트에 추가 
function addToMyList(item,detailitems) {
    // 중복 추가 방지
    if (!isInMyList(item.contentid)) {
        myList.push(item);
        alert(item.title + "이(가) 마이리스트에 추가되었습니다.");
        updateMyListUI(); // UI 업데이트 함수 호출
        addMyListMarker(item); // 마이리스트 마커 추가

        // 기존 마커 삭제
        removeRegularMarker(item.contentid);
    } else {
        alert(item.title + "은(는) 이미 마이리스트에 있습니다.");
    }

    // 로컬 스토리지에 저장
    localStorage.setItem('myList', JSON.stringify(myList));
}


//마이리스트 ui변경 
function updateMyListUI() {
    const myListElement = document.getElementById('myList');
    if (!myListElement) return; // 마이리스트 컨테이너가 없는 경우 스킵

    myListElement.innerHTML = ''; // 기존 리스트 초기화

    for (let i = 0; i < myList.length; i++) {
        const item = myList[i];
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <span>${item.title} - ${item.addr1}</span>
            <button class="myList-delete" data-contentid="${item.contentid}">삭제</button>
        `;
        myListElement.appendChild(listItem);
    }

    // 삭제 버튼에 이벤트 리스너 추가
    const deleteButtons = document.querySelectorAll('.myList-delete');
    for (let j = 0; j < deleteButtons.length; j++) {
        (function(button) {
            button.addEventListener('click', function() {
                const contentid = this.getAttribute('data-contentid');
                removeFromMyList(contentid);
            });
        })(deleteButtons[j]);
    }
}




function addMyListMarker(item) {
    for(var i = 0; i < markerlists.myList.length; i++) {
        if(markerlists.myList[i].contentid === item.contentid) {
            console.log(item.title + "은(는) 이미 마이리스트에 있습니다.");
            return;
        }
    }

    const position = new kakao.maps.LatLng(item.y, item.x);

   
    const myListIcon = new kakao.maps.MarkerImage(
        'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 예시 아이콘 URL
        new kakao.maps.Size(24, 35)
    );

    const marker = new kakao.maps.Marker({
        position: position,
        map: myListMarkersVisible ? map : null,
        title: item.id,
        image: myListIcon // MyList 전용 아이콘 설정
    });
	let infowindow = new kakao.maps.InfoWindow({
        content: `
            <div style="padding:5px; text-align:center;">
                <p style="font-weight:bold;">${item.id}</p>
                <p>${item.addr}</p>
                <button id="removeMyListbtn-${item.contentid}">삭제</button>
				
            </div>
        `
    });


    let isInfoWindowOpen = false;

    // 마커 클릭 이벤트에 인포윈도우 연결
    kakao.maps.event.addListener(marker, 'click', function() {
        if (isInfoWindowOpen) {
            infowindow.close(); // 인포윈도우 닫기
            isInfoWindowOpen = false;
        } else {
            infowindow.open(map, marker); // 인포윈도우 열기
            isInfoWindowOpen = true;

            // 인포윈도우가 열릴 때 버튼에 이벤트 리스너 추가
            setTimeout(function() {
                var button = document.getElementById('removeMyListbtn-' + item.contentid);
                if (button) {
                    button.addEventListener('click', function() {
                        removeFromMyList(item.contentid);
                        infowindow.close();
                        isInfoWindowOpen = false;
                    });
                }
            }, 100);
        }
    });

    // MyList 마커를 배열에 저장
    myListMarkers.push({ marker: marker, contentid: item.contentid, originalItem: item });
}


function isInMyList(contentid) {
    for(let i = 0; i < myList.length; i++) {
        if(myList[i].contentid === contentid) {
            return true;
        }
    }
    return false;
}




/**
 * 마이리스트에서 아이템 제거 함수
 */
function removeFromMyList(contentid) {
    // myList 배열에서 해당 contentid를 가진 항목을 제거
    var newList = [];
    var removedItem = null;
    for(var i = 0; i < myList.length; i++) {
        if(myList[i].contentid !== contentid) {
            newList.push(myList[i]);
        } else {
            removedItem = myList[i];
        }
    }
    myList = newList;
    localStorage.setItem('myList', JSON.stringify(myList));
    updateMyListUI();
    alert("항목이 마이리스트에서 삭제되었습니다.");

    // MyList 마커 배열에서 해당 마커를 찾아 제거
    for(var j = 0; j < myListMarkers.length; j++) {
        if(myListMarkers[j].contentid === contentid) {
            // 지도에서 마커 제거
            myListMarkers[j].marker.setMap(null);
            // 배열에서 마커 객체 제거
            myListMarkers.splice(j, 1);
            break;
        }
    }

    // 기존 마커 재생성
    if (removedItem) {
        addRegularMarker(removedItem);
    }
}






/**
 * 기존 마커 삭제 함수
 */
function removeRegularMarker(contentid) {
    for (let i = 0; i < markers.length; i++) {
        if (markers[i].contentid === contentid) {
            markers[i].setMap(null); // 지도에서 마커 제거
            markers.splice(i, 1); // 배열에서 마커 제거
            break;
        }
    }
}

/**
 * 기존 마커 재생성 함수
 */
function addRegularMarker(item) {
    const position = new kakao.maps.LatLng(item.mapy, item.mapx);

    // 마커 생성
    const marker = new kakao.maps.Marker({ position: position });
    marker.setMap(map);
    markers.push(marker);

    // 마커에 contentid를 속성으로 추가하여 추후 참조 가능하게 함
    marker.contentid = item.contentid;

    // 비동기로 detailintro 호출 후 처리
    detailintro(item.contentid, item.contenttypeid, function (detailitems) {
        if (!detailitems || detailitems.length === 0) {
            console.warn("세부 정보가 없습니다:", item.contentid);
            return;
        }

        // 세부 정보에서 firstmenu 가져오기
        const firstmenu = detailitems[0].firstmenu ? detailitems[0].firstmenu : "";
        const infocenterfood = detailitems[0].infocenterfood ? detailitems[0].infocenterfood : "";

        // 인포윈도우 생성
        const infowindow = new kakao.maps.InfoWindow({
            content: `
                <div data-x="${item.mapx}" data-y="${item.mapy}" style="padding:5px; text-align:center;">
                    ${item.firstimage2 ? `<img src="${item.firstimage2}" alt="${item.title}" style="width:100px; height:auto; margin-bottom:5px;">` : '<p>이미지 없음</p>'}
                    <p style="margin:5px 0; font-weight:bold;">${item.title}</p>
                    <p>${item.tel ? item.tel : (infocenterfood || "전화번호 정보 없음")}</p>
                    <p style="margin:5px 0; color:#666;">${item.addr1}</p>
                    <p>${item.zipcode}</p>
                    <p>${firstmenu || ""}</p>
                    <button id="myListbtn-${item.contentid}">담기</button>
                </div>
            `
        });
        let isInfoWindowOpen = false;

        // 마커 클릭 이벤트에 인포윈도우 연결
        kakao.maps.event.addListener(marker, "click", function () {
            if (isInfoWindowOpen) {
                infowindow.close(); // 인포윈도우 닫기
                isInfoWindowOpen = false;
            } else {
                infowindow.open(map, marker); // 인포윈도우 열기
                isInfoWindowOpen = true;
                
                // 인포윈도우가 열릴 때 버튼에 이벤트 리스너 추가
                setTimeout(function() {
                    var button = document.getElementById('myListbtn-' + item.contentid);
                    if (button) {
                        button.addEventListener('click', function() {
                            addToMyList(item);
                            infowindow.close();
                            isInfoWindowOpen = false;
                        });
                    }
                }, 100); 
            }
        });

        // 인포윈도우를 배열에 추가 (모든 인포윈도우를 관리하기 위함)
        infowindows.push(infowindow);
    });
}

/**
 * MyList 마커 표시/숨기기 함수


/**
 * 페이지 로드 시 MyList 불러오기 및 MyList 마커 복원
 */
window.onload = function () {
    const storedList = sessionStorage.getItem('myList'); // sessionStorage로 변경
    if (storedList) {
        myList = JSON.parse(storedList);
        updateMyListUI();

        // MyList에 저장된 항목들을 기반으로 MyList 마커 추가
        for (let i = 0; i < myList.length; i++) {
            addMyListMarker(myList[i]); // 함수 호출로 수정
        }
    }
};



function onReset() {
    clearMarkers();
    selectedArea = null;
    selectedSigungu = null;
    selectedType = null;
    updateSelectedFiltersText();
    alert("초기화 완료");
}

document.getElementById("searchButton").addEventListener("click", function(event) {
    event.preventDefault();

    let keyword = document.getElementById("searchKeyword").value;
    if (keyword) {
        searchPlaces(keyword, function(results) {
           
            displayMarkers(formetter(results, 2));

            let listContainer = document.getElementById("serch-List");
            listContainer.innerHTML = ""; // 기존 내용을 초기화

            // 검색 결과를 리스트로 표시
            results.forEach(result => {
                listContainer.innerHTML += `<p>${result.place_name}</p>`;
            });
        });
    } else {
        alert("검색어를 입력해주세요");
    }
});

function searchPlaces(keyword, callback) {
    if (!keyword) {
        console.error("키워드가 없습니다.");
        return;
    }
    console.log(keyword);
    const ps = new kakao.maps.services.Places();
    
    ps.keywordSearch(keyword, function (data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            callback(data); // 검색 결과를 콜백으로 반환
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            alert("검색 결과가 없습니다.");
        } else {
            console.error("검색 중 오류 발생:", status);
        }
    });
}

document.getElementById("myList-submit").addEventListener("click",function(){
	
	sessionStorage.setItem("myListData", JSON.stringify(myList));
	window.location.href ="postform";

})
	
	
	





