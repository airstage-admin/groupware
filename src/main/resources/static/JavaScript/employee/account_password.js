//パスワード入力値の伏字表示・非表示切り替え

function togglePassword(inputId, icon) {
    const input = document.getElementById(inputId);
    
    if (input.type === "password") {
        input.type = "text"; // 文字を表示
        icon.style.opacity = "0.5"; // アイコンを薄くする（見た目の変化用）
    } else {
        input.type = "password"; // 伏字に戻す
        icon.style.opacity = "1.0"; // アイコンを濃くする
    }
}