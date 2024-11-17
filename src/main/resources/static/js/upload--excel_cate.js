$(document).ready(function () {
    $('#btnOpenUploadExcelDialog').click(function () {
        $('#upload-excel-dialog').modal('show');
    });
});

function uploadExcelFile() {

    var excelFile = $('#excelFile')[0].files[0];

    if (!excelFile) {
        alert('Please select an Excel file.');
        return;
    }

    showLoadingSpinner();

    var formData = new FormData();
    formData.append('file', excelFile);

    $.ajax({
        url: '/api/v1/category/import',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Import successful!',
                confirmButtonText: 'OK'
            }).then((result) => {
                if (result.isConfirmed) {
                    $('#upload-excel-dialog').modal('hide');
                    location.reload();
                }
            });
        },
        error: function (error) {
            console.error(error);

            var errorMessage = error.responseText;

            alert('Error during import: ' + errorMessage);
        },
        complete: function () {
            hideLoadingSpinner();
        }
    });
}

function showLoadingSpinner() {
    $('#loading-spinner').show();
}

function hideLoadingSpinner() {
    $('#loading-spinner').hide();
}