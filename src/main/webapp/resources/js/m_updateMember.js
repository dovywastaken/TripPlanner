document.addEventListener('DOMContentLoaded', function () {
    // 필요한 요소들 가져오기
    let phone = document.getElementById("phone");
    let name = document.getElementById("name");
    let submitButton = document.getElementById("submit");

    // 초기 상태에서 버튼 비활성화
    submitButton.disabled = true;

    // 전화번호와 닉네임 입력 이벤트 추가
    phone.addEventListener('input', function () {
        phoneValidator();
        toggleSubmitButton();
    });

    name.addEventListener('input', function () {
        nameValidator();
        toggleSubmitButton();
    });

    // 두 validation 함수가 모두 true일 때 버튼 활성화
    function toggleSubmitButton() {
        if (phoneValidator() && nameValidator()) {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        }
    }
});

function phoneValidator() {
    let phone = document.getElementById("phone").value;
    let phonePattern = /^\d{3}-\d{4}-\d{4}$/;

    var messageElement = document.getElementById('message_phone');
    if (phone === '') {
        messageElement.innerHTML = '<span style="color:red;">전화번호를 비울순 없습니다!</span>';
        return false;
    }

    if (!phonePattern.test(phone)) {
        messageElement.innerHTML = '<span style="color:red;">전화번호는 010-1234-5678 형식이어야 합니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">전화번호가 유효합니다.</span>';
        return true;
    }
}

function nameValidator() {
    let nameValue = document.getElementById("name").value.trim();
    let messageElement = document.getElementById('message_name'); 

    if (nameValue.length == 1) {
        messageElement.innerHTML = '<span style="color:red;">닉네임은 2글자 이상</span>';
        return false;
    } else if (nameValue.length == 0) {
        messageElement.innerHTML = '';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">닉네임이 유효합니다.</span>';
        return true;
    }
}