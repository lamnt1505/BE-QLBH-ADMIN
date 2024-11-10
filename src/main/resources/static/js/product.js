$(document).ready(function () {
    getProducts();
});

var listProduct = [];
var origin = window.location.origin;
var pathname = $(location).attr('pathname');
pathname.indexOf(1);
pathname.toLowerCase();
pathname = pathname.split("/")[1];
var pageTotal = 0;
var pageCurrent = 6;
var pageCheck = 1;
var pageLimit = 6;
var pageProduct = 4;
var pageNext = 0;

$('.price-sale').on('price-sale', function (e) {
    $(this).val(formatCurrency(this.value.replace(/[,VNĐ]/g, '')));
}).on('keypress', function (e) {
    if (!$.isNumeric(String.fromCharCode(e.which))) e.preventDefault()
}).on('paste', function (e) {
    var cb = e.originalEvent.clipboardData || window.clipboardData;
    if (!$.isNumeric(cb.getData('text'))) e.preventDefault();
})
function formatCurrency(number) {
    var n = number.split('').reverse().join("");
    var n2 = n.replace(/\d\d\d(?!$)/g, "$&,");
    return n2.split('').reverse().join('') + 'VNĐ';
}
function detailHtml(pageTotal, max) {
    if (listProduct == null) {
        alert("Failed");
    } else {
        listPageProduct(0, max, true);
    }
}
function getProducts() {
    $.ajax({
        type: "GET",
        contentType: "application/json;",
        dataType: 'json',
        url: "/dossier-statistic/list--Product",
        success: function (result) {
            listProduct = result;
            pageTotal = listProduct.length / pageProduct;
            detailHtml(pageTotal, listProduct.length);
        },
        error: function (result) {
            console.log(result);
            alert('failed');
        }
    });
}
function listPageProduct(start, max, donit) {
    $('#dataProduct').empty();
    var j = 0;
    for (var i = start; i < max; i++) {
        if (j == pageProduct) break;
        var html = '<div class="col-md-6 col-lg-3 ftco-animate">' +
            '<div class="card">' +
            '<a href="' + origin + '/' + pathname + '/showProductSingle/' + listProduct[i]['id'] + '" class="img-profile">' +
            '<div class="img-container">' +
            '<img class="card-img-top" src="data:image/png;base64,' + listProduct[i]['imageBase64'] + '" alt="Card image cap">' +
            '</div> ' +
            '</a>' +
            '<div class="card-body">' +
            '<h5 class="card-title">' +
            '<a href="#">' + listProduct[i]['name'] + '</a>' +
            '</h5>' +
            '<div class="card-text price">' +
            '<span class="formatPrice">' + listProduct[i]['price'] + '</span> <span class="price-unit">VNĐ</span>' +
            '</div>' +
            '</div>' +
            '<div class="card-footer">' +
            '<a class="btn btn-primary buy-now procart" data-productid=' + listProduct[i]['id'] + '>' +
            '<i class="ion-ios-cart"></i> Thêm Vào Giỏ Hàng' +
            '</a>' +
            '<a class="btn btn-danger favorite-btn addToFavorite" data-productid="' + listProduct[i]['id'] + '">' +
            '<i class="ion-ios-heart"></i> Yêu Thích' +
            '</a>' +
            '<a class="btn btn-secondary compare-btn addToCompare" data-productid="' + listProduct[i]['id'] + '">' +
            '<i class="ion-ios-shuffle"></i> So Sánh' +
            '</a>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('#dataProduct').append(html);
        j++;
    }
    if (donit) {
        detailPage(0, pageTotal, "1");
    }
}
function showListProductByIdFilter() {
    var id = [];
    $.each($("input[name='categoryID']:checked"), function () {
        id.push($(this).val());
    });
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        url: "/dossier-statistic/list--ProductById--Category--Filter/" + id,
        success: function (result) {
            listProduct = result;
            pageTotal = listProduct.length / pageProduct;
            detailHtml(pageTotal, listProduct.length);
            pageCheck = 1;
        },
        error: function (result) {
            console.log(result);
            alert('failed');
        }
    });
}
function listProductNewBest() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        async: false,
        url: "/dossier-statistic/list--Product--NewBest",
        success: function (result) {
            listProduct = result;
            pageCheck = 1;
            pageTotal = listProduct.length / pageProduct;
            detailHtml(pageTotal, listProduct.length);
        },
        error: function (result) {
            console.log(result);
            alert('failedd');
        }
    });
}
function listProductPriceDesc() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        url: "/dossier-statistic/list--Product--PriceDesc",
        success: function (result) {
            listProduct = result;
            pageCheck = 1;
            pageTotal = listProduct.length / pageProduct;
            detailHtml(pageTotal, listProduct.length);
        },
        error: function (result) {
            alert('failedd');
        }
    });
}
function listProductPriceAsc() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        url: "/dossier-statistic/list--Product--PriceAsc",
        success: function (result) {
            listProduct = result;
            pageCheck = 1;
            pageTotal = listProduct.length / pageProduct;
            detailHtml(pageTotal, listProduct.length);
        },
        error: function (result) {
            alert('failedd');
        }
    });
}
function detailPage(indexPage, page) {
    $('#pageproduct').empty();
    var html =
        '<div class="col text-center">'
        + '<div class="block-27">'
        + '<button class="btn btn-primary" id="preeValue" style="margin-right:2px;">&lt;</button>'
        + '<span id="pageNumbers"></span>'
        + '<button class="btn btn-primary" id="nextValue">&gt;</button>'
        + '</div>'
        + '</div>';
    $('#pageproduct').append(html);
    var i = 0;
    for (var j = indexPage; j < page; j++) {
        if (i == 6)
            break;
        var active = "";
        var index = j + 1;
        if (i == 6) {
            item = '<button class="btn btn-primary pageNumber '
                + active + '" attr-start="0" style="margin-right:2px;" data-page="' + index + '"> + index + </button>';
        } else {
            var start = j * pageProduct - 1;
            if (start < 0) {
                start = 0;
            }
            item = '<button class="btn btn-primary pageNumber " attr-start= '
                + start + ' style="margin-right:2px;" data-page="' + index + '">' + index + '</button>'
        }
        $('#pageNumbers').append(item);
        i++;
    }
    $.each($('#pageNumbers .pageNumber'), function (i, page) {
        if ($(page).attr("data-page") == pageCheck) {
            $(page).addClass("active");
        }
    });

    if (pageTotal <= pageCurrent) $('button#nextValue').prop('disabled', true);
    if (indexPage == 0) $('button#preeValue').prop('disabled', true);

    $('.pageNumber').click(function () {
        $('#pageNumbers .pageNumber.active ').removeClass("active")
        $(this).addClass("active");
        var attr = $(this).attr("attr-start");
        listPageProduct(attr, listProduct.length, false);
        pageCheck = $(this).attr("data-page");
    });
    $('#nextValue').click(function () {
        var numberPage = pageTotal - pageCurrent;
        pageCurrent = pageCurrent + pageLimit;
        var button = $('#pageNumbers .pageNumber')[$('#pageNumbers .pageNumber').length - 0];
        pageNext = parseInt($(button).attr("data-page"));
        detailPage(pageNext, pageTotal);
    });
    $('#preeValue').click(function () {
        var numberPage = pageTotal - pageCurrent;
        pageCurrent = pageCurrent - pageLimit;
        if (pageCurrent < 0) {
            pageCurrent = 0;
        }
        var button = $('#pageNumbers .pageNumber')[$('#pageNumbers .pageNumber').length - 0];
        pageNext = pageNext - pageLimit;
        pageNext = pageNext - pageLimit;
        if (pageNext < 0) {
            pageNext = 0;
        }
        detailPage(pageNext, pageTotal);
    });
    $(document).ready(function () {
        $('#preeValue').prop('disabled', false);
        $('#nextValue').prop('disabled', false);
    });
}