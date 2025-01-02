let contentTypeId;
let contentId;

let festivalOnly = document.querySelector('.festivalOnly');

let title = document.querySelector("#title");
let addr1Tag = document.getElementById('addr1Tag');
let cat2 = document.getElementById('cat2');
let cat3 = document.getElementById('cat3');
let firstmenu = document.getElementById('firstmenu');
let overview = document.getElementById('overview');
let modifiedtime = document.getElementById('modifiedtime');
let addr1 = document.getElementById('addr1');
let zipcode = document.getElementById('zipcode');
let image = document.getElementById('image');

let sponsor1 = document.getElementById('sponsor1');
let sponsor1tel = document.getElementById('sponsor1tel');
let infocenter = document.getElementById('infocenter');

let infocenterfood = document.getElementById('infocenterfood');

let usetime = document.getElementById('usetime');
let opentimefood = document.getElementById('opentimefood');

let restdate = document.getElementById('restdate');
let restdatefood = document.getElementById('restdatefood');

let treatmenu = document.getElementById('treatmenu');

let eventstartdate = document.getElementById('eventstartdate');
let eventenddate = document.getElementById('eventenddate');
let workTime = document.getElementById('workTime');

let playtime = document.getElementById('playtime');

let homepage = document.getElementById('homepage');

let chkpet = document.getElementById('chkpet');
let parking = document.getElementById('parking');
let parkingfood = document.getElementById('parkingfood');
let reservationfood = document.getElementById('reservationfood');
let packing = document.getElementById('packing');
let usetimefestival = document.getElementById('usetimefestival');
let chkcreditcardfood = document.getElementById('chkcreditcardfood');

const key = "WrDDwyS8ewwsZtX%2Bw9POHX4r8rVWShuslpdt7%2Bv0hEZhVvlddHkM0eFnIi2DYxfltV0h9zHXlW6mgecGdjXqvw%3D%3D";



document.addEventListener('DOMContentLoaded', function() {
	const url = new URL(window.location.href); //현재 URL 가져오는 객체
	const params = new URLSearchParams(url.search); //URL의 파라미터를 찾아주는 객체
	contentId = params.get('contentId');
	contentTypeId = params.get('contentTypeId');
	let backToBoard = document.getElementById('backToBoard');
	
	if(contentTypeId === "12")
	{
		backToBoard.href = "/TripPlanner/boardTour"
	}
	else if(contentTypeId === "15")
	{
		backToBoard.href = "/TripPlanner/boardFestival"
	}
	else if(contentTypeId === "39")
	{
		backToBoard.href = "/TripPlanner/boardRestaurant"
	}
	else
	{
		backToBoard.href = "/TripPlanner/boardFestival"
	}
	
	
	detailIntro(contentTypeId, contentId);
	detailCommon(contentTypeId, contentId);
});


function detailIntro(contentTypeId, contentId){
	console.log("개별정보 함수 호출");
	$.ajax({
	    url: 'http://apis.data.go.kr/B551011/KorService1/detailIntro1?ServiceKey=' + key  + '&_type=json',
	    type: 'GET',
	    dataType: 'json',
	    data: {
	        contentTypeId: encodeURIComponent(contentTypeId),
	        contentId: encodeURIComponent(contentId),
	        MobileOS: 'ETC',
	        MobileApp: 'AppTest'
	    },
	    success: function(response) {
			console.log('디테일정보 불러오기 성공:', response);
			let items = response.response.body.items.item; // 배열
			
			if(items[0].contenttypeid == "12") //불러온 페이지가 관광지일 때
			{
				console.log("관광지 카테고리");
				chkpet.innerText = '반려동물 입장 : ' + items[0].chkpet;
				parking.innerText = '주차 공간 : ' + items[0].parking;
				chkcreditcardfood.style.display='none';
				usetimefestival.style.display='none';
				sponsor1.innerText = '문의 및 안내 : ' + items[0].infocenter;
				if(!items[0].usetime || items[0].usetime === "")
				{
					sponsor1tel.innerHTML = '이용 시간 : 연중개방';
				}
				else
				{
					sponsor1tel.innerHTML = '이용 시간 : <br>' + items[0].usetime;
				}
				eventstartdate.innerText = '쉬는날 : ' + items[0].restdate;
			}
			if(items[0].contenttypeid == "15") //불러온 페이지가 축제일때
			{
				console.log("축제 카테고리");
				sponsor1.innerText = '주최 : ' + items[0].sponsor1;
				if (!sponsor1 || sponsor1 === "") {
				    sponsor1tel.innerText = '문의 전화 : ' + items[0].sponsor1tel;
				}
				eventstartdate.innerText = '행사기간 : '+ formatDateTime(items[0].eventstartdate) + ' ~ ' +  formatDateTime(items[0].eventenddate);
				workTime.innerText = '운영 시간 : ' + items[0].playtime;
				usetimefestival.innerText = '이용 요금 : ' + items[0].usetimefestival;
				chkpet.style.display='none';
				parking.style.display='none';
				chkcreditcardfood.style.display='none';
				
			}
			if(items[0].contenttypeid == "39") //불러온 페이지가 식당일때
			{
				console.log("식당 카테고리");
				let telCheck = document.getElementById("sponsor1tel").value;
				if (!telCheck || telCheck === "") {
				    sponsor1tel.innerText = '문의 전화 : ' + items[0].infocenterfood;
				}
				workTime.innerText = '영업 시간 : ' +  removeBrTags(items[0].opentimefood);
				restdatefood.innerText = '쉬는 날 : ' + items[0].restdatefood;
				firstmenu.innerText = '주력 메뉴 : ' + items[0].firstmenu;
				treatmenu.innerText = '메뉴 : ' + items[0].treatmenu;
				homepage.style.display='none';
				
				parkingfood.innerText = '주차 공간 : ' + items[0].parkingfood;
				chkcreditcardfood.innerText = '카드 결제 : ' + items[0].chkcreditcardfood;
				reservationfood.innerText = '예약 가능 : ' + items[0].reservationfood;
				packing.innerText = '포장 가능 : ' + items[0].packing;
			}
	    },
		error: function(xhr, status, error) {
	        console.error('Error:', error);
	        console.log('Status:', status);
	        console.log('Response:', xhr.responseText);
	    }
	});
}


function detailCommon(contentTypeId, contentId){
	console.log("공통정보 함수 호출");
	$.ajax({
	    url: 'http://apis.data.go.kr/B551011/KorService1/detailCommon1?ServiceKey='+key  + '&_type=json' ,
	    type: 'GET',
	    dataType: 'json',
	    data: {
	        contentTypeId: encodeURIComponent(contentTypeId),
	        contentId: encodeURIComponent(contentId),
	        MobileOS: 'ETC',
	        MobileApp: 'AppTest',
	        defaultYN: 'Y',
	        firstImageYN: 'Y',
	        areacodeYN: 'Y',
	        catcodeYN: 'Y',
	        addrinfoYN: 'Y',
	        mapinfoYN: 'Y',
	        overviewYN: 'Y'
	    },
	    success: function(response) {
	        console.log("공통정보 불러오기 성공"+ response);
			let items = response.response.body.items.item
			
			if(items[0].contenttypeid == "12") //불러온 페이지가 관광지일 때
			{
				console.log("관광지 카테고리");
			}
			if(items[0].contenttypeid == "15") //불러온 페이지가 축제일때
			{
				console.log("축제 카테고리");
				sponsor1tel.innerText = '문의전화 : ' + items[0].tel;
			}
			if(items[0].contenttypeid == "39") //불러온 페이지가 식당일때
			{
				console.log("식당 카테고리");
				sponsor1tel.innerText = '문의전화 : ' + items[0].tel;
			}
			
			image.src = items[0].firstimage;
			title.innerText = items[0].title;
			addr1Tag.innerText = '#' + items[0].addr1;
			cat2.innerText = '#' + items[0].cat2;
			cat3.innerText = '#' + items[0].cat3;
			overview.innerText = items[0].overview;
			modifiedtime.innerText = '업데이트  ' + formatDateTime(items[0].modifiedtime);
			addr1.innerText = items[0].addr1;
			zipcode.innerText = '(우)' + items[0].zipcode;
			homepage.href = items[0].homepage;
			homepage.innerHTML = '홈페이지 : ' + items[0].homepage;
	    },
		error: function(xhr, status, error) {
	        console.error('Error:', error);
	        console.log('Status:', status);
	        console.log('Response:', xhr.responseText);
	    }
	});
}



function formatDateTime(input) 
{
    // 년, 월, 일, 시, 분, 초를 각각 추출
    const year = input.substring(0, 4);
    const month = input.substring(4, 6);
    const day = input.substring(6, 8);

    // 포맷에 맞게 반환
    return `${year}.${month}.${day}`;
}


function removeBrTags(input) 
{
    return input.replace(/<br\s*\/?>/g, ' ');
}