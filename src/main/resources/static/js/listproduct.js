$(document).ready(function () {
    $.ajax({
        url: "/api/v1/product/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, product) {
                var row = "<tr>" +
                    "<td>" + product.name + "</td>" +
                    "<td>" +
                    "<img src='data:image/png;base64," + product.imageBase64 + "' width='60' height='60'>" +
                    "</td>" +
                    "<td>" + product.description + "</td>" +
                    "<td>" + product.price + "</td>" +
                    "<td>" + product.date_product + "</td>" +
                    "<td>" + product.categoryname + "</td>" +
                    "<td>" + product.tradeName + "</td>" +
                    "<input type='hidden' name='productId' value='" + product.id + "'>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showProductDetails(" + product.id + ")\"><i class='fas fa-check'></i> Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdateProductForm(" + product.id + ")\"><i class='fas fa-info-circle'></i> Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteProduct(" + product.id + ")\"><i class='fas fa-trash'></i> Xóa</button>" +
                    "</td>" +
                    "</td>" +
                    "</tr>";
                $("#product-table").append(row);
            });
        },
        error: function () {
            alert("Error");
        }
    });
});