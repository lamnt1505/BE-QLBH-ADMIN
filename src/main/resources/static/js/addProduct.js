$(document).ready(function () {
    $('#productForm').submit(function (event) {
        event.preventDefault();
        if (validateProductForm()) {
            ajaxPost();
        }
    });
    function validateProductForm() {
        var isValid = true;

        if ($('#name').val().trim() === "") {
            alert("Tên sản phẩm không được để trống.");
            isValid = false;
        }
        if ($('#description').val().trim() === "") {
            alert("Miêu tả không được để trống.");
            isValid = false;
        }
        if ($('#date_product').val() === "") {
            alert("Ngày sản xuất không được để trống.");
            isValid = false;
        }
        var price = $('#price').val();
        if (price === "" || price <= 0) {
            alert("Giá sản phẩm phải là số dương và không được để trống.");
            isValid = false;
        }
        if ($('#categorySelect').val() === "") {
            alert("Vui lòng chọn loại sản phẩm.");
            isValid = false;
        }
        if ($('#trademarkSelect').val() === "") {
            alert("Vui lòng chọn thương hiệu.");
            isValid = false;
        }

        if ($('#image').get(0).files.length === 0) {
            alert("Vui lòng chọn hình ảnh sản phẩm.");
            isValid = false;
        }

        return isValid;
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