$(document).ready(function () {
    $.ajax({
        url: '/api/trademark/gettrademark',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $.each(data, function (index, trademark) {
                $('#trademarkSelect').append($('<option>', {
                    value: trademark.tradeID,
                    text: trademark.tradeName
                }));
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log('Failed to get list of TradeMarks');
        }
    });
});