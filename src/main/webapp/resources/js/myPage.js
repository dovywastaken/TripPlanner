document.addEventListener('DOMContentLoaded', function() {
    let checkButton = document.getElementById("email");
    checkButton.addEventListener('click', emailCheck);
});

function emailCheck() {
    let checkButton = document.getElementById("email");
    checkButton.disabled = true; // 이메일 버튼 비활성화

    // AJAX 요청을 통해 이메일 전송
    $.ajax({
        url: contextPath + '/members/email',
        method: 'GET',
        success: function(response) {
            if (response.status === "success") {
                alert("이메일이 성공적으로 전송되었습니다.");
                timer(); // 타이머 시작
            } else {
                alert("이메일 전송에 실패했습니다.");
                checkButton.disabled = false; // 이메일 전송 실패 시 버튼 다시 활성화
            }
        },
        error: function() {
            console.error('이메일 전송 중 오류 발생');
            alert('이메일 전송에 실패했습니다.');
            checkButton.disabled = false; // 오류 발생 시 버튼 다시 활성화
        }
    });
}

function timer() {
    let time = document.getElementById("remainingTime");
    let checkButton = document.getElementById("email");
    let remainingSeconds = 3 * 60; // 3분을 초 단위로 설정 (3 * 60 = 180초)

    // 첫 번째 시간 바로 갱신
    updateTime();

    // 시간 갱신을 위한 setInterval 사용
    let interval = setInterval(function() {
        remainingSeconds--;  // 1초 감소
        updateTime();

        // 시간이 다 되면 카운트다운 종료, 버튼 활성화
        if (remainingSeconds <= 0) {
            clearInterval(interval);
            checkButton.disabled = false; // 버튼 활성화
        }
    }, 1000);  // 1초마다 실행

    // 시간을 표시하는 함수
    function updateTime() {
        let minutes = Math.floor(remainingSeconds / 60);  // 분 계산
        let seconds = remainingSeconds % 60;             // 초 계산

        // 3자리로 표시 (예: 03:00)
        time.innerHTML = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    }
}
