document.addEventListener('DOMContentLoaded', function()
{
    const tableHeaders = document.querySelectorAll('#member th');
    tableHeaders.forEach(function(tableHeaders, index) //index는 forEach함수 기본 제공 변수 //자동으로 tableHeaders 안에 있는 요소 수만큼 반복한다
	{
        tableHeaders.addEventListener('click', function() 
		{
            sortTable(index);
        });
    });
	
	let keyword = document.getElementById("keyword");
	let search = document.getElementById("search");
	let searchButton = document.getElementById("searchButton");
	keyword.addEventListener('keydown', function(event)
	{
		if(event.key === "Enter")
		{
			search.submit();
		}
	});
	
	searchButton.addEventListener('click', function()
	{
		search.submit();
	});
	
	
	//테스트체임버
	let testChamber = document.getElementById("test")
	testChamber.addEventListener('click', () =>
		{
			console.log("hi");
		})
});


function sortTable(columnIndex) {
	
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