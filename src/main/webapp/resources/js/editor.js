
function getTagText(contenttypeid) {
    switch (contenttypeid) {
        case "12": return "관광지";
        case "14": return "문화시설";
        case "15": return "축제공연";
        case "28": return "레포츠";
        case "32": return "숙박";
        case "38": return "쇼핑";
        case "39": return "맛집";
        case "1": return "카카오";
        default: return "기타";
    }
}

function renderMyList() {
    const myListContainer = document.getElementById('myListContainer');
    let myList = JSON.parse(sessionStorage.getItem('myList') || '[]');

    myListContainer.innerHTML = '';

    if (myList.length === 0) {
        myListContainer.innerHTML = '<p>저장된 장소가 없습니다.</p>';
        return;
    }

    myList.forEach((item) => {
        const listItem = document.createElement('div');
        listItem.className = 'mylist-wrapper';
        const tagText = getTagText(item.contenttypeid);
        const shortTitle = item.id.length > 11 ? item.id.substring(0, 11) + '...' : item.id;

        listItem.innerHTML = `
            <div class="mylist-item-content" data-id="${item.contentid}">
                <div class="place-title">${shortTitle} <p class="place-tag place-tag-${tagText}">${tagText}</p></div>
                <div class="place-address">${item.addr || '주소 정보 없음'}</div>
                <div class="place-tel">${item.tel || ''}</div>
                
                <button type="button" class="place-button">추가</button>
            </div>
        `;

        myListContainer.appendChild(listItem);

        listItem.querySelector('.place-button').addEventListener('click', function () {
            handleButtonClick(item);
        });
    });
}

function handleButtonClick(item) {
    const existingContent = $('#summernote').summernote('code');
    if (existingContent.includes(item.id)) {
        alert('이미 추가된 장소입니다!');
        return;
    }

    const tagText = getTagText(item.contenttypeid);
    const placeHtml = `
        <div class="place-container">
            <button type="button" class="location-name-btn" data-info='${JSON.stringify(item)}'>
                <div class="place-info" contenteditable="false">
                    <h3>${item.id}</h3>
                    <p class="p-btn" contenteditable="false">주소: ${item.addr}</p>
                </div>
                <button type="button" class="remove-button">×</button>
            </button>
        </div>
        <p><br></p>  <!-- contenteditable="false" 제거 -->
    `;

    const node = document.createElement('div');
    node.innerHTML = placeHtml;
    $('#summernote').summernote('insertNode', node);

    node.querySelector('.remove-button').addEventListener('click', function(e) {
        e.stopPropagation();
        node.remove();
        addToMyList(item);
    });

    removeFromMyList(item.contentid);
}

function removeFromMyList(contentid) {
    let myList = JSON.parse(sessionStorage.getItem('myList') || '[]');
    myList = myList.filter(item => item.contentid !== contentid);
    sessionStorage.setItem('myList', JSON.stringify(myList));

    renderMyList();
}


function addToMyList(item) {
    let myList = JSON.parse(sessionStorage.getItem('myList') || '[]');
    

    if (!myList.some(existingItem => existingItem.contentid === item.contentid)) {
        myList.push(item);
        sessionStorage.setItem('myList', JSON.stringify(myList));
    }


    renderMyList();
}



/* */

$(document).ready(function () {

    $('#titles').on('input', function() {
        const titleText = $(this).text().trim();
        $('#title').val(titleText);  // hidden input에 값 설정
    });

    $('#titles').on('focus', function() {
        if ($(this).text().trim() === '제목을 입력하세요') {
            $(this).text('');
        }
    });

    $('#titles').on('blur', function() {
        if ($(this).text().trim() === '') {
            $(this).text('제목을 입력하세요');
        }
    });

    $('#postForm').on('submit', function(e) {
        const titleText = $('#titles').text().trim();

        // 1. 제목 입력 여부 및 기본 텍스트 검증
        if (titleText === '' || titleText === '제목을 입력하세요') {
            e.preventDefault(); // 폼 제출 방지
            // 사용자에게 메시지 표시 (alert 대신 커스텀 모달 또는 UI 메시지를 사용하는 것이 좋습니다)
            alert('제목을 입력해주세요.'); // alert() 사용하지 않음
            $('#titles').focus();
            return false;
        }

        // 2. 제목 길이 검증 (20자 초과 금지)
        if (titleText.length > 40) {
            e.preventDefault(); // 폼 제출 방지
            alert('제목은 40자를 초과할 수 없습니다.'); // alert() 사용하지 않음
            $('#titles').focus();
            return false;
        }

        // 3. HTML 태그 포함 여부 검증
        // 정규식을 사용하여 HTML 태그 패턴을 확인합니다.
        // 예를 들어, <p>, <div>, <span> 등 일반적인 HTML 태그를 검사합니다.
        // 더 복잡한 태그나 속성까지 검사하려면 정규식을 더 정교하게 작성해야 합니다.
        const htmlTagRegex = /<[^>]+>/; // <로 시작해서 >로 끝나는 패턴
        if (htmlTagRegex.test(titleText)) {
            e.preventDefault(); // 폼 제출 방지
            alert('제목을 다시 확인해주세요.');
            $('#titles').focus();
            return false;
        }

        // 모든 검증을 통과하면 hidden input에 값 설정 후 폼 제출
        $('#title').val(titleText);
    });
});

/* 
$(document).ready(function () {
 
    $('#titles').on('input', function() {
        const titleText = $(this).text().trim();
        $('#title').val(titleText);  // hidden input에 값 설정
    });

    $('#titles').on('focus', function() {
        if ($(this).text().trim() === '제목을 입력하세요') {
            $(this).text('');
        }
    });

    $('#titles').on('blur', function() {
        if ($(this).text().trim() === '') {
            $(this).text('제목을 입력하세요');
        }
    });

    $('#postForm').on('submit', function(e) {
        const titleText = $('#titles').text().trim();
        
        if (titleText === '' || titleText === '제목을 입력하세요') {
            e.preventDefault();
            alert('제목을 입력해주세요.');
            $('#titles').focus();
            return false;
        }
        

        $('#title').val(titleText);
    });
});

*/

$(document).ready(function () {


    const savedData = sessionStorage.getItem('tempPostData');
    if (savedData) {
        const tempData = JSON.parse(savedData);

        if (tempData.title && tempData.title !== '제목을 입력하세요') {
            $('#titles').text(tempData.title);
            $('#title').val(tempData.title);
        }

        if (tempData.content) {
            $('#summernote').summernote('code', tempData.content);
        }

        if (tempData.isPrivate) {
            $(`input[name="isPrivate"][value="${tempData.isPrivate}"]`).prop('checked', true);
        }
        if (tempData.commentIsAllowed) {
            $(`input[name="commentIsAllowed"][value="${tempData.commentIsAllowed}"]`).prop('checked', true);
        }


    }
});

document.addEventListener('click', function(e) {

    let clickedLink = e.target.closest('a');
    
    if (clickedLink) {
        if (!clickedLink.href.includes(contextPath+'/map') && 
            !clickedLink.id.includes('open-map')) {
            sessionStorage.clear();
        }
    }
});


$('.submit-btn').click(function() {
    sessionStorage.clear();
});


$('#open-map').click(function () {
    const tempData = {
        title: $('#titles').text().trim(),
        content: $('#summernote').summernote('code'),
        isPrivate: $('input[name="isPrivate"]:checked').val(),
        commentIsAllowed: $('input[name="commentIsAllowed"]:checked').val()
    };
    sessionStorage.setItem('tempPostData', JSON.stringify(tempData));
    window.location.href = contextPath+'/map';
});
