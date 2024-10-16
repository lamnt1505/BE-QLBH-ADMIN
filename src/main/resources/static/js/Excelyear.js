$(document).ready(function() {
    $('#exportExcel').click(function() {
        $.ajax({
            url: '/api/export/year',
            type: 'GET',
            xhrFields: {
                responseType: 'blob'
            },
            success: function(response, status, xhr) {
                var filename = "";
                var disposition = xhr.getResponseHeader('Content-Disposition');
                if (disposition && disposition.indexOf('attachment') !== -1) {
                    var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                    var matches = filenameRegex.exec(disposition);
                    if (matches != null && matches[1]) {
                        filename = matches[1].replace(/['"]/g, '');
                    }
                }
                var blob = new Blob([response], { type: 'application/octet-stream' });
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = filename || 'statistical_year.xlsx';
                link.click();

                Toastify({
                    text: 'Xuất File Thành Công',
                    duration: 3000,
                    gravity: 'top',
                    position: 'right',
                    backgroundColor: 'green',
                    stopOnFocus: true
                }).showToast();
            },
            error: function(xhr, status, error) {
                console.log('Error:', error);
                Toastify({
                    text: 'Export Failed',
                    duration: 3000,
                    gravity: 'top',
                    position: 'right',
                    backgroundColor: 'red',
                    stopOnFocus: true
                }).showToast();
            }
        });
    });
});
