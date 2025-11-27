function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/; secure; samesite=Lax";
}

document.addEventListener('DOMContentLoaded', function() {
    const selectElement = document.getElementById('departmentCategory');

    if (selectElement) {
        selectElement.addEventListener('change', function() {
            // 選択された値を Cookie "departmentCategoryCookie" に保存
            setCookie('departmentCategoryCookie', this.value, 31); // 31日間保存
			window.location.href = '/employee_list';
        });
    }

    // ボタン要素をIDで取得
	const stopButtons = document.querySelectorAll('.stopAccountButton');
	    
	// 取得したボタンのコレクションに対してループ処理を行う
	stopButtons.forEach(button => {
	    // 各ボタンがクリックされた時の処理を設定
	    button.addEventListener('click', function() {
	        // 確認メッセージ
	        const confirmationMessage = "このアカウントの停止を実行してもよろしいですか";
	            
	        // 確認ダイアログを表示
	        if (confirm(confirmationMessage)) {
	            this.closest('form').submit();
	        }
	    });
	});
});