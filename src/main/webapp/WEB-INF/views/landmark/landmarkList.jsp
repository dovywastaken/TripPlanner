<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>관광지 정보</h1>
    <button id="test">Test</button>
    <div id="contents"></div>

    <a href="${pageContext.request.contextPath}/landmark/list/detail">상세 페이지</a>

    <script>
        let testChamber = document.getElementById("test");
        let contents = document.getElementById("contents");

        testChamber.addEventListener('click', function() {
            console.log("매핑완료");
            loadAjax();
        });

        function loadAjax() {
            let open_key = 'WrDDwyS8ewwsZtX%2Bw9POHX4r8rVWShuslpdt7%2Bv0hEZhVvlddHkM0eFnIi2DYxfltV0h9zHXlW6mgecGdjXqvw%3D%3D';

            $.ajax({
                url: "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=" + open_key,
                type: 'GET',
                dataType: 'json',
                data: {
                    MobileApp: 'AppTest',
                    MobileOS: 'ETC',
                    listYN: 'Y',
                    arrange: 'A',
                    areaCode: 36,
                    sigunguCode: 16,
                    _type: 'json',
                    numOfRows: 10,
                    pageNo: 2
                },
                success: function(response) {
                	
                	$.ajax({
                        url: contextPath + 'TripPlanner/landmark/checkCurrentPw',
                        type: 'GET',
                        dataType: 'json',
                        data: {

                        },
                        success: function(response) {
                        }
                        error: function(error) {
                            console.error('데이터 받아오기 실패! Error: ' + error);
                            contents.innerHTML = "JSON데이터 가져오기 실패";
                        }
                    });
                    console.log("데이터 받아오기 성공");
                    let items = response.response.body.items.item;
                    
                    console.log("리스폰스 데이터 왔는지 체크 " + response);
                    
                    console.log(items);
                    
                    contents.innerHTML = ""; // 초기화

                    items.forEach(function(item) {
                        let addr1 = item.addr1.split(' ')[1]; // '성산구'와 같은 시/군/구 추출
                        let content = `
                        <div style="outline: red solid 1px;">
                            <div>
                                <img src="${item.firstimage2}" alt="${item.title}" style="width:100px;height:100px;">
                                <div>
                                    <div>${item.contenttypeid}</div>
                                    <div>${item.cat1}</div>
                                </div>
                                <div>
                                    <div>${addr1}</div>
                                    <div>${item.cat3}</div>
                                </div>
                            </div>
                            <div>
                                <h2>${item.title}</h2>
                                <p>${item.overview}</p>
                            </div>
                        </div>
                        `;

                        contents.insertAdjacentHTML('beforeend', content);
                    });
                },
                error: function(error) {
                    console.error('데이터 받아오기 실패! Error: ' + error);
                    contents.innerHTML = "JSON데이터 가져오기 실패";
                }
            });
        }
    </script>
</body>
</html>
