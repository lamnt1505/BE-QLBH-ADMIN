$(document).ready(function () {
    $('#btnOpenUploadExcelDialog').click(function () {
        $('#upload-excel-dialog').modal('show');
    });
});

function uploadExcelFile() {
    var excelFile = $('#excelFile')[0].files[0];
    if (!excelFile) {
        Toastify({
            text: "Vui lòng chọn tệp Excel!",
            duration: 3000,
            gravity: "top",
            position: "right",
            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
        }).showToast();
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
            Toastify({
                text: "Nhập dữ liệu thành công ✔️!",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #56ab2f, #a8e063)",
            }).showToast();
            setTimeout(() => {
                $('#upload-excel-dialog').modal('hide');
                location.reload();
            }, 1500);
        },
        error: function (error) {
            if (error.status === 200) {
                Toastify({
                    text: "Nhập dữ liệu thành công ✔️!",
                    duration: 3000,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "linear-gradient(to right, #56ab2f, #a8e063)",
                }).showToast();
                setTimeout(() => {
                    $('#upload-excel-dialog').modal('hide');
                    location.reload();
                }, 1500);
            } else {
                var errorMessage = error.responseText || "Đã xảy ra lỗi không xác định.";
                Toastify({
                    text: "Lỗi khi nhập dữ liệu: " + errorMessage,
                    duration: 5000,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                }).showToast();
            }
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