$(document).ready(function () {
    $.ajax({
        url: "/api/v1/category/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, category) {
                var row = "<tr>" +
                    "<td>" + category.name + "</td>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showCategoryDetails(" + category.id + ")\"><i class='fas fa-check'></i> Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdateCategoryForm(" + category.id + ")\"><i class='fas fa-info-circle'></i> Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteCategory(" + category.id + ")\"><i class='fas fa-trash'></i> Xóa</button>" +
                    "</td>" +
                    "</tr>";
                $("#category-table").append(row);
            });
        },
        error: function () {
            alert("Error");
        }
    });
});