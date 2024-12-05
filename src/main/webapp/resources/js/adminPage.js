document.addEventListener('DOMContentLoaded', searchButton);

//===================================================================================================================

document.addEventListener('DOMContentLoaded', () => {
    const headers = document.querySelectorAll('#member th');
    headers.forEach((header, index) => {
        header.addEventListener('click', () => sortTable(index));
    });
});

function sortTable(n) {
    const table = document.getElementById("member");
    const rows = Array.from(table.rows).slice(1); // 첫 번째 행(헤더)은 제외하고 모든 행을 배열로 변환
    let asc = table.getAttribute("data-sort-asc") === "true"; // 현재 정렬 방향을 가져옴
    asc = !asc; // 정렬 방향을 반대로 변경
    table.setAttribute("data-sort-asc", asc); // 새 정렬 방향을 저장

    rows.sort((row1, row2) => {
        const cell1 = row1.cells[n].textContent.trim();
        const cell2 = row2.cells[n].textContent.trim();
        const comparison = isNaN(cell1) || isNaN(cell2) ? cell1.localeCompare(cell2) : cell1 - cell2;
        return asc ? comparison : -comparison;
    });

    // 정렬된 행을 테이블에 다시 추가
    rows.forEach(row => table.tBodies[0].appendChild(row));
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


// 문자열과 숫자를 분리하여 비교하는 함수
function parseMixedValue(value) {
    const result = value.match(/^([a-zA-Z]+)(\d+)$/);  // 문자열과 숫자를 분리하는 정규식
    if (result) {
        return [result[1], parseInt(result[2], 10)];  // 문자열과 숫자 반환
    }
    return [value, 0];  // 기본적으로 숫자나 문자열이 아닌 경우, 그대로 반환
}

//===================================================================================================================

function keywordSearch() {
    const keyword = document.getElementById('keyword').value.trim();

    if (keyword === '') {
        console.log('전체 목록 검색');
        fetch(contextPath + '/admin/search?keyword=', {
            method: 'GET'
        })
        .then(response => response.json())
        .then(members => {
            console.log("전체 목록 검색 결과:", members);
            updateTable(members);
        })
        .catch(error => {
            console.error('Error:', error);
            console.log('검색 중 오류가 발생했습니다.');
        });
    } else {
        console.log('검색어로 필터링');
        fetch(contextPath + '/admin/search?keyword=' + encodeURIComponent(keyword), {
            method: 'GET'
        })
        .then(response => response.json())
        .then(members => {
            console.log("검색 결과:", members);
            updateTable(members);
        })
        .catch(error => {
            console.error('Error:', error);
            console.log('검색 중 오류가 발생했습니다.');
        });
    }
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

    // 테이블 헤더의 클릭 이벤트를 다시 설정 (정렬 기능)
    const headers = document.querySelectorAll('#member th');
    headers.forEach((header, index) => {
        header.addEventListener('click', function() {
            sortTable(index); // 클릭된 열 인덱스로 정렬
        });
    });
}
