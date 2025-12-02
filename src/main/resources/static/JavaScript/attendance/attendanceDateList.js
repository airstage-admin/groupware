// クッキーに値を保存する関数
function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

// クッキーから値を読み込む関数
function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

// ユーザーが選択した値をクッキーに保存する関数 (HTMLのonchangeで呼び出す)
function saveSelection() {
    const selectElement = document.getElementById('month-year-select');
    if (selectElement) {
        // 'selected_month_year' という名前で31日クッキーに保存
        setCookie('selected_month_year', selectElement.value, 31);
		window.location.href = '/attendance_calendar';

    }
}

function saveSelection2() {
    const selectElement = document.getElementById('month-year-select');
    if (selectElement) {
        // 'selected_month_year' という名前で31日クッキーに保存
        setCookie('selected_month_year', selectElement.value, 31);
		window.location.href = '/attendance_employee_list';

    }
}

function generateMonthYearOptions() {
    const selectElement = document.getElementById('month-year-select');
    if (!selectElement) return;

    const today = new Date();
    const currentYear = today.getFullYear();
    const currentMonth = today.getMonth(); // 0-11
    const lastSelected = getCookie('selected_month_year');
	
	const prevMonthDate = new Date(currentYear, currentMonth - 1, 1);
	const pmYear = prevMonthDate.getFullYear();
	const pmMonth = prevMonthDate.getMonth() + 1;
    
    // クッキーがなければ、当月をデフォルト値とする
    const currentMonthYear = `${pmYear}-${String(pmMonth).padStart(2, '0')}`;
    const defaultMonthYear = lastSelected || currentMonthYear;

    // 選択範囲の計算 (前1年～今月)
    const startDate = new Date(currentYear, currentMonth - 12); // 12ヶ月前
    const endDate = new Date(currentYear, currentMonth -1);   // 前月

    // オプションの生成と**一時配列への追加**
    let iterDate = new Date(startDate.getFullYear(), startDate.getMonth());
    const optionsArray = []; // 👈 一時的な配列を定義
    
    while (iterDate <= endDate) {
        const year = iterDate.getFullYear();
        const month = iterDate.getMonth() + 1; // 1-12の表示月に変換
        
        // YYYY-MM 形式
        const value = `${year}-${String(month).padStart(2, '0')}`;
        const text = `${year}年${month}月`;

        const option = document.createElement('option');
        option.value = value;
        option.textContent = text;
        
        // 🌟 デフォルト選択の設定
        if (value === defaultMonthYear) {
            option.selected = true;
        }

        optionsArray.push(option); // 👈 配列に追加

        // 次の月に進める
        iterDate.setMonth(iterDate.getMonth() + 1, 1);
    }

    // **🚀 最新月からのリストにするための修正箇所**
    // 1. 配列を逆順にする (最新月が先頭になる)
    optionsArray.reverse()
    // 2. 逆順になった配列を selectElement に追加する
    optionsArray.forEach(option => {
        selectElement.appendChild(option);
    });
}

document.addEventListener('DOMContentLoaded', generateMonthYearOptions);