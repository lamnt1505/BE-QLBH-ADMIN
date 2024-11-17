async function showUpdateCategoryForm(id) {
    try {
        const urlGetCategoryDetail = "/api/v1/category/" + id + "/get";
        const category = await doGetAjax(urlGetCategoryDetail);
        console.log("category", category);
        bindingCategoryToForm(category);
        $('#update-category-dialog').modal('show');
    } catch (error) {
        console.error("An error occurred:", error);
    }
}

function bindingCategoryToForm(category){
    if (category) {
        $('#id').val(category.id);
        $('#name').val(category.name);
    }
}

function saveCategory() {

    const id = $('#id').val();
    const name = $('#name').val();

    const formData = new FormData();
    formData.append('id', id);
    formData.append('name', name);

    const url = '/api/v1/category/' + id + '/update';
    doPostAjax(url, formData)
        .then(response => {
            Swal.fire({
                icon: 'success',
                title: 'Cập nhật danh mục thành công ✔️!',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                window.location.reload();
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi!',
                text: 'Đã xảy ra lỗi khi cập nhật danh mục',
            });
        });
}

function renderTable() {
    $.ajax({
        url: "/api/v1/category/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var table = $("#category-table");
            table.empty();
            table.append("");
            $.each(data, function (index, category) {
                table.append("<tr><td>" + category.name + "</td>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showCategoryDetails(" + category.id + ")\">Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdateCategoryForm(" + category.id + ")\">Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteCategory(" + category.id + ")\">Xóa</button>" +
                    "</td>" +
                    "</tr>");
            });
        },
        error: function () {
            alert("Error while retrieving categories.");
        }
    });
}

function deleteCategory(id) {
    Swal.fire({
        icon: 'warning',
        title: 'Bạn có chắc muốn xóa danh mục này không?',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            const url = '/api/v1/category/' + id + '/delete/';
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