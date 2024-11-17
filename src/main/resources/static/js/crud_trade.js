async function showUpdatetrademarkForm(id) {
    try {
        const urlGetTrademarkDetail = "/api/trademark/" + id;
        const trademark = await doGetAjax(urlGetTrademarkDetail);
        bindingTrademarkToForm(trademark);
        $('#update-trademark-dialog').modal('show');
    } catch (error) {
        console.error("An error occurred:", error);
    }
}

function bindingTrademarkToForm(trademark) {
    if (trademark) {
        $('#id').val(trademark.tradeID);
        $('#name').val(trademark.tradeName);
    }
}

function saveTradeMark() {

    const id = $('#id').val();
    const name = $('#name').val();

    const data = {
        id: id,
        tradeName: name
    };

    const url = '/api/trademark/update/' + id;

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
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
            } else {
                throw new Error('Cập nhật thất bại');
            }
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
        url: "/api/trademark/gettrademark",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var table = $("#trademark-table");
            table.empty();
            table.append("");
            $.each(data, function (index, trademark) {
                table.append("<tr><td>" + trademark.tradeName + "</td>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showCategoryDetails(" + category.id + ")\">Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdatetrademarkForm(" + trademark.tradeID + ")\">Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteTrademark(" + trademark.tradeID + ")\">Xóa</button>" +
                    "</td>" +
                    "</tr>");
            });
        },
        error: function () {
            alert("Error while retrieving categories.");
        }
    });
}

function deleteTrademark(id) {
    Swal.fire({
        icon: 'warning',
        title: 'Bạn có chắc muốn xóa danh mục này không?',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            const url = '/api/trademark/delete/' + id;
            doDeleteAjax(url)
                .then(response => {
                    if (response.status === 'success') {
                        Swal.fire({
                            icon: 'success',
                            title: 'Xóa danh mục thành công ✔️!',
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() => {
                            renderTable();
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Lỗi!',
                            text: response.message
                        });
                    }
                })
                .catch(error => {
                    alert(error.message);
                });
        }
    });
}

async function doDeleteAjax(url) {
    return $.ajax({
        url: url,
        type: 'DELETE',
        dataType: 'json',
    });
}

async function doPostAjax(url, formData) {
    return $.ajax({
        url: url,
        type: 'PUT',
        dataType: 'json',
        contentType: false,
        processData: false,
        data: formData
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