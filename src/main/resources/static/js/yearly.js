$(document).ready(function() {
    $.ajax({
        url: '/api/v1/product/yearly',
        method: 'GET',
        dataType: 'json',
        success: function(data) {

            renderChart(data);
        },
        error: function() {
            alert('Failed to fetch statistics data.');
        }
    });
});

function renderChart(data) {
    var year = data.map(item => item.year);
    var orderCount = data.map(item => item.orderCount);

    Highcharts.chart('container', {
        chart: {
            type: 'spline'
        },
        title: {
            text: 'Yearly Statistics'
        },
        xAxis: {
            categories: year,
            title: {
                text: 'Year'
            }
        },
        yAxis: {
            title: {
                text: 'Order Count'
            }
        },
        series: [{
            name: 'Order Count',
            data: orderCount
        }]
    });
}