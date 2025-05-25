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
            Toastify({
                text: "Cập nhật danh mục thành công ✔️!",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #56ab2f, #a8e063)",
            }).showToast();
            setTimeout(() => {
                window.location.reload();
            }, 1500);
        })
        .catch(error => {
            Toastify({
                text: "Đã xảy ra lỗi khi cập nhật danh mục!",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            }).showToast();
        });
}

function renderTable() {
    $.ajax({
        url: "/api/v1/category/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var table = $("#category-table1");
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
    Toastify({
        text: "Đang xử lý yêu cầu xóa...",
        duration: 3000,
        gravity: "top",
        position: "right",
        backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
    }).showToast();
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
                        Toastify({
                            text: "Xóa danh mục thành công ✔️!",
                            duration: 3000,
                            gravity: "top",
                            position: "right",
                            backgroundColor: "linear-gradient(to right, #56ab2f, #a8e063)",
                        }).showToast();
                        setTimeout(() => {
                            window.location.reload();
                        }, 1500);
                    } else {
                        Toastify({
                            text: "Lỗi: " + response.message,
                            duration: 5000,
                            gravity: "top",
                            position: "right",
                            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                        }).showToast();
                    }
                })
                .catch(error => {
                    Toastify({
                        text: "Lỗi khi xóa: " + error.message,
                        duration: 5000,
                        gravity: "top",
                        position: "right",
                        backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                    }).showToast();
                });
        } else {
            Toastify({
                text: "Hủy thao tác xóa danh mục.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #6a11cb, #2575fc)",
            }).showToast();
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