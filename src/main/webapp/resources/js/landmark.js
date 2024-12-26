let regionSelect = null; //선택한 지역
let tourSelect = null; //선택한 관광
let sigunguSelect = null; // 선택한 구 단위

let previousRegion = null; //직전에 선택했던 지역
let previousTour = null; // 직전에 선택했던 관광
let previousSigungu = null; // 직전에 선택했던 구 단위

let regionResult = 1;//얘는 나중에 AJAX에 파라미터로 보내줄 값
let tourResult = 12; //얘는 나중에 AJAX에 파라미터로 보내줄 값
let sigunguResult = null; // AJAX로 보낼 구 단위 값

const regionDivs = document.querySelectorAll('.regionType div'); //지역 목록이 배열로 들어감
const tourDivs = document.querySelectorAll('.tourType div'); //모든 관광 목록이 배열로 들어감

const filterResult = document.getElementById("filterResult");
const reset = document.getElementById('reset');
const requestAPI = document.getElementById('requestAPI');







document.addEventListener('DOMContentLoaded', function() {
    console.log("페이지 로딩");
    loadAjax();
});

function loadAjax() {
    let open_key = 'WrDDwyS8ewwsZtX%2Bw9POHX4r8rVWShuslpdt7%2Bv0hEZhVvlddHkM0eFnIi2DYxfltV0h9zHXlW6mgecGdjXqvw%3D%3D';
    let arrrange = 'A';
    let sigunguCode = 1;

    console.log("loadAjax 실행됨");
    $.ajax({
        url: "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=" + open_key,
        type: 'GET',
        dataType: 'json',
        data: {
            MobileApp: 'AppTest',
            MobileOS: 'ETC',
            listYN: 'Y',
			contentTypeId: tourConverter(tourResult),
            arrange: arrrange,
            areaCode: regionConverter(regionResult),
            sigunguCode: sigunguCode,
            _type: 'json',
            numOfRows: 3,  // 최대 8개 데이터 가져오기
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
		loadAjax();
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
		'': '1',
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


function sigunguConverter(sigunguResult) {
    const regionCode = {
        '서울': {
            '강남구': '1',
            '강동구': '2',
            '강북구': '3',
            '강서구': '4',
            '관악구': '5',
            '광진구': '6',
            '구로구': '7',
            '금천구': '8',
            '노원구': '9',
            '도봉구': '10',
            '동대문구': '11',
            '동작구': '12',
            '마포구': '13',
            '서대문구': '14',
            '서초구': '15',
            '성동구': '16',
            '성북구': '17',
            '송파구': '18',
            '양천구': '19',
            '영등포구': '20',
            '용산구': '21',
            '은평구': '22',
            '종로구': '23',
            '중구': '24',
            '중랑구': '25'
        },
        '인천': {
            '강화군': '1',
            '계양구': '2',
            '남동구': '3',
            '동구': '4',
            '미추홀구': '5',
            '부평구': '6',
            '서구': '7',
            '연수구': '8',
            '옹진군': '9',
            '중구': '10'
        },
        '강원특별자치도': {
            '강릉시': '1',
            '고성군': '2',
            '동해시': '3',
            '삼척시': '4',
            '속초시': '5',
            '양구군': '6',
            '양양군': '7',
            '영월군': '8',
            '원주시': '9',
            '인제군': '10',
            '정선군': '11',
            '철원군': '12',
            '춘천시': '13',
            '태백시': '14',
            '평창군': '15',
            '홍천군': '16',
            '화천군': '17',
            '횡성군': '18'
        },
        '충청북도': {
            '괴산군': '1',
            '단양군': '2',
            '보은군': '3',
            '영동군': '4',
            '옥천군': '5',
            '음성군': '6',
            '제천시': '7',
            '증평군': '12',
            '진천군': '8',
            '청원군': '9',
            '청주시': '10',
            '충주시': '11'
        },
        '대전': {
            '대덕구': '1',
            '동구': '2',
            '서구': '3',
            '유성구': '4',
            '중구': '5'
        },
        '충남': {
            '계룡시': '16',
            '공주시': '1',
            '금산군': '2',
            '논산시': '3',
            '당진시': '4',
            '보령시': '5',
            '부여군': '6',
            '서산시': '7',
            '서천군': '8',
            '아산시': '9',
            '예산군': '11',
            '천안시': '12',
            '청양군': '13',
            '태안군': '14',
            '홍성군': '15'
        },
        '대구': {
            '군위군': '9',
            '남구': '1',
            '달서구': '2',
            '달성군': '3',
            '동구': '4',
            '북구': '5',
            '서구': '6',
            '수성구': '7',
            '중구': '8'
        },
        '경상북도': {
            '경산시': '1',
            '경주시': '2',
            '고령군': '3',
            '구미시': '4',
            '김천시': '6',
            '문경시': '7',
            '봉화군': '8',
            '상주시': '9',
            '성주군': '10',
            '안동시': '11',
            '영덕군': '12',
            '영양군': '13',
            '영주시': '14',
            '영천시': '15',
            '예천군': '16',
            '울릉군': '17',
            '울진군': '18',
            '의성군': '19',
            '청도군': '20',
            '청송군': '21',
            '칠곡군': '22',
            '포항시': '23'
        },
        '광주': {
            '광산구': '1',
            '남구': '2',
            '동구': '3',
            '북구': '4',
            '서구': '5'
        },
        '경상남도': {
            '거제시': '1',
            '거창군': '2',
            '고성군': '3',
            '김해시': '4',
            '남해군': '5',
            '마산시': '6',
            '밀양시': '7',
            '사천시': '8',
            '산청군': '9',
            '양산시': '10',
            '의령군': '12',
            '진주시': '13',
            '진해시': '14',
            '창녕군': '15',
            '창원시': '16',
            '통영시': '17',
            '하동군': '18',
            '함안군': '19',
            '함양군': '20',
            '합천군': '21'
        },
        '부산': {
            '강서구': '1',
            '금정구': '2',
            '기장군': '3',
            '남구': '4',
            '동구': '5',
            '동래구': '6',
            '부산진구': '7',
            '북구': '8',
            '사상구': '9',
            '사하구': '10',
            '서구': '11',
            '수영구': '12',
            '연제구': '13',
            '영도구': '14',
            '중구': '15',
            '해운대구': '16'
        },
        '전북': {
            '고창군': '1',
            '군산시': '2',
            '김제시': '3',
            '남원시': '4',
            '무주군': '5',
            '부안군': '6',
            '순창군': '7',
            '완주군': '8',
            '익산시': '9',
            '임실군': '10',
            '장수군': '11',
            '전주시': '12',
            '정읍시': '13',
            '진안군': '14'
        },
        '울산': {
            '남구': '2',
            '동구': '3',
            '북구': '4',
            '울주군': '5',
            '중구': '1'
        },
        '전라남도': {
            '강진군': '1',
            '고흥군': '2',
            '곡성군': '3',
            '광양시': '4',
            '구례군': '5',
            '나주시': '6',
            '담양군': '7',
            '목포시': '8',
            '무안군': '9',
            '보성군': '10',
            '순천시': '11',
            '신안군': '12',
            '여수시': '13',
            '영광군': '16',
            '영암군': '17',
            '완도군': '18',
            '장성군': '19',
            '장흥군': '20',
            '진도군': '21',
            '함평군': '22',
            '해남군': '23',
            '화순군': '24'
        },
        '세종특별자치시': {
            '세종특별자치시': '9'
        },
        '제주': {
            '남제군': '1',
            '북제주군': '2',
            '서귀포시': '3',
            '제주시': '4'
        },
        '경기도': {
            '가평군': '1',
            '고양시': '2',
            '과천시': '3',
            '광명시': '4',
            '광주시': '5',
            '구리시': '6',
            '군포시': '7',
            '김포시': '8',
            '남양주시': '9',
            '동두천시': '10',
            '부천시': '11',
            '성남시': '12',
            '수원시': '13',
            '시흥시': '14',
            '안산시': '15',
            '안성시': '16',
            '안양시': '17',
            '양주시': '18',
            '양평군': '19',
            '여주시': '20',
            '연천군': '21',
            '오산시': '22',
            '용인시': '23',
            '의왕시': '24',
            '의정부시': '25',
            '이천시': '26',
            '파주시': '27',
            '평택시': '28',
            '포천시': '29',
            '하남시': '30',
            '화성시': '31'
        }
    };
  
    return regionCode[regionResult];
}




function tourConverter(tourResult)
{
	const tourCode = {
		'': '12',
	    '관광지': '12',
	    '문화시설': '14',
		'축제공연' : '15',
		'레포츠' : '28',
		'쇼핑' : '38',
		'음식점' : '39'
	  };
	  
	  return tourCode[tourResult];
}