$(document).ready(function() {
    $.ajax({
        url: '/api/v1/product/sales',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log(data)
            var progressHtml = '';
            for (var i = 0; i < data.length; i++) {
                var name = data[i].name;
                var quantitysold = data[i].quantitysold;
                var total = data[i].total;

                var percentage = Math.floor(( total / quantitysold));
                console.log(percentage)
                progressHtml += '<h4 class="small font-weight-bold">' + name + ' <span class="float-right">' + percentage + '%</span></h4>';
                progressHtml += '<div class="progress mb-4">';
                progressHtml += '<div class="progress-bar" role="progressbar" style="width: ' + percentage + '%" aria-valuenow="' + percentage + '" aria-valuemin="0" aria-valuemax="100"></div>';
                progressHtml += '</div>';
            }


            $('#progressContainer').html(progressHtml);
        },
        error: function() {
            console.log('Error occurred while retrieving statistical data.');
        }
    });
});