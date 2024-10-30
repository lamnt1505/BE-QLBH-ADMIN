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
                        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'],
                        borderColor: '#fff',
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    title: {
                        display: true,
                        text: 'Nguồn doanh thu theo sản phẩm',
                        fontSize: 16,
                        fontColor: '#333'
                    },
                    tooltips: {
                        callbacks: {
                            label: function(tooltipItem, data) {
                                var dataset = data.datasets[tooltipItem.datasetIndex];
                                var total = dataset.data.reduce(function(previousValue, currentValue) {
                                    return previousValue + currentValue;
                                });
                                var currentValue = dataset.data[tooltipItem.index];
                                var percentage = Math.floor(((currentValue / total) * 100) + 0.5);
                                return data.labels[tooltipItem.index] + ': ' + percentage + '%';
                            }
                        }
                    },
                    legend: {
                        position: 'top',
                        labels: {
                            boxWidth: 20,
                            fontSize: 12,
                            padding: 15
                        }
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    }
                }
            });
        },
        error: function() {
            console.log('Error occurred while retrieving sales data.');
        }
    });
});