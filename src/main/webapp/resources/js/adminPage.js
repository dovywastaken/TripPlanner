document.addEventListener('DOMContentLoaded', function () {
    const tableHeaders = document.querySelectorAll('#member th');
    tableHeaders.forEach(function (header, index) {
        header.addEventListener('click', function () {
            sortTable(index);
        });
    });

    let keyword = document.getElementById("keyword");
    let search = document.getElementById("search");
    let searchButton = document.getElementById("searchButton");

    if (keyword && search && searchButton) {
        keyword.addEventListener('keydown', function (event) {
            if (event.key === "Enter" && search.tagName === "FORM") {
                search.submit();
            }
        });

        searchButton.addEventListener('click', function () {
            if (search.tagName === "FORM") {
                search.submit();
            }
        });
    }

    function sortTable(columnIndex) {
        var table = document.getElementById("member");
        var rows = table.getElementsByTagName("TR");
        var isAscending = table.getAttribute("data-sort-asc") === "true" ? false : true;
        table.setAttribute("data-sort-asc", isAscending);

        var sortableRows = [];
        for (var i = 1; i < rows.length; i++) {
            sortableRows.push(rows[i]);
        }

        sortableRows.sort(function (rowA, rowB) {
            var valueA = rowA.cells[columnIndex].innerText.trim();
            var valueB = rowB.cells[columnIndex].innerText.trim();
            var compareResult;

            if (isNaN(valueA) || isNaN(valueB)) {
                compareResult = valueA.localeCompare(valueB);
            } else {
                compareResult = Number(valueA) - Number(valueB);
            }

            if (!isAscending) {
                compareResult = -compareResult;
            }

            return compareResult;
        });

        var tbody = table.getElementsByTagName("TBODY")[0];
        for (var i = 0; i < sortableRows.length; i++) {
            tbody.appendChild(sortableRows[i]);
        }
    }
});
