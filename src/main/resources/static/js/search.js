$(document).ready(function() {
    loadDropdownData();
    $('#searchButton').on('click', function() {
        searchProducts();
    });
});

function loadDropdownData() {
    $.ajax({
        url: "/api/v1/category/Listgetall",
        method: "GET",
        dataType: "json"
    }).done(function(response) {
        response.forEach(function(category) {
            $('#category-Dropdown').append(
                '<li><label class="dropdown-item">' +
                '<input type="checkbox" class="category-checkbox" id="category-' + category.id + '" value="' + category.id + '"> ' +
                category.name +
                '</label></li>'
            );
        });
    }).fail(function(error) {
        console.error("Lỗi khi lấy dữ liệu danh mục: ", error);
    });

    $(document).ready(function() {
        $.ajax({
            url: "/api/trademark/gettrademark",
            method: "GET",
            dataType: "json"
        }).done(function(response) {
            response.forEach(function(trademark) {
                $('#trademark-Dropdown').append(
                    '<li><label class="dropdown-item">' +
                    '<input type="checkbox" class="trademark-checkbox" id="trademark-' + trademark.tradeID + '" value="' + trademark.tradeID + '"> ' +
                    trademark.tradeName +
                    '</label></li>'
                );
            });
        }).fail(function(error) {
            console.error("Lỗi khi lấy dữ liệu danh mục: ", error);
        });
    });

    $.ajax({
        url: "/api/productversion/Listgetall",
        method: "GET",
        dataType: "json"
    }).done(function(response) {
        response.forEach(function(version) {
            $('#productVersion-Dropdown').append(
                '<li><label class="dropdown-item">' +
                '<input type="checkbox" class="version-checkbox" id="version-' + version.versionID + '" value="' + version.versionID + '"> ' +
                version.memory + ' - ' + version.color +
                '</label></li>'
            );
        });
    }).fail(function(error) {
        console.error("Lỗi khi lấy dữ liệu phiên bản sản phẩm: ", error);
    });
}
$(document).ready(function() {
    $.ajax({
        url: "/api/v1/productdetail/Listgetall",
        method: "GET",
        dataType: "json"
    }).done(function(response) {
        response.forEach(function(productDetail) {
            $('#details-Dropdown').append(
                `<li>
                    <label class="dropdown-item">
                        <input type="checkbox" class="detail-checkbox" id="product-${productDetail.productDetailID}" value="${productDetail.productDetailID}">
                        <div style="margin-left: 20px;">
                            <span><strong>Camera:</strong> ${productDetail.productCamera}</span><br>
                            <span><strong>Wifi:</strong> ${productDetail.productWifi}</span><br>
                            <span><strong>Screen:</strong> ${productDetail.productScreen}</span><br>
                            <span><strong>Bluetooth:</strong> ${productDetail.productBluetooth}</span>
                        </div>
                    </label>
                </li>`
            );
        });
    }).fail(function(error) {
        console.error("Lỗi khi lấy dữ liệu danh mục: ", error);
    });
});
function searchProducts() {

    let selectedTrademarks = [];
    $('.trademark-checkbox:checked').each(function() {
        selectedTrademarks.push($(this).val());
    });

    let selectedDetails = [];
    $('.detail-checkbox:checked').each(function() {
        selectedDetails.push($(this).val());
    });

    let selectedCategories = [];
    $('.category-checkbox:checked').each(function() {
        selectedCategories.push($(this).val());
    });

    let selectedVersions = [];
    $('.version-checkbox:checked').each(function() {
        selectedVersions.push($(this).val());
    });

    let criteria = {
        tradeID: selectedTrademarks,
        categoryID: selectedCategories,
        versionID: selectedVersions,
        productDetailID: selectedDetails
    };

    console.log("Dữ liệu tìm kiếm gửi đến API:", criteria);

    $.ajax({
        url: "/dossier-statistic/search",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(criteria),
        success: function(response) {
            renderProductModal(response);
        },
        error: function(error) {
            console.error("Lỗi khi tìm kiếm sản phẩm: ", error);
        }
    });
}
function renderProductModal(products) {
    $('#productModalBody').empty();
    if (!products.content || products.content.length === 0) {
        $('#productModalBody').append( '<tr><td colspan="5">Không tìm thấy sản phẩm phù hợp</td></tr>');
    }else{
        products.content.forEach(function(product) {
            $('#productModalBody').append(
                '<tr>' +
                '<td>' +
                '<a href="/index/showProductSingle/' + product.id + '">' + product.name + '</a>' +
                '<td>' +
                '<img src="data:image/png;base64,' + product.imageBase64 + '" alt="Product Image" style="width: 50px; height: 50px;" />' +
                '</td>' +
                '<td>' + product.tradeName + '</td>' +
                '<td>' + product.categoryname + '</td>' +
                '<td>' + product.price + '</td>' +
                '</tr>'
            );
        });
    }
    $('#productModal').modal('show');
}