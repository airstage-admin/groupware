document.addEventListener('DOMContentLoaded', function() {
    const outputItemSelect = document.getElementById('outputItem');
    const yearMonthSelect = document.getElementById('month-year-select');

    if (!outputItemSelect || !yearMonthSelect) { 
        return;
    }

    function toggleYearMonthState() {
        const isPaidLeaveSelected = (outputItemSelect.value === 'paid_leave');
        yearMonthSelect.disabled = isPaidLeaveSelected;
    }

    outputItemSelect.addEventListener('change', toggleYearMonthState);
    toggleYearMonthState();
});