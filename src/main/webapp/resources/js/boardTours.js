document.addEventListener('DOMContentLoaded', function() {
    let img = document.querySelector(".pImg"); // 클래스명을 사용하셨다면 .을 붙여야 합니다.
    img.addEventListener('click', function() {
        console.log("hi");
    });
});


/*

공통으로 요청해야 하는 데이터

- detailCommon1로 요청 -
firstimage 사진
homepage 홈페이지 주소
addr1 주소
cat2 분류 2
cat3 분류 3
zipcode 우편번호
overview 설명
tel 전화번호 (없을 시 문의전화로 대체 해야함)

- detailIntro1로 요청 -
infocenter 문의전화
restdate 휴무일
parking 주차여부
chkpet 반려동물 여부
usetime 영업시간

축제 전용 데이터

- detailIntro1로 요청 -
playtime  공연시간
sponsor1 주관사
sponsor1tel 주관사 전화번호
sponsor2 주관사2
sponsor2tel 주관사 전화번호2
eventstartdate 행사 시작일
eventenddate : 행사 종료일
usetimefestival 이용요금
agelimit 연령제한

맛집 전용 데이터
- detailIntro1로 요청 -
opentimefood 영업시간
firstmenu 대표메뉴
treatmenu 취급메뉴


*/

//그냥 사진이나 제목 누르면 상세페이지로 이동하게 컨트롤러에 매핑되도록 js에서 요청하고 contentTypeId랑 contentId만 들고가는 형식으로 하자

function detailintro(contentId, contentType) {
  const detailCommon = "http://apis.data.go.kr/B551011/KorService1/detailCommon1";
  const detailIntro = "http://apis.data.go.kr/B551011/KorService1/detailIntro1";
  const key = "WrDDwyS8ewwsZtX%2Bw9POHX4r8rVWShuslpdt7%2Bv0hEZhVvlddHkM0eFnIi2DYxfltV0h9zHXlW6mgecGdjXqvw%3D%3D";
  const params = 
    "?ServiceKey=" + key +
    "&contentTypeId=" + encodeURIComponent(contentType) +
    "&contentId=" + encodeURIComponent(contentId) +
    "&MobileOS=ETC" +
    "&MobileApp=AppTest";
  
	$.ajax({
	      url: detailCommon + params,
	      method: 'GET',
	      dataType: 'json',
	      success: function(data) {
	        const items = data.response.body.items.item;
			let homepage = items.homepage;
			let 
	      },
	      error: function(err) {
	        console.error("Tour API 오류:", err);
	      }
	    });
	
	  $.ajax({
	    url: detailIntro + params,
	    method: 'GET',
	    dataType: 'json',
	    success: function(data) {
	      const items = data.response.body.items.item;
		  
	    },
	    error: function(err) {
	      console.error("Tour API 오류:", err);
	    }
	  });
  
  
  
}


