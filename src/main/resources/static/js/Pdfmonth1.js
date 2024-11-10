document.getElementById('btn2').addEventListener('click', function() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/v1/reports/month', true);
    xhr.responseType = 'arraybuffer';
    xhr.onload = function() {
        if (xhr.status === 200) {
            var blob = new Blob([xhr.response], { type: 'application/pdf' });
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'month-statistics.pdf';
            link.click();
            Toastify({
                text: 'Xuất File Thành Công',
                duration: 3000,
                gravity: 'top',
                position: 'right',
                backgroundColor: 'green',
                stopOnFocus: true
            }).showToast();
        } else {
            Toastify({
                text: 'Lỗi Xuất Danh Sách',
                duration: 3000,
                gravity: 'top',
                position: 'right',
                backgroundColor: 'red',
                stopOnFocus: true
            }).showToast();
        }
    };
    xhr.send();
});
