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



// 위치로 이동하는 함수
document.querySelectorAll('.location-name-btn').forEach(button => {
    button.addEventListener('click', () => {
        // data-info 속성 가져오기
        const dataInfo = button.getAttribute('data-info');

        // 현재 화면 크기 가져오기
        const screenWidth = window.innerWidth;
        const screenHeight = window.innerHeight;

        // 팝업 창 크기 설정 (화면의 80% 크기로 설정)
        const popupWidth = Math.floor(screenWidth * 0.9);
        const popupHeight = Math.floor(screenHeight * 0.9);

        // 팝업 창 중앙에 위치하도록 설정
        const left = Math.floor((screenWidth - popupWidth) / 2);
        const top = Math.floor((screenHeight - popupHeight) / 2);

        // 팝업 창 열기 (반응형 크기 설정)
        window.open(
            contextPath+`/map/detailed?info=${encodeURIComponent(dataInfo)}`,
            'popupWindow',
            `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`
        );
    });
});


