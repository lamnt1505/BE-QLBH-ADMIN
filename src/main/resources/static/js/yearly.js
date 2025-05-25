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
            backgroundColor: '#f7f9fc',
            type: 'spline',
            borderRadius: 10,
            style: {
                fontFamily: "'Roboto', sans-serif"
            }
        },
        title: {
            text: 'Thống Kê Đơn Hàng Hàng Năm',
            style: {
                fontSize: '24px',
                color: '#333',
                fontWeight: 'bold'
            }
        },
        subtitle: {
            text: 'Dữ liệu từ hệ thống đơn hàng',
            style: {
                fontSize: '14px',
                color: '#666'
            }
        },
        xAxis: {
            categories: year,
            title: {
                text: 'Năm',
                style: {
                    fontSize: '16px',
                    color: '#555'
                }
            },
            lineColor: '#ccc',
            tickColor: '#ccc',
            labels: {
                style: {
                    fontSize: '14px',
                    color: '#666'
                }
            }
        },
        yAxis: {
            title: {
                text: 'Số Lượng Đơn Hàng',
                style: {
                    fontSize: '16px',
                    color: '#555'
                }
            },
            gridLineDashStyle: 'solid',
            gridLineColor: '#e5e5e5',
            labels: {
                style: {
                    fontSize: '14px',
                    color: '#666'
                }
            },
            tickInterval: 10
        },
        tooltip: {
            shared: true,
            valueSuffix: ' đơn hàng',
            backgroundColor: '#fff',
            borderColor: '#007bff',
            borderRadius: 10,
            shadow: true,
            style: {
                fontSize: '14px',
                color: '#333'
            }
        },
        plotOptions: {
            spline: {
                dataLabels: {
                    enabled: true,
                    style: {
                        fontSize: '12px',
                        color: '#333',
                        textOutline: 'none'
                    }
                },
                enableMouseTracking: true
            }
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            itemStyle: {
                fontSize: '14px',
                fontWeight: 'normal',
                color: '#555'
            },
            itemHoverStyle: {
                color: '#000'
            }
        },
        series: [{
            name: 'Số Lượng Đơn Hàng',
            data: orderCount,
            color: '#007bff',
            marker: {
                radius: 6,
                symbol: 'circle',
                fillColor: '#fff',
                lineWidth: 2,
                lineColor: '#007bff'
            }
        }]
    });
}
