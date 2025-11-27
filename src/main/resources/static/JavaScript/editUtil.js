$(document).ready(function() {
    $('#employeeForm input[type="text"]').on('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault(); // Enterキーの既定の動作（フォーム送信）を停止
        }
    });
});