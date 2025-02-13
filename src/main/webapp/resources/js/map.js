import regionCode from './data/regionCode.js';
import tourCode from './data/tourCode.js';

// 전역 변수 선언
let map = null;
let selectedArea = null;
let selectedSigungu = null;
let selectedType = null;
let myList = [];
let currentSearchItems = [];
let currentInfoWindow = null;

let currentPage = 1;       // 추가
let itemsPerPage = 5; 

// 마커 관리를 위한 Map 객체
const searchMarkers = new Map();
const myListMarkers = new Map();

// 마커 이미지 정의
const defaultMarkerImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png',
    new kakao.maps.Size(24, 35),
    { offset: new kakao.maps.Point(12, 35) }
);

const selectedMarkerImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png',
    new kakao.maps.Size(24, 35),
    { offset: new kakao.maps.Point(12, 35) }
);

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', function() {
    initMap();
    initMapEvents();
    initGlobalEventListeners();
    showSearchList(); // 기본적으로 검색 리스트 표시
});

// 지도 초기화
function initMap() {
    const mapContainer = document.getElementById('map');
    if (!mapContainer) {
        console.error('지도 영역이 없습니다.');
        return;
    }

    const options = {
        center: new kakao.maps.LatLng(37.566, 126.978),
        level: 8
    };
    map = new kakao.maps.Map(mapContainer, options);

    // 지도 클릭 시 열린 인포윈도우 닫기
    kakao.maps.event.addListener(map, 'click', closeAllInfoWindows);
}

// 이벤트 초기화
function initMapEvents() {
    // 지역 버튼 이벤트
	const areaBtns = document.querySelectorAll('[data-area]');
	areaBtns.forEach(btn => {
	    btn.addEventListener('click', function() {
	        // 기존 선택된 지역 버튼에서 클래스 제거
	        areaBtns.forEach(b => b.classList.remove('selected'));
	        // 현재 버튼에 선택 클래스 추가
	        this.classList.add('selected');
	        
	        selectedArea = this.getAttribute('data-area');
	        selectedSigungu = null;
	        
	        const sigunguContainer = document.getElementById('sigunguButtons');
	        if (sigunguContainer) {
	            sigunguContainer.innerHTML = '';
	        }
	        
	        renderSigunguButtons(selectedArea);
	        updateSelectedFiltersText();
	    });
	});

    // 관광 타입 버튼 이벤트
	const typeBtns = document.querySelectorAll('[data-type]');
	typeBtns.forEach(btn => {
	    btn.addEventListener('click', function() {
	        // 기존 선택된 타입 버튼에서 클래스 제거
	        typeBtns.forEach(b => b.classList.remove('selected'));
	        // 현재 버튼에 선택 클래스 추가
	        this.classList.add('selected');
	        
	        selectedType = this.getAttribute('data-type');
	        updateSelectedFiltersText();
	    });
	});

    // 검색 버튼 이벤트
    const searchBtn = document.getElementById('searchBtn');
    if (searchBtn) {
        searchBtn.addEventListener('click', onSearch);
    }

    // 초기화 버튼 이벤트
    const resetBtn = document.getElementById('resetBtn');
    if (resetBtn) {
        resetBtn.addEventListener('click', onReset);
    }

    // 키워드 검색 이벤트
    const searchButton = document.getElementById('searchButton');
    const searchKeyword = document.getElementById('searchKeyword');
    if (searchButton && searchKeyword) {
        searchButton.addEventListener('click', function() {
            onKeywordSearch(searchKeyword.value);
        });
    }

    // 리스트 토글 버튼 이벤트
    const searchListBtn = document.getElementById('serch-Listbtn');
    const myListBtn = document.getElementById('myListbtn');
    if (searchListBtn && myListBtn) {
        searchListBtn.addEventListener('click', showSearchList);
        myListBtn.addEventListener('click', showMyList);
    }

    // 저장 버튼 이벤트
    const saveBtn = document.getElementById('myList-submit');
    if (saveBtn) {
        saveBtn.addEventListener('click', saveMyList);
    }
}

// 전역 이벤트 리스너 초기화
function initGlobalEventListeners() {
    document.addEventListener('click', function(e) {
        // 리스트 아이템 클릭 처리
        if (e.target.closest('.search-item-content')) {
            const contentId = e.target.closest('.search-item-content').getAttribute('data-id');
            handleSearchItemClick(contentId);
        } else if (e.target.closest('.mylist-item-content')) {
            const contentId = e.target.closest('.mylist-item-content').getAttribute('data-id');
            handleMyListItemClick(contentId);
        }
        // 버튼 클릭 처리
        else if (e.target.matches('.add-to-list')) {
            handleAddToList(e.target);
        } else if (e.target.matches('.remove-from-list')) {
            handleRemoveFromList(e.target);
        }
    });
}
// 시군구 버튼 렌더링
function renderSigunguButtons(areaName) {
    const container = document.getElementById('sigunguButtons');
    if (!container || !areaName || !regionCode[areaName]) return;
    
    container.innerHTML = '';
    const sigunguObj = regionCode[areaName].sigungu;

    for (let sgName in sigunguObj) {
        if (sigunguObj.hasOwnProperty(sgName)) {
            const btn = document.createElement('button');
            btn.textContent = sgName;
            btn.className = 'sgbtn';
            btn.addEventListener('click', function() {
                // 기존 선택된 시군구 버튼에서 클래스 제거
                container.querySelectorAll('.sgbtn').forEach(b => b.classList.remove('selected'));
                // 현재 버튼에 선택 클래스 추가
                this.classList.add('selected');
                
                selectedSigungu = this.textContent;
                updateSelectedFiltersText();
            });
            container.appendChild(btn);
        }
    }
}

// 필터 텍스트 업데이트
function updateSelectedFiltersText() {
    const selectedFiltersEl = document.getElementById('selectedFilters');
    if (!selectedFiltersEl) return;

    if (!selectedArea && !selectedSigungu && !selectedType) {
        selectedFiltersEl.textContent = '선택된 필터: 없음';
        return;
    }

    let msg = '선택된 필터: ';
    msg += selectedArea ? selectedArea : '';
    msg += selectedSigungu ? ' ' + selectedSigungu : '';
    msg += selectedType ? (selectedArea || selectedSigungu ? ' / ' : '') + selectedType : '';
    selectedFiltersEl.textContent = msg;
}

// 데이터 포맷팅
function formatter(items, type) {
    return items.map(item => {
        if (type === 1) {
			console.log(item.cat2);
            return {
                id: item.title,
                addr: item.addr1 || item.addr2,
                y: parseFloat(item.mapy),
                x: parseFloat(item.mapx),
                img: item.firstimage,
                tel: item.tel || (item.detailInfo && item.detailInfo.infocenterfood) || '',
                contentid: item.contentid,
                contenttypeid: item.contenttypeid,
                zipcode: item.zipcode,
                cat2: item.cat2,
			 
                cat3: item.cat3,
            };
        } else {
            return {
                id: item.place_name,
                addr: item.address_name,
                y: parseFloat(item.y),
                x: parseFloat(item.x),
                img: item.image_url || "",
                tel: item.phone || "",
                zipcode: "",
                contentid: item.id,
                contenttypeid: "1",
                cat2: 1,
                cat3: 1
            };
        }
    });
}
// 마커 생성 및 관리
function createMarker(item, isMyList = false) {
    const position = new kakao.maps.LatLng(item.y, item.x);
    const marker = new kakao.maps.Marker({
        position: position,
        map: map,
        image: isMyList ? selectedMarkerImage : defaultMarkerImage
    });

    marker.item = item;

    // 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function() {
        closeAllInfoWindows();
        
        // 마이리스트 마커가 아닐 경우에만 줌 레벨 변경
        if (!isMyList) {
            map.setCenter(marker.getPosition());
            map.setLevel(7);
        }
        
        const content = createInfoWindowContent(item, isMyList);
        const infowindow = new kakao.maps.InfoWindow({ content });
        infowindow.open(map, marker);
        currentInfoWindow = infowindow;
    });

    return marker;
}

// 인포윈도우 컨텐츠 생성
function createInfoWindowContent(item, isInMyList) {
    const buttonClass = isInMyList ? 'remove-from-list' : 'add-to-list';
    const buttonText = isInMyList ? '삭제' : '담기';
    
    return `
        <div class="custom-infowindow">
            <div class="info-content">
                <div class="info-img">
                    ${item.img 
                        ? `<img src="${item.img}" alt="${item.id}">` 
                        : '사진'
                    }
                </div>
                <div class="info-body">
                    <div class="info-title">${item.id}</div>
                    <div class="info-address">
                        <div class="addr-main">${item.addr}</div>
                        <div class="addr-zipcode">(${item.zipcode})</div>
                    </div>
                    <div class="info-contact">
                        <div class="info-tel">${item.tel || ""}</div>
                        <div class="info-email">${item.email || ""}</div>
                    </div>
                </div>
            </div>
            <div class="info-button">
                <button class="${buttonClass} additional-class" data-id="${item.contentid}">
                    ${buttonText}
                </button>
            </div>
        </div>
    `;
}
// 마커 표시
function displayMarkers(items) {
    if (!items || items.length === 0) return;

    items.forEach(function(item) {
        if (myListMarkers.has(item.contentid) || searchMarkers.has(item.contentid)) return;
        
        const marker = createMarker(item);
        searchMarkers.set(item.contentid, marker);
    });

    fitMarkersToBounds();
}

// 리스트 표시/숨김 함수
function showSearchList() {
    const searchListDiv = document.getElementById('search-List');
    const myListDiv = document.getElementById('myList');
    const searchListBtn = document.getElementById('serch-Listbtn');
    const myListBtn = document.getElementById('myListbtn');

    if (searchListDiv && myListDiv) {
        searchListDiv.style.display = 'block';
        myListDiv.style.display = 'none';
        searchListDiv.classList.add('active');
        myListDiv.classList.remove('active');
        searchListBtn.classList.add('active');
        myListBtn.classList.remove('active');
    }
}

function showMyList() {
    const searchListDiv = document.getElementById('search-List');
    const myListDiv = document.getElementById('myList');
    const searchListBtn = document.getElementById('serch-Listbtn');
    const myListBtn = document.getElementById('myListbtn');

    if (searchListDiv && myListDiv) {
        myListDiv.style.display = 'block';
        searchListDiv.style.display = 'none';
        myListDiv.classList.add('active');
        searchListDiv.classList.remove('active');
        myListBtn.classList.add('active');
        searchListBtn.classList.remove('active');

        // 마이리스트의 모든 마커가 보이도록 지도 조정
        if (myListMarkers.size > 0) {
            const bounds = new kakao.maps.LatLngBounds();
            myListMarkers.forEach(marker => {
                bounds.extend(marker.getPosition());
            });
            map.setBounds(bounds);
        }
    }
}


// 리스트 업데이트
function updateSearchList(items) {
    const searchListDiv = document.getElementById('search-List');
    if (!searchListDiv) return;

    searchListDiv.innerHTML = '';
    
    // 페이지네이션 계산
    const totalPages = Math.ceil(items.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, items.length);
    const currentItems = items.slice(startIndex, endIndex);
    
    // 아이템 목록 생성
    currentItems.forEach(item => {
        const inMyList = myList.some(myItem => myItem.contentid === item.contentid);
        const listItem = document.createElement('div');
        listItem.className = 'list-item';
        
        listItem.onclick = function(e) {
            if (!e.target.classList.contains('add-to-list') && 
                !e.target.classList.contains('remove-from-list')) {
                handleSearchItemClick(item.contentid);
            }
        };
		
		let tagText = '';
		if (item.contenttypeid === "12") {
		    tagText = '관광지';
		} else if (item.contenttypeid === "14") {
		    tagText = '문화시설';
		} else if (item.contenttypeid === "15") {
		    tagText = '축제공연';
		} else if (item.contenttypeid === "28") {
		    tagText = '레포츠';
		} else if (item.contenttypeid === "38") {
		    tagText = '쇼핑';
		} else if (item.contenttypeid === "39") {
		    tagText = '맛집';
		} else if (item.contenttypeid === "1") {
		    tagText = '카카오';
		} else {
		    tagText = '기타';
		} 
		let shortTitle = item.id;
		    if(item.id.length > 11) {
		        shortTitle = item.id.substring(0, 11) + '...';
		    }
	listItem.innerHTML = '' +
	    '<div class="search-item-content" data-id="' + item.contentid + '">' +
	    '    <div class="place-title">' + shortTitle + '</div>' +
	    '    <div class="place-address">' + (item.addr || '') + '</div>' +
	    '    <div class="place-tel">' + (item.tel || '') + '</div>' +
	    '    <div class="place-tag place-tag-' + tagText + '">' + tagText + '</div>' +
	    '</div>' +
	    '<button class="' + (inMyList ? 'remove-from-list' : 'add-to-list') + '" data-id="' + item.contentid + '">' +
	    (inMyList ? '-' : '+') +
	    '</button>';

        searchListDiv.appendChild(listItem);
    });

    const box = document.createElement('div');
    box.className = 'box';
    searchListDiv.insertBefore(box, searchListDiv.firstChild);

    // 페이지네이션 UI
    if (items.length > itemsPerPage) {
        const paginationDiv = document.createElement('div');
        paginationDiv.className = 'pagination';
        
        const maxButtons = 10;
        const startPage = Math.max(1, Math.min(currentPage - Math.floor(maxButtons / 2), totalPages - maxButtons + 1));
        const endPage = Math.min(totalPages, startPage + maxButtons - 1);
        
        for (let i = startPage; i <= endPage; i++) {
            const pageButton = document.createElement('button');
            pageButton.innerHTML = i;
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.onclick = () => {
                currentPage = i;
                updateSearchList(items);
            };
            paginationDiv.appendChild(pageButton);
        }
        
        searchListDiv.appendChild(paginationDiv);
    }
}

function updateMyList() {
    const myListDiv = document.getElementById('myList');
    if (!myListDiv) return;

    myListDiv.innerHTML = '';
    
    myList.forEach((item, index) => {
        const listItem = document.createElement('div');
        listItem.className = 'list-item';
        listItem.draggable = true;
        
        // 알파벳 순서 (A, B, C...)
        const orderLabel = String.fromCharCode(65 + index); // 65는 'A'의 ASCII 코드
		let tagText = '';
				if (item.contenttypeid === "12") {
				    tagText = '관광지';
				} else if (item.contenttypeid === "14") {
				    tagText = '문화시설';
				} else if (item.contenttypeid === "15") {
				    tagText = '축제공연';
				} else if (item.contenttypeid === "28") {
				    tagText = '레포츠';
				} else if (item.contenttypeid === "38") {
				    tagText = '쇼핑';
				} else if (item.contenttypeid === "39") {
				    tagText = '맛집';
				} else if (item.contenttypeid === "1") {
				    tagText = '카카오';
				} else {
				    tagText = '기타';
				} 
		let shortTitle = item.id;
		if(item.id.length > 11) {
		 shortTitle = item.id.substring(0, 11) + '...';
						    }
		listItem.innerHTML = '' +
		    '<div class="mylist-item-content" data-id="' + item.contentid + '">' +
		        '<div class="place-title">' + orderLabel + ' ' + shortTitle + '</div>' +
		        '<div class="place-address">' + item.addr + '</div>' +
		        '<div class="place-tel">' + item.tel + '</div>' +
		        '<div class="place-tag place-tag-' + tagText + '">' + tagText + '</div>' +
		    '</div>' +
		    '<button class="remove-from-list" data-id="' + item.contentid + '">-</button>';


        listItem.addEventListener('dragstart', handleDragStart);
        listItem.addEventListener('dragover', handleDragOver);
        listItem.addEventListener('drop', handleDrop);
        listItem.addEventListener('dragend', handleDragEnd);
        
        myListDiv.appendChild(listItem);
    });

}


// 드래그 관련 변수
let draggedItem = null;

// 드래그 이벤트 핸들러들
function handleDragStart(e) {
    draggedItem = this;
    this.style.opacity = '0.5';
}

function handleDragOver(e) {
    e.preventDefault();
}

function handleDrop(e) {
    e.preventDefault();
    if (this !== draggedItem) {
        // 원래 위치와 새 위치의 인덱스 찾기
        const items = [...this.parentNode.children];
        const fromIndex = items.indexOf(draggedItem);
        const toIndex = items.indexOf(this);

        // myList 배열 재정렬
        const [movedItem] = myList.splice(fromIndex, 1);
        myList.splice(toIndex, 0, movedItem);

        // DOM 재정렬
        this.parentNode.insertBefore(draggedItem, toIndex > fromIndex ? this.nextSibling : this);
        
        // 리스트 업데이트
        updateMyList();
    }
}

function handleDragEnd(e) {
    this.style.opacity = '1';
    draggedItem = null;
}
// 이벤트 핸들러
function handleSearchItemClick(contentid) {
    const marker = searchMarkers.get(contentid) || myListMarkers.get(contentid);
    if (marker) {
        map.setCenter(marker.getPosition());
        map.setLevel(3);
        closeAllInfoWindows();
        const isInMyList = myListMarkers.has(contentid);
        const content = createInfoWindowContent(marker.item, isInMyList);
        const infowindow = new kakao.maps.InfoWindow({ content });
        infowindow.open(map, marker);
        currentInfoWindow = infowindow;
    }
}

function handleMyListItemClick(contentid) {
    const marker = myListMarkers.get(contentid);
    if (marker) {
        map.setCenter(marker.getPosition());
        map.setLevel(3);
        closeAllInfoWindows();
        const content = createInfoWindowContent(marker.item, true);
        const infowindow = new kakao.maps.InfoWindow({ content });
        infowindow.open(map, marker);
        currentInfoWindow = infowindow;
    }
}

function handleAddToList(target) {
    const contentid = target.getAttribute('data-id');
    const item = currentSearchItems.find(item => item.contentid === contentid);
    if (!item || myList.some(myItem => myItem.contentid === contentid)) return;

    myList.push(item);

    const searchMarker = searchMarkers.get(contentid);
    if (searchMarker) {
        searchMarker.setMap(null);
        searchMarkers.delete(contentid);
    }

    const myListMarker = createMarker(item, true);
    myListMarkers.set(contentid, myListMarker);

    updateMyList();
    updateSearchList(currentSearchItems);
    closeAllInfoWindows();
}

	function handleRemoveFromList(target) {
	    const contentid = target.getAttribute('data-id');
	    const item = myList.find(item => item.contentid === contentid);
	    if (!item) return;

	    myList = myList.filter(item => item.contentid !== contentid);

	    const myListMarker = myListMarkers.get(contentid);
	    if (myListMarker) {
	        myListMarker.setMap(null);
	        myListMarkers.delete(contentid);
	    }

	    if (currentSearchItems.some(searchItem => searchItem.contentid === contentid)) {
	        const searchMarker = createMarker(item);
	        searchMarkers.set(contentid, searchMarker);
	    }

	    updateMyList();
	    updateSearchList(currentSearchItems);
	    closeAllInfoWindows();
	}

	// 검색 관련 함수
	function onSearch() {
	    if (!selectedArea || !selectedSigungu) {
	        alert('지역과 시군구를 선택해주세요!');
	        return;
	    }

	    clearSearchMarkers();
	    const areaCode = regionCode[selectedArea] ? regionCode[selectedArea].areaCode : null;
	    const sigunguCode = regionCode[selectedArea] && regionCode[selectedArea].sigungu[selectedSigungu]
	        ? regionCode[selectedArea].sigungu[selectedSigungu]
	        : null;
	    const contentTypeId = selectedType ? tourCode[selectedType] : null;

	    fetchTourApiAreaBased(areaCode, sigunguCode, contentTypeId, function(items) {
	        if (!items || items.length === 0) {
	            alert('검색 결과가 없습니다.');
	            return;
	        }

	        showSearchList();
	        currentSearchItems = formatter(items, 1);
	        updateSearchList(currentSearchItems);
	        displayMarkers(currentSearchItems);
	    });
	}


	function onKeywordSearch(keyword) {
	    if (!keyword || keyword.trim() === '') {
	        alert('검색어를 입력해주세요.');
	        return;
	    }

	    const ps = new kakao.maps.services.Places();
	    const searchInBoundsCheckbox = document.getElementById('searchInBounds');
	    const searchInBounds = searchInBoundsCheckbox ? searchInBoundsCheckbox.checked : false;

	    const searchOptions = {
	        size: 15  // 한 페이지에 보여질 갯수
	    };

	    if (searchInBounds) {
	        const bounds = map.getBounds();
	        searchOptions.bounds = bounds;
	    }

	    ps.keywordSearch(keyword, function(data, status) {
	        if (status === kakao.maps.services.Status.OK) {
	            currentSearchItems = formatter(data, 2);
	            updateSearchList(currentSearchItems);
	            displayMarkers(currentSearchItems);
	            showSearchList();
	        } else {
	            alert(status === kakao.maps.services.Status.ZERO_RESULT ? 
	                '검색 결과가 없습니다.' : '검색 중 오류가 발생했습니다.');
	        }
	    }, searchOptions);
	}


	function onReset() {
	    selectedArea = null;
	    selectedSigungu = null;
	    selectedType = null;
	    
	    // 모든 선택된 버튼의 스타일 초기화
	    document.querySelectorAll('.selected').forEach(btn => btn.classList.remove('selected'));
	    
	    const selectedFiltersEl = document.getElementById('selectedFilters');
	    if (selectedFiltersEl) {
	        selectedFiltersEl.textContent = '선택된 필터: 없음';
	    }
	    
	    const sigunguContainer = document.getElementById('sigunguButtons');
	    if (sigunguContainer) {
	        sigunguContainer.innerHTML = '';
	    }
	    
	    clearSearchMarkers();
	    updateSearchList([]);
	}

	// 마커 관리 함수
	function clearSearchMarkers() {
	    searchMarkers.forEach(marker => marker.setMap(null));
	    searchMarkers.clear();
	    closeAllInfoWindows();
	}


	function closeAllInfoWindows() {
	    if (currentInfoWindow) {
	        currentInfoWindow.close();
	        currentInfoWindow = null;
	    }
	}

	function fitMarkersToBounds() {
	    const bounds = new kakao.maps.LatLngBounds();
	    let hasMarkers = false;

	    // 검색 결과 마커만 bounds에 포함
	    searchMarkers.forEach(marker => {
	        bounds.extend(marker.getPosition());
	        hasMarkers = true;
	    });

	    if (hasMarkers) {
	        map.setBounds(bounds);
	        
	        // bounds 설정 후 적절한 줌 레벨 유지를 위한 추가 처리
	        if (map.getLevel() < 5) {  // 줌 레벨이 너무 가까우면
	            map.setLevel(5);  // 적정 레벨로 조정
	        }
	    }
	}


	
	function saveMyList() {
	    sessionStorage.setItem('myList', JSON.stringify(myList));
	    const prevUrl = document.referrer;

	    if (prevUrl.includes('update')) {
	        const num = prevUrl.split('num=')[1];
	        if (num) {
	            // 문자열 연결로 변경
	            window.location.href = contextPath+'/postView/update?num=' + num;
	        } else {
	            console.error('URL에서 num 파라미터를 찾을 수 없습니다.');
	        }
	    } else {
	        window.location.href = contextPath + '/postForm';
	    }
	}
	
	
	
	
	
	document.getElementById('myListbtn').addEventListener('click', function() {
	    const submitButton = document.getElementById('myList-submit');
	    submitButton.classList.toggle('show');  // active 상태에 따라 show 토글
	});

	// 검색창 버튼 클릭시 저장 버튼 숨기기
	document.getElementById('serch-Listbtn').addEventListener('click', function() {
	    document.getElementById('myList-submit').classList.remove('show');
	});