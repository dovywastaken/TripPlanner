document.addEventListener('DOMContentLoaded', function() {
    // 검색 버튼 클릭 시 검색
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
});

function keywordSearch() {
    const keyword = document.getElementById('keyword').value.trim();

    // 키워드가 비어있으면 전체 목록을 가져오도록 처리
    if (keyword === '') {
        console.log('전체 목록 검색');
        fetch(contextPath + '/admin/search?keyword=', {
            method: 'GET'
        })
        .then(response => response.json())  // JSON 응답 처리
        .then(members => {
            console.log("전체 목록 검색 결과:", members);  // 서버 응답 확인
            updateTable(members);  // 테이블 갱신
        })
        .catch(error => {
            console.error('Error:', error);
            console.log('검색 중 오류가 발생했습니다.');
        });
    } else {
        // 검색어가 있을 경우
        console.log('검색어로 필터링');
        fetch(contextPath + '/admin/search?keyword=' + encodeURIComponent(keyword), {
            method: 'GET'
        })
        .then(response => response.json())  // JSON 응답 처리
        .then(members => {
            console.log("검색 결과:", members);  // 서버 응답 확인
            updateTable(members);  // 테이블 갱신
        })
        .catch(error => {
            console.error('Error:', error);
            console.log('검색 중 오류가 발생했습니다.');
        });
    }
}

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
                <td data-column="id">${member.id}</td>
                <td data-column="name">${member.name}</td>
                <td data-column="region">${member.region}</td>
                <td data-column="sex">${member.sex}</td>
                <td data-column="birthday">${member.birthday}</td>
                <td>${member.phone1}-****-${member.phone3}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });

    // 테이블 헤더의 클릭 이벤트를 다시 설정
    const headers = document.querySelectorAll('#member th');
    headers.forEach((header, index) => {
        header.addEventListener('click', function() {
            sortTable(index); // 클릭된 열 인덱스로 정렬
        });
    });
}
