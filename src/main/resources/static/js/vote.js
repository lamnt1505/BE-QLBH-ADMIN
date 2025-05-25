$(document).ready(function () {
    let productID = null;

    $(document).on('click', '.vote-btn', function () {
        productID = $(this).data('productid');

        $('input[name="rate"]').prop('checked', false);
        $('#comment').val('');
    });

    $('#voteForm').on('submit', function (e) {
        e.preventDefault();

        const rate = $('input[name="rate"]:checked').val();
        const comment = $('#comment').val();

        if (!rate) {
            alert("Vui lòng chọn số sao để đánh giá!");
            return;
        }

        if (!productID) {
            alert("Không xác định được sản phẩm để đánh giá!");
            return;
        }
        const voteData = {
            productId: productID,
            rate: rate,
            comment: comment
        };

        $.ajax({
            url: '/api/v1/votes/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(voteData),
            success: function (response) {
                alert("Đánh giá của bạn đã được lưu thành công!");
                $('#voteModal').modal('hide');
            },
            error: function (xhr) {
                alert("Có lỗi xảy ra khi lưu đánh giá. Vui lòng thử lại sau.");
                console.error(xhr.responseText);
            }
        });
    });
});
