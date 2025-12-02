function resetState() {
    //OKフラグをfalse（未確認状態）に戻す
    const hiddenOk = document.getElementById('isCheckOk');
    if (hiddenOk) {
        hiddenOk.value = 'false';
    }

    //メッセージが表示されていたら消す
    const msgDiv = document.getElementById('serverMessage');
    const errDiv = document.getElementById('serverError');
    if (msgDiv) msgDiv.style.display = 'none';
    if (errDiv) errDiv.style.display = 'none';

    //ボタンの活性・非活性
    toggleControls();
}

//ボタンやプルダウンの制御を行う関数
function toggleControls() {
    const itemSelect = document.getElementById('outputItem');
    const dateSelect = document.getElementById('targetYearMonth');
    const btnCheck = document.getElementById('btnCheck');

    //文字列 'true' かどうかで判定
    const isCheckOk = document.getElementById('isCheckOk').value === 'true';
    const selectedValue = itemSelect.value;

    // --- 年月セレクトボックスの制御 ---
    // 「有給管理簿(paid_leave)」の場合は年月を非アクティブ
    if (selectedValue === 'paid_leave') {
        dateSelect.disabled = true;
    } else {
        dateSelect.disabled = false;
    }

    // --- 勤怠確認ボタンの制御 ---
    // 「勤怠管理(attendance)」以外なら非アクティブ
    if (selectedValue !== 'attendance') {
        btnCheck.disabled = true;
    } else {
        // 「勤怠管理」の場合
        // OK済み(true)ならボタンは非アクティブ(disabled=true)
        // 未確認(false)ならボタンはアクティブ(disabled=false)
        btnCheck.disabled = isCheckOk;
    }
}

//画面ロード時にも実行して状態を反映
document.addEventListener('DOMContentLoaded', function() {
    toggleControls();
});