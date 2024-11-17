$(document).ready(function () {
    var pageSize = 5;
    var allProducts = [];


    $('#pageSize').change(function () {
        pageSize = $(this).val();
        renderPagination();
    });

    function renderPagination() {
        $.ajax({
            url: "/api/v1/product/Listgetall",
            type: "GET",
            dataType: "json",
            success: function (data) {
                allProducts = data;
                var table = $("#product-table");
                table.empty();
                var currentPageProducts = allProducts.slice(0, pageSize);
                $.each(currentPageProducts, function (index, product) {
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
                        "</tr>";
                    table.append(row);
                });
            },
            error: function () {
                alert("Error fetching products.");
            }
        });
    }
    renderPagination();
});
