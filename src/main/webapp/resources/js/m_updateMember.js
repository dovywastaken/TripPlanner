document.addEventListener('DOMContentLoaded', function () {
    // 필요한 요소들 가져오기
    var phone = document.getElementById("phone");

    // 전화번호 입력할 때마다 체크하기
    phone.addEventListener('input', function () {
        phoneValidator();
    });
});

function phoneValidator() {
    let phone = document.getElementById("phone").value;
    let phonePattern = /^\d{3}-\d{4}-\d{4}$/;

    var messageElement = document.getElementById('message_phone');
    if(phone === '')
    {
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