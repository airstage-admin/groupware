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
			// ① 選択された「部署名の文字」を取得（例："総務"）
			            const selectedText = this.options[this.selectedIndex].text;
			            
			            // ② 表のデータ行をすべて取得
			            // （HTMLの table tbody の中にある tr を探します）
			            const rows = document.querySelectorAll('table tbody tr');
			            
			            rows.forEach(row => {
			                // ③ 各行の「部署」が書かれている列（左から2番目＝インデックス1）を取得
			                // ※HTMLでは <td>社員番号</td>, <td>部署</td>... の順なので cells[1] です
			                const deptCell = row.cells[1];
			                
			                if (deptCell) {
			                    const deptNameInRow = deptCell.textContent.trim();

			                    // ④ 判定：「部署選択」に戻した時、または部署名が一致すれば表示
			                    // ※もしプルダウンの初期値が「部署選択」という文字でない場合は、ここの文字を合わせてください
			                    if (selectedText === '部署選択' || deptNameInRow === selectedText) {
			                        row.style.display = ''; // 表示する
			                    } else {
			                        row.style.display = 'none'; // 非表示にする（消す）
			                    }
			                }
			            });
			        });
			    }   

    // ボタン要素をIDで取得
	const stopButtons = document.querySelectorAll('.stopAccountButton');
	    
	// 取得したボタンのコレクションに対してループ処理を行う
	stopButtons.forEach(button => {
	    // 各ボタンがクリックされた時の処理を設定
	    button.addEventListener('click', function() {
	        // 確認メッセージ
	        const confirmationMessage = "このアカウントの停止を実行してもよろしいですか？（停止後は、勤怠関連の資料に出力されません）";
	            
	        // 確認ダイアログを表示
	        if (confirm(confirmationMessage)) {
	            this.closest('form').submit();
	        }
	    });
	});
});