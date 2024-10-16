$(document).ready(function() {
    $.ajax({
        url: '/api/v1/product/sales',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var labels = [];
            var quantities = [];

            for (var i = 0; i < data.length; i++) {
                labels.push(data[i].name);
                quantities.push(data[i].quantitysold);
            }

            var canvas = document.createElement('canvas');
            canvas.id = 'pieChart';
            document.getElementById('chartContainer1').appendChild(canvas);

            var ctx = document.getElementById('pieChart').getContext('2d');
            new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                        data: quantities,
                        backgroundColor: ['red', 'green', 'yellow','blue','brow','black']
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        },
        error: function() {
            console.log('Error occurred while retrieving sales data.');
        }
    });
});