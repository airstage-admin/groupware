function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/; secure; samesite=Lax";
}

document.addEventListener('DOMContentLoaded', function() {
    const selectElement = document.getElementById('placeCategory');
    
    if (selectElement) {
        selectElement.addEventListener('change', function() {
            // 選択された値を Cookie "placeCategoryCookie" に保存
            setCookie('placeCategoryCookie', this.value, 31); // 31日間保存
			window.location.href = '/attendance_calendar';
        });
    }
});