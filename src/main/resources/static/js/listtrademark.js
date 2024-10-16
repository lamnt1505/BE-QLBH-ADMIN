$(document).ready(function () {
    $.ajax({
        url: "/api/trademark/gettrademark",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, trademark) {
                var row = "<tr>" +
                    "<td>" + trademark.tradeName + "</td>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showCategoryDetails(" + trademark.tradeID + ")\"><i class='fas fa-check'></i> Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdatetrademarkForm(" + trademark.tradeID + ")\"><i class='fas fa-info-circle'></i> Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteTrademark(" + trademark.tradeID + ")\"><i class='fas fa-trash'></i> Xóa</button>" +
                    "</td>" +
                    "</tr>";
                $("#trademark-table").append(row);
            });
        },
        error: function () {
            alert("Error");
        }
    });
});