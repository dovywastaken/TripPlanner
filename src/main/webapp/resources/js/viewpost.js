document.addEventListener('DOMContentLoaded', function() {
    // place-info 요소들 초기화
    initializePlaceInfos();
   
});

// place-info 요소 초기화
function initializePlaceInfos() {
    const placeInfos = document.querySelectorAll('.place-info');

    placeInfos.forEach(info => {
        // 기존 x 버튼 제거
        const deleteBtn = info.querySelector('.delete-btn');
        if (deleteBtn) {
            deleteBtn.remove();
        }

        // 클릭 이벤트 추가 (장소 이동)
        info.addEventListener('click', function() {
            const locationBtn = this.querySelector('.location-btn');
            if (locationBtn && locationBtn.dataset.info) {
                try {
                    const placeData = JSON.parse(locationBtn.dataset.info);
                    navigateToLocation(placeData);
                } catch (error) {
                    console.warn('Invalid place data:', error);
                }
            }
        });
    });
}

// 위치로 이동하는 함수 (URL은 실제 매핑된 주소로 변경 필요)
function navigateToLocation(placeData) {
    // 예: 실제로는 '/TripPlanner/place?id=...' 대신
    //     프로젝트에 맞는 URL 로직을 작성하세요.
    window.location.href = `/TripPlanner/place?id=${placeData.id}`;
}
