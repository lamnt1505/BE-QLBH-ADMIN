$(document).ready(function () {
    var pageSize = 5;

    $('#pageSize').change(function () {
        pageSize = $(this).val();
        renderPagination();
    });

    function renderPagination() {
        $.ajax({
            url: "/api/v1/votes/List--get--all",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var table = $("#vote-table");
                table.empty();

                $.each(data.slice(0, pageSize), function (index, vote) {
                    var row = "<tr>" +
                        "<td>" + (index + 1) + "</td>" +
                        "<td>" + vote.rating + "</td>" +
                        "<td>" + (vote.comment || "Không có bình luận") + "</td>" +
                        "<td>" + (vote.accountID ? vote.accountID : "Khách") + "</td>" +
                        "<td>" + vote.productID + "</td>" +
                        "<td>" +
                        "<button class='btn btn-info' onclick=\"viewVoteDetails(" + vote.productVoteID + ")\"><i class='fas fa-eye'></i> Chi tiết</button>" +
                        "</td>" +
                        "</tr>";
                    table.append(row);
                });
            },
            error: function () {
                alert("Đã xảy ra lỗi khi lấy danh sách đánh giá.");
            }
        });
    }
    renderPagination();
});
