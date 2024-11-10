$(document).ready(function () {

    populateProductSelect();

    $('#importForm').submit(function (event) {
        event.preventDefault();
        ajaxPost();
    });

    function populateProductSelect() {
        $.ajax({
            url: '/api/v1/product/Listgetall',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $.each(data, function (index, product) {
                    $('#productSelect').append($('<option>', {
                        value: product.id,
                        text: product.name
                    }));
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Failed to get list of products');
            }
        });
    }

    function ajaxPost() {
        var formData = new FormData();
        formData.append('productId', $('#productSelect').val());
        formData.append('quantity', $('#quantity').val());
        formData.append('createDate', $('#createDate').val());
        formData.append('updateDate', $('#updateDate').val());
        formData.append('users', $('#users').val());


        formData.forEach(item => console.log("item", item));

        $.ajax({
            type: "POST",
            enctype: "multipart/form-data",
            url: '/api/v1/storage/add',
            data: formData,
            processData: false,
            contentType: false,
        })
            .done(function (response) {
                Toastify({
                    text: "Thêm mới lưu trữ thành công!",
                    duration: 1500,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "green",
                    stopOnFocus: true,
                }).showToast();

                setTimeout(function () {
                    window.location.href = '/manager/listStorage';
                }, 1500);
            })
            .fail(function (xhr, status, error) {
                Toastify({
                    text: "Đã xảy ra lỗi khi thêm mới lưu trữ",
                    duration: 3000,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "red",
                    stopOnFocus: true,
                }).showToast();
            });
    }
});
