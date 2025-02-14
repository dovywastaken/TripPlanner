let map;

document.addEventListener("DOMContentLoaded", function() {
    initMap();
});

function initMap() {
    const mapContainer = document.getElementById('map');
    if (!mapContainer) {
        console.error('지도 영역이 없습니다.');
        return;
    }

    // data-info 파싱
    const infoStr = mapContainer.getAttribute('data-info');
    const info = JSON.parse(infoStr);

    // 지도 기본 설정
    const position = new kakao.maps.LatLng(info.y, info.x);
    const options = {
        center: position,
        level: 3
    };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, options);

    // 마커 생성
    const marker = new kakao.maps.Marker({
        position: position,
        map: map
    });

    // 🔥 InfoWindow 생성 - 마커에 제목 표시
    const infoWindow = new kakao.maps.InfoWindow({
        content: `<div style="padding:5px; font-size:14px;">${info.id}</div>`
    });

    // 마커에 이벤트 리스너 추가 - 클릭 시 정보창 열기
    kakao.maps.event.addListener(marker, 'click', function() {
        infoWindow.open(map, marker);
    });

    // 마커 위에 바로 타이틀을 띄우고 싶다면 아래 코드 추가
    infoWindow.open(map, marker);

    // 상세 정보 렌더링
    if (info.contenttypeid === "1") { // 카카오맵 데이터
        renderKakaoPlace(info.contentid);
    } else { // 관광공사 데이터
        renderTourPlace(info);
    }
}

// 카카오맵 장소 렌더링
function renderKakaoPlace(contentId) {
    const detailBox = document.getElementById('detail-box');
    if(!detailBox) return;

    const url = `https://place.map.kakao.com/${contentId}`;
    console.log(url);
    detailBox.innerHTML = `<iframe src="${url}" style="width: 100%; height: 100%; border: none; position: absolute; top: 0; left: 0;"></iframe>`;
}


// 관광공사 데이터 렌더링
function renderTourPlace(info) {
    detailintro(info.contenttypeid, info.contentid, function(detail) {
        if(detail && typeof detailCommon === 'function') {
            detailCommon(info.contentid, info.contenttypeid, function(details) {
                if(details) {
                    renderTourDetail(detail, details);
                }
            });
        }
    });
}

// 관광공사 상세 정보 렌더링
function renderTourDetail(detail, details) {
	console.log("왓냐?");
    const detailBox = document.getElementById('detail-box');
    if(!detailBox) return;
	console.log("왓냐?2");
	
    detailBox.innerHTML = `
        <div class="detail-wrapper">
            ${details[0].title ? `
                <div class="detail-title">
                    <h1>${details[0].title}</h1>
                </div>
            ` : ''}

            ${details[0].firstimage ? `
                <div class="detail-images">
                    <div class="single-image">
                        <img src="${details[0].firstimage}" alt="대표 이미지">
                    </div>
                </div>
            ` : ''}

            <div class="basic-info">
                <table class="info-table">
                    ${details[0].addr1 ? `
                        <tr>
                            <th>주소</th>
                            <td>${details[0].addr1}</td>
                        </tr>
                    ` : ''}
                    ${details[0].tel ? `
                        <tr>
                            <th>문의 전화</th>
                            <td>${details[0].tel}</td>
                        </tr>
                    ` : ''}
                    ${details[0].zipcode ? `
                        <tr>
                            <th>우편번호</th>
                            <td>${details[0].zipcode}</td>
                        </tr>
                    ` : ''}
                </table>
            </div>

            ${details[0].overview ? `
                <div class="overview-section">
                    <h2>상세 설명</h2>
                    <div class="overview-content">
                        ${details[0].overview}
                    </div>
                </div>
            ` : ''}
        </div>
    `;
}