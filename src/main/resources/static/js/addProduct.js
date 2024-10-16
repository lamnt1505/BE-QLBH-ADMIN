$(document).ready(function () {
    $('#productForm').submit(function (event) {
        event.preventDefault();
        ajaxPost();
    });
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
        }).done(function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Thêm Mới Thành Công!',
                showConfirmButton: false,
                timer: 1500
            });
            location.reload();
        }).fail(function (xhr, status, error) {
            alert("Lỗi!" + error)
        })
    }
})