// tourApi.js
// - Tour API 호출 (searchKeyword)

const TOUR_API_KEY = "Vhwj7xm%2F998k3SEuB5KHDCgm7ZAxGf2XsB%2Bt3ZLGv10j1wsrRo%2Bl1z0SusBjgM38UX7toW942xyj5xA%2BBNqmHw%3D%3D"; // 실제 서비스키로 교체

function fetchTourApiAreaBased(areaCode, sigunguCode, contentTypeId, callback) {
  const url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
  const params = 
  "?numOfRows=5"+
  "&pageNo=1"+
  "&MobileOS=ETC"+
  "&MobileApp=AppTest"+
  "&ServiceKey="+TOUR_API_KEY+
  "&listYN=Y"+
  "&arrange=A"+
  "&contentTypeId="+contentTypeId+
  "&areaCode="+areaCode+
  "&sigunguCode="+sigunguCode+
  "&cat1="+
  "&cat2="+
  "&cat3="+
  "&_type=json";
  const fullUrl = url + params;

  fetch(fullUrl)
    .then(function(res){
      return res.json();
    })
    .then(function(data){
      const items = data.response.body.items.item;
      if (callback) {
        callback(items || []);
      }
    })
    .catch(function(err){
      console.error("Tour API 오류:", err);
      if (callback) {
        callback([]);
      }
    });
}



function detailintro(contentTypeId,contentId, callback) {
  const url = "https://apis.data.go.kr/B551011/KorService1/detailIntro1";
  const params = 
  "?ServiceKey="+TOUR_API_KEY+
  "&contentTypeId="+encodeURIComponent(contentTypeId)+
  "&contentId="+encodeURIComponent(contentId)+
  "&MobileOS=ETC"+
  "&MobileApp=AppTest"+
  "&_type=json";
  
  const fullUrl = url + params;
  console.log(fullUrl);
  fetch(fullUrl)
    .then(function(res){
      return res.json();
    })
    .then(function(data){
      const items = data.response.body.items.item;
      if (callback) {
        callback(items || []);
      }
    })
    .catch(function(err){
      console.error("Tour API 오류:", err);
      if (callback) {
        callback([]);
      }
    });
}




function detailCommon(contentId, contentTypeId, callback) {
    const url = "https://apis.data.go.kr/B551011/KorService1/detailCommon1";
    const params = 
        "?ServiceKey=" + TOUR_API_KEY +
        "&contentTypeId=" + encodeURIComponent(contentTypeId) +
        "&contentId=" + encodeURIComponent(contentId) +
        "&MobileOS=ETC" +
        "&MobileApp=AppTest" +
        "&defaultYN=Y" +
        "&firstImageYN=Y" +
        "&areacodeYN=Y" +
        "&catcodeYN=Y" +
        "&addrinfoYN=Y" +
        "&mapinfoYN=Y" +
        "&overviewYN=Y" +
        "&_type=json";

    const fullUrl = url + params;
    console.log(fullUrl);

    fetch(fullUrl)
        .then(function(res) {
            return res.json();
        })
        .then(function(data) {
            const items = data.response.body.items.item;
            if (callback) {
                callback(items || []);
            }
        })
        .catch(function(err) {
            console.error("Tour API 오류:", err);
            if (callback) {
                callback([]);
            }
        });
}



