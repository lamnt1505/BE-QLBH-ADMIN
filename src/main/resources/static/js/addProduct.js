$(document).ready(function () {
    $('#productForm').submit(function (event) {
        event.preventDefault();
        if (validateProductForm()) {
            ajaxPost();
        }
    });
    function validateProductForm() {
        // Kiểm tra từng trường theo thứ tự ưu tiên
        if ($('#name').val().trim() === "") {
            Toastify({
                text: "Tên sản phẩm không được để trống.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false; // Dừng kiểm tra nếu lỗi
        }

        if ($('#image').get(0).files.length === 0) {
            Toastify({
                text: "Vui lòng chọn hình ảnh sản phẩm.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false;
        }

        if ($('#description').val().trim() === "") {
            Toastify({
                text: "Miêu tả không được để trống.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false;
        }

        if ($('#date_product').val() === "") {
            Toastify({
                text: "Ngày sản xuất không được để trống.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false;
        }

        var price = $('#price').val();
        if (price === "" || price <= 0) {
            Toastify({
                text: "Giá sản phẩm phải là số dương và không được để trống.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false;
        }

        if ($('#categorySelect').val() === "") {
            Toastify({
                text: "Vui lòng chọn loại sản phẩm.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false;
        }

        if ($('#trademarkSelect').val() === "") {
            Toastify({
                text: "Vui lòng chọn thương hiệu.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "red",
                stopOnFocus: true,
            }).showToast();
            return false;
        }

        return true;
    }
    function ajaxPost() {
        var formData = new FormData();
        formData.append('name', $('#name').val());
        formData.append('description', $('#description').val());
        formData.append('date_product', $('#date_product').val());
        formData.append('price', $('#price').val());
        formData.append('categoryID', $('#categorySelect').val());
        formData.append('tradeID', $('#trademarkSelect').val());

        var imageFile = $('#image')[0].files[0];
        if (imageFile) {
            formData.append('image', imageFile);
        }
        formData.forEach(item => console.log("item", item));
        $.ajax({
            type: "POST",
            enctype: "multipart/form-data",
            url: '/api/v1/product/add',
            data: formData,
            processData: false,
            contentType: false,
        })
            .done(function (response) {
                Toastify({
                    text: "Thêm mới thành công!",
                    duration: 1500,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "green",
                    stopOnFocus: true,
                }).showToast();

                setTimeout(function () {
                    window.location.href = '/manager/listProduct';
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