function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    // secure; samesite=Lax を維持
    document.cookie = name + "=" + (value || "") + expires + "; path=/; secure; samesite=Lax";
}

document.addEventListener('DOMContentLoaded', function() {
    // フォーム要素を取得
    const form = document.getElementById('attendanceForm');

    if (form) {
        form.addEventListener('submit', function(event) {
            // clockInとclockOutの現在の値を取得
			const placeWorkNameValue = document.getElementById('placeWorkName')?.value;
            const clockInValue = document.getElementById('clockIn').value;
            const clockOutValue = document.getElementById('clockOut').value;
			const breakTimeValue = document.getElementById('breakTime').value;
			const nightBreakTimeValue = document.getElementById('nightBreakTime').value;
			
            // Cookieに値をセット（31日間保存）
			if (placeWorkNameValue) {
			    setCookie('placeWorkNameCookie', placeWorkNameValue, 31);
			}
            if (clockInValue) {
                setCookie('clockInCookie', clockInValue, 31);
            }
            if (clockOutValue) {
                setCookie('clockOutCookie', clockOutValue, 31);
            }
			if (breakTimeValue) {
			    setCookie('breakTimeCookie', breakTimeValue, 31);
			}
			if (nightBreakTimeValue) {
			    setCookie('nightBreakTimeCookie', nightBreakTimeValue, 31);
			}
        });
    }
});