document.addEventListener('DOMContentLoaded', function() {
    console.log("페이지 로딩");
    loadAjax();
});

function loadAjax() {
    let open_key = 'WrDDwyS8ewwsZtX%2Bw9POHX4r8rVWShuslpdt7%2Bv0hEZhVvlddHkM0eFnIi2DYxfltV0h9zHXlW6mgecGdjXqvw%3D%3D';
    let arrrange = 'A';
    let areaCode = 1;
    let sigunguCode = 3;

    console.log("loadAjax 실행됨");
    $.ajax({
        url: "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=" + open_key,
        type: 'GET',
        dataType: 'json',
        data: {
            MobileApp: 'AppTest',
            MobileOS: 'ETC',
            listYN: 'Y',
			contentTypeId: 12,
            arrange: arrrange,
            areaCode: areaCode,
            sigunguCode: sigunguCode,
            _type: 'json',
            numOfRows: 8,  // 최대 8개 데이터 가져오기
            pageNo: 1
        },
        success: function(response) {
            console.log("AJAX 요청 성공");
            const items = response.response.body.items.item; // API에서 데이터 가져오기

            items.forEach(function(item, index) {
                const itemId = `.container:nth-child(${index + 2})`; // 각 container div 선택
                const imagePlaceholder = $(itemId).find('.image-placeholder');
                const title = $(itemId).find('.title');
                const overview = $(itemId).find('.overview');
                const contentTypeId = $(itemId).find('#contentTypeId');
                const cat1 = $(itemId).find('#cat1');
                const addr1 = $(itemId).find('#addr1');
                const cat3 = $(itemId).find('#cat3');

                // 데이터로 업데이트
                imagePlaceholder.html(`<img src="${item.firstimage2}" alt="${item.title}">`);
                title.text(item.title);
                overview.text(item.overview);
                contentTypeId.text(item.contenttypeid);
                cat1.text(item.cat1);
                addr1.text(item.addr1);
                cat3.text(item.cat3);
            });
        },
        error: function(error) {
            console.error('첫 번째 AJAX 요청 실패! Error:', error);
        }
    });
}
