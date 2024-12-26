let regionSelect = null; //선택한 지역
let tourSelect = null; //선택한 관광

let previousRegion = null; //직전에 선택했던 지역
let previousTour = null; // 직전에 선택했던 관광

let regionResult = null;//얘는 나중에 AJAX에 파라미터로 보내줄 값
let tourResult = null; //얘는 나중에 AJAX에 파라미터로 보내줄 값

const regionDivs = document.querySelectorAll('.regionType div'); //지역 목록이 배열로 들어감
const tourDivs = document.querySelectorAll('.tourType div'); //모든 관광 목록이 배열로 들어감

const filterResult = document.getElementById("filterResult");
const reset = document.getElementById('reset');
const requestAPI = document.getElementById('requestAPI');

reset.addEventListener('click', function()
{
	console.log("리셋 들어옴");
	regionSelect = null;
	tourSelect = null;
	previousRegion = null;
	previousTour = null;
	regionResult = null;
	tourResult = null;
	filterResult.innerHTML = "선택한 필터 : ";
	
});


requestAPI.addEventListener('click', function()
{
	if(regionResult && tourResult)
	{
		console.log(regionResult+ ", " + tourResult);
		regionConverter(regionResult);
		tourConverter(tourResult);
		console.log(regionConverter(regionResult));
		console.log(tourConverter(tourResult));
		console.log("AJAX 요청 준비 완료");
	}
	else
	{
		console.log("필터를 아직 안골랐어요...");
	}
	
});


regionDivs.forEach(div => { //지역 배열을 반복문으로 div 하나하나 마다 click 이벤트를 지정
    div.addEventListener('click', () => {
        regionSelect = div.textContent; //클릭이 되면 해당 div 안의 value가 regionSelect의 값으로 대입됨
        
        if (previousRegion === null) { //지역 필터 처음 선택할 때
            previousRegion = regionSelect; //일단 직전 선택과 현재 선택 모두 값 담아주고
            if (tourSelect === null) { //만약 관광 타입을 아직 설정하지 않았다면
                filterResult.innerHTML = "선택한 지역 : " + regionSelect;
                regionResult = regionSelect; //지역 정보만 결과에 담는다
            } else { //만약 관광 타입이 이미 선택된 상황이라면
                filterResult.innerHTML = "선택한 지역 : " + regionSelect + ", 선택한 관광 타입 : " + tourSelect;
                regionResult = regionSelect;
                tourResult = tourSelect;
            }
        } else if (previousRegion === regionSelect) { //직전 선택과 현재 선택이 같다면
            filterResult.innerHTML = "선택한 필터 : "; //다시 초기화
            previousRegion = null; //다시 초기화
            regionSelect = null;
            regionResult = null;
            if (tourSelect === null) {
                regionResult = null; //다시 초기화
                tourResult = tourSelect; //다시 초기화
                filterResult.innerHTML = "선택한 필터 : ";
            } else {
                tourResult = tourSelect; //다시 초기화
                filterResult.innerHTML = "선택한 관광 타입 : " + tourSelect;
            }
        } else {
            previousRegion = regionSelect;
            if (tourSelect === null) {
                filterResult.innerHTML = "선택한 필터 : " + regionSelect; //방금 선택한 항목 표시하기
                regionResult = regionSelect;
            } else {
                filterResult.innerHTML = "선택한 지역 : " + regionSelect + ", 선택한 관광 타입 : " + tourSelect;
                regionResult = regionSelect;
                tourResult = tourSelect;
            }
        }
        console.log("지역 : " + regionSelect + ", 관광 : " + tourSelect);
        console.log("최종 리스트에 담긴  항목은 : " + regionResult, tourResult);
    });
});

tourDivs.forEach(div => { // 관광 배열을 반복문으로 div 하나하나 마다 click 이벤트를 지정
    div.addEventListener('click', () => {
        tourSelect = div.textContent; // 클릭이 되면 해당 div 안의 value가 tourSelect의 값으로 대입됨
        
        if (previousTour === null) { // 관광 필터 처음 선택할 때
            previousTour = tourSelect; // 일단 직전 선택과 현재 선택 모두 값 담아주고
            if (regionSelect === null) { // 만약 지역 타입을 아직 설정하지 않았다면
                filterResult.innerHTML = "선택한 관광 타입 : " + tourSelect;
                tourResult = previousTour; // 관광 정보만 결과에 담는다
            } else { // 만약 지역 타입이 이미 선택된 상황이라면
                filterResult.innerHTML = "선택한 지역 : " + regionSelect + ", 선택한 관광 타입 : " + tourSelect;
                regionResult = regionSelect;
                tourResult = tourSelect;
            }
        } else if (previousTour === tourSelect) { // 직전 선택과 현재 선택이 같다면
            filterResult.innerHTML = "선택한 필터 : "; // 다시 초기화
            previousTour = null; // 다시 초기화
            tourSelect = null;
            tourResult = null;
            if (regionSelect === null) {
                regionResult = null;
                tourResult = null; // 다시 초기화
                filterResult.innerHTML = "선택한 필터 : ";
            } else {
                regionResult = regionSelect; // 다시 초기화
                filterResult.innerHTML = "선택한 지역 : " + regionSelect;
            }
        } else {
            previousTour = tourSelect;
            if (regionSelect === null) {
                filterResult.innerHTML = "선택한 필터 : " + tourSelect; // 방금 선택한 항목 표시하기
                tourResult = tourSelect;
            } else {
                filterResult.innerHTML = "선택한 지역 : " + regionSelect + ", 선택한 관광 타입 : " + tourSelect;
                regionResult = regionSelect;
                tourResult = tourSelect;
            }
        }
        console.log("지역 : " + regionSelect + ", 관광 : " + tourSelect);
        console.log("최종 리스트에 담긴  항목은 : " + regionResult, tourResult);
    });
});



function regionConverter(regionResult)
{
	const regionCode = {
	    '서울': '1',
	    '부산': '6',
	    '대구': '4', 
	    '인천': '2',
	    '광주': '5',
	    '대전': '3',
	    '울산': '7',
	    '세종': '8',
	    '경기': '31',
	    '강원': '32',
	    '충북': '33',
	    '충남': '34',
	    '경북': '35',
	    '경남': '36',
	    '전북': '37',
	    '전남': '38',
	    '제주': '39'
	  };
	  
	  return regionCode[regionResult];
}


function tourConverter(tourResult)
{
	const tourCode = {
	    '관광지': '12',
	    '문화시설': '14',
		'축제공연' : '15',
		'레포츠' : '28',
		'쇼핑' : '38',
		'음식점' : '39'
	  };
	  
	  return tourCode[tourResult];
}

