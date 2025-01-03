document.addEventListener('DOMContentLoaded', () => {
    const editor = document.getElementById('editor');
    const fileInput = document.getElementById('fileImage');
    const titleDiv = document.getElementById('titles');
    const titleInput = document.getElementById('title');
    const contentsInput = document.getElementById('contents');
    const mapBtn = document.getElementById('open-map');
    const myListContainer = document.getElementById('myListContainer');

    // 서식 적용 함수
    window.formatText = function(command, value = null) {
        document.execCommand(command, false, value);
        document.getElementById('editor').focus();
    };

    // 이미지 리사이즈 함수
    function makeResizable(imgContainer) {
        const img = imgContainer.querySelector('img');
        const handle = imgContainer.querySelector('.resize-handle');
        let isResizing = false;
        let startX, startY, initW, initH;

        handle.addEventListener('mousedown', e => {
            isResizing = true;
            startX = e.clientX;
            startY = e.clientY;
            initW = img.offsetWidth;
            initH = img.offsetHeight;
            document.addEventListener('mousemove', resizing);
            document.addEventListener('mouseup', stopResize);
            e.preventDefault();
        });

        function resizing(e) {
            if (!isResizing) return;
            const dx = e.clientX - startX;
            const ratio = initW / initH;
            const newW = initW + dx;
            const newH = newW / ratio;
            img.style.width = newW + 'px';
            img.style.height = newH + 'px';
        }

        function stopResize() {
            isResizing = false;
            document.removeEventListener('mousemove', resizing);
            document.removeEventListener('mouseup', stopResize);
        }
    }

    // place-info 구조 수정 함수
	// place-info 구조 수정 함수
	function fixPlaceInfoStructure(editor) {
	    const placeInfos = editor.querySelectorAll('.place-info');
	    placeInfos.forEach((info) => {
	        info.setAttribute('contenteditable', 'false');
	        info.draggable = true;
	        
	        // 드래그 이벤트 추가
	        info.addEventListener('dragstart', ev => {
	            ev.stopPropagation();
	            info.classList.add('dragging');
	            ev.dataTransfer.setData('text/plain', JSON.stringify({ type: 'place-info' }));
	        });

	        info.addEventListener('dragend', () => {
	            info.classList.remove('dragging');
	        });

	        // 삭제 버튼 재초기화
	        const locationBtn = info.querySelector('.location-btn');
	        let deleteBtn = locationBtn.querySelector('.delete-btn'); // 위치 변경: location-btn 내부에서 찾기

	        if (!deleteBtn) {
	            deleteBtn = document.createElement('button');
	            deleteBtn.type = 'button';
	            deleteBtn.className = 'delete-btn';
	            deleteBtn.textContent = '×';
	            locationBtn.appendChild(deleteBtn);
	        }

	        // 이벤트 리스너 재설정
	        deleteBtn.onclick = (evt) => {
	            evt.stopPropagation();
	            try {
	                if (locationBtn && locationBtn.dataset.info) {
	                    const itemData = JSON.parse(locationBtn.dataset.info);
	                    savedMyList.push(itemData);
	                    sessionStorage.setItem('myList', JSON.stringify(savedMyList));
	                    const wrapper = renderMyListItem(itemData, savedMyList.length - 1);
	                    myListContainer.appendChild(wrapper);
	                }
	            } catch (error) {
	                console.warn('Error handling delete:', error);
	            }
	            info.remove();
	        };
	    });
	}
    // 수정 페이지 초기화 함수
    function initializeEditPage(editor, myListContainer, savedMyList) {
        fixPlaceInfoStructure(editor);

        // 이미지 요소들 재초기화
        const imageWrappers = editor.querySelectorAll('.image-wrapper');
        imageWrappers.forEach(wrapper => {
            wrapper.setAttribute('contenteditable', 'false');
            
            // 삭제 버튼 재초기화
            const deleteBtn = wrapper.querySelector('.delete-btn');
            if (deleteBtn) {
                deleteBtn.onclick = () => wrapper.remove();
            }

            // 리사이즈 핸들 재초기화
            const container = wrapper.querySelector('.image-container');
            if (container) {
                makeResizable(container);
            }
        });
    }

    // 마이리스트 아이템 렌더링
    function renderMyListItem(item, index) {
        const wrapper = document.createElement('div');
        wrapper.className = 'mylist-wrapper';
        wrapper.innerHTML = `
            <button type="button" class="mylist-item" draggable="true">
                <div class="location-marker">📍</div>
                <div class="location-name">${item.id || '위치정보 없음'}</div>
            </button>
            <button type="button" class="delete-btn">×</button>
        `;

        const deleteBtn = wrapper.querySelector('.delete-btn');
        deleteBtn.onclick = (e) => {
            e.stopPropagation();
            const updatedList = savedMyList.filter((_, i) => i !== index);
            sessionStorage.setItem('myList', JSON.stringify(updatedList));
            wrapper.remove();
        };

        const dragBtn = wrapper.querySelector('.mylist-item');
        dragBtn.addEventListener('dragstart', (e) => {
            e.dataTransfer.setData('text/plain', JSON.stringify(item));
            e.dataTransfer.setData('index', index);
        });

        return wrapper;
    }

    // Placeholder 처리
    if (titleDiv.innerHTML === '제목') {
        titleDiv.innerHTML = '';
        titleDiv.setAttribute('placeholder', '제목을 입력하세요');
    }
    if (editor.innerHTML === '내용') {
        editor.innerHTML = '';
        editor.setAttribute('placeholder', '내용을 입력하세요');
    }

    // 세션 복원
    const savedTitle = sessionStorage.getItem('postTitle');
    const savedContent = sessionStorage.getItem('postContent');
    const savedMyList = JSON.parse(sessionStorage.getItem('myList') || '[]');

    if (savedTitle) {
        titleDiv.innerHTML = savedTitle;
        titleInput.value = savedTitle;
    }
    if (savedContent) {
        editor.innerHTML = savedContent;
        contentsInput.value = savedContent;
    }

    // 수정 페이지 초기화
    initializeEditPage(editor, myListContainer, savedMyList);

    // 마이리스트 렌더링
    if (savedMyList.length > 0) {
        myListContainer.innerHTML = '';
        savedMyList.forEach((item, i) => {
            myListContainer.appendChild(renderMyListItem(item, i));
        });
    }

    // 지도 버튼
    if (mapBtn) {
        mapBtn.addEventListener('click', () => {
            sessionStorage.setItem('postTitle', titleDiv.innerHTML);
            sessionStorage.setItem('postContent', editor.innerHTML);
            window.location.href = '/TripPlanner/Maps';
        });
    }

    // 드래그앤드롭 (에디터)
    editor.addEventListener('dragover', e => e.preventDefault());
    editor.addEventListener('drop', e => {
        e.preventDefault();
        try {
            const data = JSON.parse(e.dataTransfer.getData('text/plain'));
            if (data.type === 'place-info') {
                const draggingElement = document.querySelector('.dragging');
                if (draggingElement) {
                    editor.insertBefore(draggingElement, getDropPosition(editor, e.clientY));
                }
                return;
            }
            const item = data;
            const itemIndex = e.dataTransfer.getData('index');

            const placeInfo = document.createElement('div');
            placeInfo.className = 'place-info';
            placeInfo.draggable = true;
            placeInfo.setAttribute('contenteditable', 'false');
            placeInfo.innerHTML = `
                <div class="location-btn" data-id="${item.id || ''}" data-info='${JSON.stringify(item)}'>
                    <button type="button" class="location-name-btn">
                        📍 ${item.id || ''}
                    </button>
                    <button type="button" class="delete-btn">×</button>
                </div>
            `;

            placeInfo.addEventListener('dragstart', ev => {
                ev.stopPropagation();
                placeInfo.classList.add('dragging');
                ev.dataTransfer.setData('text/plain', JSON.stringify({ type: 'place-info' }));
            });

            placeInfo.addEventListener('dragend', () => {
                placeInfo.classList.remove('dragging');
            });

            const deleteBtn = placeInfo.querySelector('.delete-btn');
            deleteBtn.onclick = (evt) => {
                evt.stopPropagation();
                savedMyList.push(item);
                sessionStorage.setItem('myList', JSON.stringify(savedMyList));
                const wrapper = renderMyListItem(item, savedMyList.length - 1);
                myListContainer.appendChild(wrapper);
                placeInfo.remove();
            };

            editor.insertBefore(placeInfo, getDropPosition(editor, e.clientY));
            if (itemIndex) {
                savedMyList.splice(parseInt(itemIndex), 1);
                sessionStorage.setItem('myList', JSON.stringify(savedMyList));
                const target = document.querySelector(`#myListContainer .mylist-wrapper:nth-child(${parseInt(itemIndex)+1})`);
                if (target) target.remove();
            }
        } catch(err) {
            console.error(err);
        }
    });

    function getDropPosition(container, y) {
        const boxes = [...container.getElementsByClassName('place-info')];
        return boxes.find(box => {
            const rect = box.getBoundingClientRect();
            return y < rect.top + rect.height / 2;
        }) || null;
    }

    // 에디터 드래그 오버 처리
    editor.addEventListener('dragover', e => {
        e.preventDefault();
        const draggingElement = document.querySelector('.dragging');
        if (draggingElement) {
            const afterElement = getDropPosition(editor, e.clientY);
            if (afterElement) {
                editor.insertBefore(draggingElement, afterElement);
            } else {
                editor.appendChild(draggingElement);
            }
        }
    });

    // 제목 드래그 방지
    titleDiv.addEventListener('dragover', e => e.preventDefault());
    titleDiv.addEventListener('drop', e => e.preventDefault());

    // 이미지 업로드
    fileInput.addEventListener('change', e => {
        const files = Array.from(e.target.files);
        files.forEach(file => {
            const reader = new FileReader();
            reader.onload = function(ev) {
                const imgWrapper = document.createElement('div');
                imgWrapper.className = 'image-wrapper';
                imgWrapper.setAttribute('contenteditable', 'false');
                imgWrapper.innerHTML = `
                    <div class="image-container">
                        <img src="${ev.target.result}" class="resizable-image">
                        <div class="resize-handle"></div>
                    </div>
                    <button type="button" class="delete-btn">×</button>
                `;

                const delBtn = imgWrapper.querySelector('.delete-btn');
                delBtn.onclick = () => imgWrapper.remove();
                
                editor.appendChild(imgWrapper);
                makeResizable(imgWrapper.querySelector('.image-container'));
            };
            reader.readAsDataURL(file);
        });
    });

    // 실시간 동기화
    editor.addEventListener('input', () => {
        contentsInput.value = editor.innerHTML;
        sessionStorage.setItem('postContent', editor.innerHTML);
    });

    titleDiv.addEventListener('input', () => {
        titleInput.value = titleDiv.innerHTML;
        sessionStorage.setItem('postTitle', titleDiv.innerHTML);
    });

    // 내용 변경 감지
    const observer = new MutationObserver(() => {
        contentsInput.value = editor.innerHTML;
        sessionStorage.setItem('postContent', editor.innerHTML);
    });
    
    observer.observe(editor, { 
        childList: true, 
        subtree: true, 
        characterData: true,
        attributes: true 
    });

    // form 제출
    document.querySelector('form').addEventListener('submit', () => {
        titleInput.value = titleDiv.innerHTML;
        contentsInput.value = editor.innerHTML;

        sessionStorage.removeItem('postTitle');
        sessionStorage.removeItem('postContent');
        sessionStorage.removeItem('myList');
    });
});