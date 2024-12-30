document.addEventListener("DOMContentLoaded", function () {
    const imageUpload = document.getElementById("fileImage");
    const editor = document.getElementById("editor");

    // 이미지 업로드 시 미리보기 추가
    imageUpload.addEventListener("change", function () {
        const files = this.files; // 업로드된 파일 리스트

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();

            reader.onload = function (e) {
                const imgTag = `<img src="${e.target.result}" alt="Uploaded Image" style="max-width: 100%; margin-top: 10px;">`;
                editor.innerHTML += imgTag; // 이미지 삽입
            };

            reader.onerror = function (e) {
                console.error("파일 읽기 중 오류 발생", e);
            };

            reader.readAsDataURL(file);
        }
    });

    // 폼 데이터와 리스트 데이터 처리
    const formData = JSON.parse(sessionStorage.getItem("formData"));
    const myListData = JSON.parse(sessionStorage.getItem("myListData"));

    const myListContainer = document.getElementById("myListContainer");

    // 기존 리스트 초기화
    myListContainer.innerHTML = "";

    // formData가 존재하면 폼에 값 채우기
    if (formData) {
        document.getElementById("title").value = formData.title;
        document.getElementById("editor").innerHTML = formData.editor;
    }

    // myListData가 존재하면 리스트에 항목 추가
    if (myListData) {
        myListData.forEach(myList => {
            // 각 항목을 스타일링된 div로 추가
            myListContainer.innerHTML += `
                <button class="myList-item" style="opacity: 1;">
                    <strong>${myList.id}</strong> - ${myList.addr}
                </button>
            `;
        });
    }
});

document.getElementById("open-map").addEventListener("click", function () {
    const title = document.getElementById("title").value; // 입력 필드 값 가져오기
    const editor = document.getElementById("editor").innerHTML; // 에디터 내용 가져오기

    const formData = {
        title: title || "Untitled", // 제목이 비어있으면 기본값 설정
        editor: editor || "No content" // 내용이 비어있으면 기본값 설정
    };
    
    sessionStorage.setItem("formData", JSON.stringify(formData));
    window.location.href = "Maps"; // 이동할 페이지 URL
});

document.addEventListener("DOMContentLoaded", function () {
    const formData = sessionStorage.getItem("formData");

    if (formData) {
        const parsedData = JSON.parse(formData);
        document.getElementById("title").innerText = parsedData.title || "Untitled";
        document.getElementById("editor").innerHTML = parsedData.editor || "No content";
    }
});