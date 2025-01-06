/*API에 파라미터로 보낼 데이터*/
let contentTypeId;
let contentId;

/*API 키*/
const key = "WrDDwyS8ewwsZtX%2Bw9POHX4r8rVWShuslpdt7%2Bv0hEZhVvlddHkM0eFnIi2DYxfltV0h9zHXlW6mgecGdjXqvw%3D%3D";


/*공통적으로 받아올 데이터들*/
/*detailCommon*/
let firstimage = document.getElementById('firstimage');
let title = document.getElementById('title');
let modifiedtime = document.getElementById('modifiedtime');
let addr1 = document.getElementById('addr1');
let zipcode = document.getElementById('zipcode');
let overview = document.getElementById('overview');
let addrTag = document.getElementById('addrTag');
let cat1 = document.getElementById('cat1');
let cat3 = document.getElementById('cat3');

/*축제 전용 데이터들*/
/*detailIntro*/
let sponsor1 = document.getElementById('sponsor1');
let sponsor1tel = document.getElementById('sponsor1tel');
let eventstartdate = document.getElementById('eventstartdate');
let eventenddate = document.getElementById('eventenddate');
let playtime = document.getElementById('playtime');
let usetimefestival = document.getElementById('usetimefestival');


/*관광지 전용 데이터들*/
/*detailCommon*/
let homepage = document.getElementById('homepage');

/*detailIntro*/
let infocenter = document.getElementById('infocenter');
let restdate = document.getElementById('restdate');
let expguide = document.getElementById('expguide');
let expagerange = document.getElementById('expagerange');
let usetime = document.getElementById('usetime');
let parking = document.getElementById('parking');
let chkbabycarriage = document.getElementById('chkbabycarriage');
let chkcreditcard = document.getElementById('chkcreditcard');
let chkpet = document.getElementById('chkpet');

/*음식점 전용 데이터들*/
/*detailIntro*/
let infocenterfood = document.getElementById('infocenterfood');
let parkingfood = document.getElementById('parkingfood');
let opentimefood = document.getElementById('opentimefood');
let restdatefood = document.getElementById('restdatefood');
let firstmenu = document.getElementById('firstmenu');
let treatmenu = document.getElementById('treatmenu');
let chkcreditcardfood = document.getElementById('chkcreditcardfood');
let packing = document.getElementById('packing');
let reservationfood = document.getElementById('reservationfood');

document.addEventListener('DOMContentLoaded', function() {
	const url = new URL(window.location.href); //현재 URL 가져오는 객체
	const params = new URLSearchParams(url.search); //URL의 파라미터를 찾아주는 객체
	contentId = params.get('contentId');
	contentTypeId = params.get('contentTypeId');
	
	if(contentTypeId === "12")
	{
		hideFestivalOnlyElements();
		hideRestaurantOnlyElements();
		cat1.innerText="#관광지"
	}
	else if(contentTypeId === "15")
	{
		hideTourOnlyElements();
		hideRestaurantOnlyElements();
		cat1.innerText="#축제/공연"
	}
	else if(contentTypeId === "39")
	{
		hideFestivalOnlyElements();
		hideTourOnlyElements();
		cat1.innerText="#음식점"
	}
	else
	{
		backToBoard.href = "/TripPlanner/errorPage"
	}
	
	detailCommon(contentTypeId, contentId);
	detailIntro(contentTypeId, contentId);
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
			
			/*축제 데이터 넣기*/
			sponsor1.innerText = items[0].sponsor1;
			sponsor1tel.innerText = items[0].sponsor1tel;
			eventstartdate.innerText = items[0].eventstartdate;
			eventenddate.innerText = items[0].eventenddate;
			playtime.innerText = items[0].playtime;
			usetimefestival.innerText = items[0].usetimefestival;
			
			
			/*관광지 데이터 넣기*/
			infocenter.innerText = items[0].infocenter;
			restdate.innerText = items[0].restdate;
			expguide.innerText = items[0].expguide;
			expagerange.innerText = items[0].expagerange;
			usetime.innerText = items[0].usetime;
			parking.innerText = items[0].parking;
			chkbabycarriage.innerText = items[0].chkbabycarriage;
			chkcreditcard.innerText = items[0].chkcreditcard;
			chkpet.innerText = items[0].chkpet;
			
			
			/*음식점 데이터 넣기*/
			infocenterfood.innerText = items[0].infocenterfood;
			parkingfood.innerText = items[0].parkingfood;
			opentimefood.innerText = items[0].opentimefood;
			restdatefood.innerText = items[0].restdatefood;
			firstmenu.innerText = items[0].firstmenu;
			treatmenu.innerText = items[0].treatmenu;
			chkcreditcardfood.innerText = items[0].chkcreditcardfood;
			packing.innerText = items[0].packing;
			reservationfood.innerText = items[0].reservationfood;
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
	    url: 'http://apis.data.go.kr/B551011/KorService1/detailCommon1?ServiceKey='+key  + '&_type=json&firstImageYN=Y',
	    type: 'GET',
	    dataType: 'json',
	    data: {
	        contentTypeId: encodeURIComponent(contentTypeId),
	        contentId: encodeURIComponent(contentId),
	        MobileOS: 'ETC',
	        MobileApp: 'AppTest',
	        defaultYN: 'Y',
	        areacodeYN: 'Y',
	        catcodeYN: 'Y',
	        addrinfoYN: 'Y',
	        mapinfoYN: 'Y',
	        overviewYN: 'Y'
	    },
	    success: function(response) {
	        console.log("공통정보 불러오기 성공"+ response);
			let items = response.response.body.items.item
			
			firstimage.src = items[0].firstimage;
			title.innerText =items[0].title;
			modifiedtime.innerText = "최근 업데이트 : " + formatDateTime(items[0].modifiedtime);
			addr1.innerText = items[0].addr1;
			zipcode.innerText = "(우) "+ items[0].zipcode;
			overview.innerText = items[0].overview;
			addrTag.innerText = "#" + extractAddr(items[0].addr1);
			homepage.innerHTML = items[0].homepage;
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


function extractAddr(address) {
  const districtPattern = /[가-힣]+구/g;
  const cityOrCountyPattern = /[가-힣]+(?:시|군)/g;

  const districtMatch = address.match(districtPattern);
  if (districtMatch) {
    return districtMatch[0];
  }

  const cityOrCountyMatch = address.match(cityOrCountyPattern);
  return cityOrCountyMatch ? cityOrCountyMatch[0] : null;
}

function hideFestivalOnlyElements() {
    const elements = document.getElementsByClassName('festivalOnly');
	console.log("축제 요소 가리기 호출" + elements);
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.display = 'none';
    }
}

function hideTourOnlyElements() {
    const elements = document.getElementsByClassName('tourOnly');
	console.log("관광지 요소 가리기 호출" + elements);
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.display = 'none';
    }
}

function hideRestaurantOnlyElements() {
    const elements = document.getElementsByClassName('restaurantOnly');
	console.log("음식점 요소 가리기 호출" + elements);
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.display = 'none';
    }
}





