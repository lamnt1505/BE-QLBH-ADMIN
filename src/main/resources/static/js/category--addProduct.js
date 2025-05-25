$(document).ready(function () {
    $.ajax({
        url: '/api/v1/category/Listgetall',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $.each(data, function (index, category) {
                $('#categorySelect').append($('<option>', {
                    value: category.id,
                    text: category.name
                }));
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log('Failed to get list of TradeMarks');
        }
    });
});