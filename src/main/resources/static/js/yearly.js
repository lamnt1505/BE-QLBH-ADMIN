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
            backgroundColor: '#667fff',
            type: 'spline',
            borderRadius: 10,
        },
        title: {
            text: 'Thống Kê Hàng Năm',
            style: {
                fontSize: '20px',
                color: '#333'
            }
        },
        xAxis: {
            categories: year,
            title: {
                text: 'Năm',
                style: {
                    fontSize: '14px',
                    color: '#666'
                }
            },
            lineColor: '#999',
            tickColor: '#999'
        },
        yAxis: {
            title: {
                text: 'Số Lượng Đơn Hàng',
                style: {
                    fontSize: '14px',
                    color: '#666'
                }
            },
            gridLineDashStyle: 'dash',
            gridLineColor: '#ddd',
            tickInterval: 10,
        },
        tooltip: {
            shared: true,
            valueSuffix: ' đơn hàng'
        },
        plotOptions: {
            spline: {
                dataLabels: {
                    enabled: true,
                    style: {
                        color: '#333'
                    }
                },
                enableMouseTracking: true
            }
        },
        series: [{
            name: 'Số Lượng Đơn Hàng',
            data: orderCount,
            color: '#007bff',
            marker: {
                radius: 5,
                symbol: 'circle',
                fillColor: '#fff',
                lineWidth: 2,
                lineColor: '#007bff'
            }
        }]
    });
}