async function showUpdateProductForm(id) {
    const urlGetProductDetail = "/api/v1/product/" + id + "/get";
    const product = await doGetAjax(urlGetProductDetail);
    console.log("product", product);
    const urlCategory = '/api/v1/category/Listgetall';
    const categories = await doGetAjax(urlCategory);
    renderCategory(categories, product.categoryID);
    const urlTradeMark = '/api/trademark/gettrademark';
    const trademarks = await doGetAjax(urlTradeMark);
    console.log("trademarks", trademarks);
    renderTradeMark(trademarks, product.tradeID);
    bindingProductToForm(product);
    $('#update-product-dialog').modal('show');
}
function renderCategory(categories, categoryID) {
    const select = $('#category-select');
    select.empty();
    $.each(categories, function (index, category) {
        const option = $('<option>', {
            value: category.id,
            text: category.name,
            selected: category.id === categoryID
        });
        select.append(option);
    });
}
function renderTradeMark(trademarks, tradeID) {
    const select = $('#trademark-select');
    select.empty();
    $.each(trademarks, function (index, trademark) {
        const option = $('<option>', {
            value: trademark.tradeID,
            text: trademark.tradeName,
            selected: trademark.tradeID === tradeID
        });
        select.append(option);
    });
}
function bindingProductToForm(product) {
    if (product) {
        $('#id').val(product.id);
        $('#name').val(product.name);
        $('#description').val(product.description);
        $('#price').val(product.price);
        $('#image').val(product.image);
        $('#date_product').val(product.date_product);
    }
}
function saveProduct() {
    const id = $('#id').val();
    const name = $('#name').val();
    const description = $('#description').val();
    const price = $('#price').val();
    const date_product = $('#date_product').val();
    const categoryID = $('#category-select').val();
    const tradeID = $('#trademark-select').val();
    const image = $('#image')[0].files[0];

    const formData = new FormData();
    formData.append('id', id);
    formData.append('name', name);
    formData.append('description', description);
    formData.append('price', price);
    formData.append('date_product', date_product);
    formData.append('categoryID', categoryID);
    formData.append('tradeID', tradeID);
    formData.append('image', image);

    formData.forEach(item => console.log("item", item));

    const url = '/api/v1/product/update/' + id;
    doPutAjax(url, formData)
        .then(response => {
            Toastify({
                text: 'Cập nhật sản phẩm thành công ✔️!',
                duration: 1500,
                gravity: 'top',
                position: 'right',
                backgroundColor: 'green',
                stopOnFocus: true,
            }).showToast();

            setTimeout(() => {
                window.location.reload();
            }, 1500);
        })
        .catch(error => {
            Toastify({
                text: 'Đã xảy ra lỗi khi cập nhật danh mục',
                duration: 3000,
                gravity: 'top',
                position: 'right',
                backgroundColor: 'red',
                stopOnFocus: true,
            }).showToast();
        });
}
function renderTable() {
    $.ajax({
        url: "/api/v1/product/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var table = $("#product-table");
            table.empty();
            table.append("");
            $.each(data, function (index, product) {
                table.append("<tr><td>" + product.name + "</td>" +
                    "<td>" +
                    "<img src='data:image/png;base64," + product.imageBase64 + "' width='60' height='60'>" +
                    "</td>" +
                    "<td>" + product.description + "</td>" +
                    "<td>" + product.price + "</td>" +
                    "<td>" + product.date_product + "</td>" +
                    "<td>" + product.categoryname + "</td>" +
                    "<td>" + product.tradeName + "</td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "</tr>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showProductDetails(" + product.id + ")\">Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdateProductForm(" + product.id + ")\">Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteProduct(" + product.id + ")\">Xóa</button>" +
                    "</td>" +
                    "</td>");
            });
        },
        error: function () {
            alert("Error while retrieving products.");
        }
    });
}
function showProductDetails(productId) {
    $.ajax({
        url: "/api/v1/product/" + productId + "/get",
        type: "GET",
        dataType: "json",
        success: function(product) {
            $("#product-details-image").attr("src", "data:image/png;base64," + product.imageBase64);
            $("#product-details-name").text(product.name);
            $("#product-details-description").text(product.description);
            $("#product-details-price").text(product.price);
            $("#product-details-date").text(product.date_product);
            $("#product-details-category").text(product.categoryname);
            $("#product-details-trademark").text(product.tradeName);

            $("#product-details-modal").modal("show");
        },
        error: function() {
            alert("Lỗi khi lấy thông tin chi tiết sản phẩm.");
        }
    });
}
function deleteProduct(id) {
    Swal.fire({
        icon: 'warning',
        title: 'Bạn có chắc muốn xóa sản phẩm này không ?',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            const url = '/api/v1/product/delete/' + id;
            doDeleteAjax(url)
                .then(response => {
                    if (response.status === 'success') {
                        Toastify({
                            text: 'Xóa sản phẩm thành công ❌!',
                            duration: 1500,
                            gravity: 'top',
                            position: 'right',
                            backgroundColor: 'green',
                            stopOnFocus: true,
                        }).showToast();

                        setTimeout(() => {
                            renderTable();
                        }, 1500);
                    } else {
                        Toastify({
                            text: response.message,
                            duration: 3000,
                            gravity: 'top',
                            position: 'right',
                            backgroundColor: 'red',
                            stopOnFocus: true,
                        }).showToast();
                    }
                })
                .catch(error => {
                    Toastify({
                        text: 'Đã xảy ra lỗi khi thực hiện xóa sản phẩm',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                });
        }
    });
}
async function doGetAjax(url, params = {}) {
    return $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data: params
    });
}
async function doPutAjax(url, formData) {
    return $.ajax({
        url: url,
        type: 'PUT',
        dataType: 'json',
        contentType: false,
        processData: false,
        data: formData
    });
}
async function doDeleteAjax(url) {
    return $.ajax({
        url: url,
        type: 'DELETE',
        dataType: 'json',
    });
}