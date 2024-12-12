function pwCheck(field, value) {
    $.ajax({
        url: contextPath + '/members/pwChange',
        method: 'post',
        data: { field: field, value: value },
        success: function(response) {
            var messageElement = document.getElementById('message_' + field);
            var resultMessage = document.getElementById("resultMessage");
            
            if (response.available) {
                messageElement.innerHTML = '<span style="color:green;">사용 가능한 ' + field + '입니다.</span>';
                resultMessage.innerHTML = '';
                alert("사용 가능한 ID 입니다");
                isDuplicateCheck = 1;
                return true;
            } else {
                messageElement.innerHTML = '<span style="color:red;">이미 사용 중인 ' + field + '입니다.</span>';
                resultMessage.innerHTML = field + '아이디 입력란을 다시 확인해주세요!';
                resultMessage.style.color = "red";
                alert("이미 사용 중인 ID 입니다");
                return false;
            }
        },
        error: function() {
            console.error(field + ' 중복 확인 중 오류 발생');
        }
    });
}




function check()
{
	let currentPw = document.getElementById("currentPw").value();
	console.log(currentPw);
	
	
	
	
}