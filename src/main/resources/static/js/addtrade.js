$(document).ready(function () {
    $('#trademarkForm').submit(function (event) {
        event.preventDefault();
        addTrademark();
    });
    function addTrademark() {
        var formData = new FormData();
        formData.append('tradeName', $('#tradeName').val());

        formData.forEach(item => console.log("item", item));
        $.ajax({
            type: "POST",
            url: '/api/trademark/add',
            data: formData,
            processData: false,
            contentType: false,
        }).done(function (response) {
            Toastify({
                text: "Thêm mới thành công!",
                duration: 1500,
                gravity: "top",
                position: "right",
                backgroundColor: "green",
                stopOnFocus: true,
            }).showToast();
            setTimeout(function () {
                window.location.href = '/manager/listTrademark';
            }, 1500);
        })
            .fail(function (xhr, status, error) {
                Toastify({
                    text: "Đã xảy ra lỗi khi thêm mới sản phẩm",
                    duration: 3000,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "red",
                    stopOnFocus: true,
                }).showToast();
            });
    }
});