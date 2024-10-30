async function showUpdateImportForm(idImport) {
    const urlGetImportDetail = "/api/v1/storage/" + idImport + "/get";
    const importData = await doGetAjax(urlGetImportDetail);
    console.log("importData", importData);

    const urlProduct = '/api/v1/product/Listgetall';
    const products = await doGetAjax(urlProduct);
    renderProduct(products, importData.productId);

    bindingImportToForm(importData);
    $('#update-import-dialog').modal('show');
}
function renderProduct(products, productId) {
    const select = $('#product-select');
    select.empty();
    $.each(products, function (index, product) {
        const option = $('<option>', {
            value: product.id,
            text: product.name,
            selected: product.id === productId
        });
        select.append(option);
    });
}
function bindingImportToForm(importData) {
    if (importData) {
        $('#idImport').val(importData.idImport);
        $('#users').val(importData.users);
        $('#quantity').val(importData.quantity);
        $('#createDate').val(importData.createDate);
        $('#updateDate').val(importData.updateDate);
    }
}
function updateImport() {
    const idImport = $('#idImport').val();
    const users = $('#users').val();
    const quantity = $('#quantity').val();
    const createDate = $('#createDate').val();
    const updateDate = $('#updateDate').val();
    const productId = $('#product-select').val();

    const formData = new FormData();
    formData.append('idImport', idImport);
    formData.append('users', users);
    formData.append('quantity', quantity);
    formData.append('createDate', createDate);
    formData.append('updateDate', updateDate);
    formData.append('productId', productId);

    const url = '/api/v1/storage/update/' + idImport;

    doPutAjax(url, formData)
        .then(response => {
            Toastify({
                text: 'Cập nhật nhập kho thành công ✔️!',
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
                text: 'Đã xảy ra lỗi khi cập nhật nhập kho',
                duration: 3000,
                gravity: 'top',
                position: 'right',
                backgroundColor: 'red',
                stopOnFocus: true,
            }).showToast();
        });
}
function deleteStorage(id) {
    Swal.fire({
        icon: 'warning',
        title: 'Bạn có chắc muốn xóa sản phẩm này không ?',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            const url = '/api/v1/storage/delete/' + id;
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
                            text: response.message ||'Đã xảy ra lỗi khi xóa sản phẩm',
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
function renderTable() {
    $.ajax({
        url: "/api/v1/storage/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var table = $("#storage-table");
            table.empty();
            table.append("");
            $.each(data, function (index, importData) {
                table.append("<tr><td>" + importData.users + "</td>" +
                    "<td>" + importData.quantity + "</td>" +
                    "<td>" + importData.createDate + "</td>" +
                    "<td>" + importData.updateDate + "</td>" +
                    "<td>" + importData.product_name + "</td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "</tr>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showImportDetails(" + importData.idImport + ")\">Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdateImportForm(" + importData.idImport + ")\">Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteImport(" + importData.idImport + ")\">Xóa</button>" +
                    "</td>" +
                    "</td>");
            });
        },
        error: function () {
            alert("Error while retrieving imports.");
        }
    });
}
function showImportDetails(idImport) {
    $.ajax({
        url: "/api/v1/storage/" + idImport + "/get",
        type: "GET",
        dataType: "json",
        success: function(importData) {
            $("#import-details-users").text(importData.users || "N/A");
            $("#import-details-quantity").text(importData.quantity || "N/A");
            $("#import-details-createDate").text(importData.createDate ? new Date(importData.createDate).toLocaleDateString() : "N/A");
            $("#import-details-updateDate").text(importData.updateDate ? new Date(importData.updateDate).toLocaleDateString() : "N/A");
            $("#import-details-product").text(importData.name || "N/A");

            $("#import-details-modal").modal("show");
        },
        error: function() {
            alert("Lỗi khi lấy thông tin chi tiết nhập kho.");
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


