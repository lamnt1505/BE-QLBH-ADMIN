$(document).ready(function () {
    var pageSize = 5;

    $('#pageSize').change(function () {
        pageSize = $(this).val();
        renderPagination();
    });

    function renderPagination() {
        $.ajax({
            url: "/api/v1/storage/Listgetall",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var table = $("#storage-table");
                table.empty();

                $.each(data.slice(0, pageSize), function (index, storage) {
                    var productName = storage.product_name ? storage.product_name : "N/A";
                    var row = "<tr>" +
                        "<td>" + (storage.users ? storage.users : "N/A") + "</td>" +
                        "<td>" + productName + "</td>" +
                        "<td>" + (storage.createDate ? new Date(storage.createDate).toLocaleDateString() : "N/A") + "</td>" +
                        "<td>" + (storage.updateDate ? new Date(storage.updateDate).toLocaleDateString() : "N/A") + "</td>" +
                        "<td>" + (storage.quantity ? storage.quantity : "N/A") + "</td>" + // Show quantity
                        "<td>" +
                        "<button class='btn btn-info' onclick=\"showStorageDetails(" + storage.idImport  + ")\">Chi tiết</button>" +
                        "</td>" +
                        "<td>" +
                        "<button class='btn btn-primary' onclick=\"showUpdateImportForm(" + storage.idImport  + ")\">Cập nhật</button>" +
                        "</td>" +
                        "<td>" +
                        "<button class='btn btn-danger' onclick=\"deleteStorage(" + storage.idImport  + ")\">Xóa</button>" +
                        "</td>" +
                        "</tr>";
                    table.append(row);
                });
            },
            error: function () {
                alert("Error while retrieving categories.");
            }
        });
    }
    renderPagination();
});
