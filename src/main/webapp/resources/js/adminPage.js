document.addEventListener('DOMContentLoaded', searchButton);

//===================================================================================================================

document.addEventListener('DOMContentLoaded', function()
{
    const tableHeaders = document.querySelectorAll('#member th');
    tableHeaders.forEach(function(tableHeaders, index) //index는 forEach함수 기본 제공 변수 //자동으로 tableHeaders 안에 있는 요소 수만큼 반복한다
	{
        tableHeaders.addEventListener('click', function() 
		{
			let testChamber = document.getElementById("test")
			console.log(testChamber);
            sortTable(index);
			
        });
    });
	
	let testChamber = document.getElementById("test")
	testChamber.addEventListener('click', () =>
		{
			console.log("hi");
		})
});


function sortTable(columnIndex) {
	
	let testChamber = document.getElementById("test")
	console.log(testChamber);
    var table = document.getElementById("member");  // 테이블 가져오기
    var rows = table.getElementsByTagName("TR");    // 테이블의 행들 가져오기 (첫 번째 행은 제외)
    var isAscending = table.getAttribute("data-sort-asc") !== "true";// 현재 정렬 방향 확인 (오름차순인지 내림차순인지)
    table.setAttribute("data-sort-asc", isAscending); // 새로운 정렬 방향 저장
    var sortableRows = []; // 정렬할 행들을 배열로 변환
    for (var i = 1; i < rows.length; i++) 
	{
        sortableRows.push(rows[i]);
    }
    sortableRows.sort(function(rowA, rowB) { // 행 정렬하기
        // 정렬할 열의 값 가져오기
        var valueA = rowA.cells[columnIndex].innerText.trim();
        var valueB = rowB.cells[columnIndex].innerText.trim();
        var compareResult; // 숫자인지 문자인지 확인하고 적절하게 비교
        if (isNaN(valueA) || isNaN(valueB)) {
            // 문자열 비교
            compareResult = valueA.localeCompare(valueB);
        } else {
            // 숫자 비교
            compareResult = valueA - valueB;
        }
        
        // 정렬 방향에 따라 결과 조정
        if (!isAscending) {
            compareResult = -compareResult;
        }
        
        return compareResult;
    });
    
    // 정렬된 행들을 테이블에 다시 추가
    var tbody = table.getElementsByTagName("TBODY")[0];
    for (var i = 0; i < sortableRows.length; i++) {
        tbody.appendChild(sortableRows[i]);
    }
}


//===================================================================================================================


// 검색 버튼 클릭 시 검색
function searchButton() {
    const searchButton = document.querySelector('input[type="button"]');
    if (searchButton) {
        searchButton.addEventListener('click', function() {
            console.log('검색 버튼 클릭됨');
            keywordSearch();  // 검색 함수 호출
        });
    }

    // 엔터키로 검색 (keydown을 사용하여 엔터키를 확인)
    const keywordInputForEnter = document.getElementById('keyword');
    if (keywordInputForEnter) {
        keywordInputForEnter.addEventListener('keydown', function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();  // 엔터키의 기본 동작 방지
                keywordSearch();  // 검색 함수 호출
                console.log('Enter 키 눌림');  // Enter 키 눌렀을 때 콘솔 출력
            }
        });
    }

    // 테이블 헤더 클릭 시 정렬 처리
    const headers = document.querySelectorAll('#member th');
    headers.forEach((header, index) => {
        header.addEventListener('click', function() {
            sortTable(index); // 클릭된 열 인덱스로 정렬
        });
    });
}

//===================================================================================================================

function keywordSearch() {
	const keyword = document.getElementById("keyword").value.trim();
    let url = contextPath + '/admin/search?keyword=';

    if (keyword !== '') {
        url += encodeURIComponent(keyword);  // 검색어가 있으면 URL에 추가
    }

    $.ajax({
        url: url,  // 요청할 URL
        type: 'GET',  // HTTP 요청 방식
        dataType: 'json',  // 서버에서 받을 데이터 형식
        success: function(members) {
            console.log(keyword === '' ? "전체 목록 검색 결과:" : "검색 결과:", members);
            updateTable(members);  // 받은 데이터를 테이블에 업데이트
        },
        error: function(error) {
            console.error('Error:', error);
            console.log('검색 중 오류가 발생했습니다.');
        }
    });
}

//===================================================================================================================

function updateTable(members) {
    const tableBody = document.querySelector('#member tbody');
    tableBody.innerHTML = '';  // 기존 테이블 행 초기화

    // 검색 결과가 없을 경우
    if (members.length === 0) {
        const noResultRow = `
            <tr>
                <td colspan="6">검색 결과가 없습니다.</td>
            </tr>
        `;
        tableBody.innerHTML = noResultRow;
        return;
    }

    // 검색 결과 테이블에 추가
    members.forEach(member => {
        const row = `
            <tr>
				<td data-column="name">${member.name}</td>
				<td data-column="id">${member.id}</td>
				<td data-column="email">${member.email}</td>
				<td data-column="region">${member.region}</td>
				<td data-column="sex">${member.sex}</td>
				<td data-column="birthday">${member.birthday}</td>
				<td>${member.phone1}-****-${member.phone3}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });

    // 테이블의 정렬 상태 초기화
    const table = document.getElementById("member");
    table.setAttribute("data-sort-asc", "true");

    // 테이블 헤더의 클릭 이벤트를 다시 설정 (정렬 기능)
    const headers = document.querySelectorAll('#member th');
    
    // 먼저 기존의 모든 클릭 이벤트 리스너 제거
    headers.forEach((header) => {
        // 이전에 추가된 모든 클릭 이벤트 리스너 제거
        header.onclick = null;
    });

    // 새로운 클릭 이벤트 리스너 추가
    headers.forEach((header, index) => {
        header.addEventListener('click', function() {
            sortTable(index); // 클릭된 열 인덱스로 정렬
        });
    });
}
