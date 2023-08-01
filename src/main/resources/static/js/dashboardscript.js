function myFunction(dashboardParamId, periodValue, typeline_select, dashboardParam) {

    // document.addEventListener('DOMContentLoaded', () => {

    let xData = [];
    let yData = [];
    let yData1 = [];
    let yData2 = 0;
    let xData2 = 0;
    let sumxx = 0;
    let sumx = 0;
    let sumxy = 0;
    let sumy = 0;
    let colors = ['#b0b691', '#aba9a9', '#8BC34A', '#c2d038',
        '#AFB42B', '#a3c5eC', '#d0d0d0', '#abbcd7',
        '#3388a4', '#aaaaaa'];
    let colorsBorder = ['#303F9F', '#00796B', '#3F51B5', '#5D4037',
        '#607D8B', '#757575', '#536DFE', '#336600',
        '#795548', '#388E3C'];
    let color = colors[Math.ceil(Math.random() * 10)];
    let colorBorder = colorsBorder[Math.ceil(Math.random() * 10)];
    let titlecolor = '#bb2020';

    axios.get('/api/dashboard/V2/' + dashboardParamId + '/' + periodValue)
        .then((response) => {
            let data = response.data;
            for (let i = 0; i < data.length; i++) {
                xData.push(data[i].date);
                yData.push(Number(data[i].value));
                sumx = sumx + i;
                sumxx = sumxx + i * i;
                sumy = sumy + Number(data[i].value);
                sumxy = sumxy + Number(data[i].value) * i;
            }
            // Вычисляем формулу прямой регресса y=a+b*x________________
            let a = (sumy * sumxx - sumx * sumxy) / (data.length * sumxx - sumx * sumx);
            let b = (data.length * sumxy - sumx * sumy) / (data.length * sumxx - sumx * sumx);
            //__________________________________________________________
            for (let i = 0; i < data.length; i++) {
                yData1.push(a + b * i);
            }
            yData2 = Number(data[data.length-1].value);
            xData2 = data[data.length-1].date;

            if (typeline_select === 'Область') {
                Highcharts.chart('container', {
                    chart: {
                        type: "area"
                    },
                    credits: {
                        enabled: false
                    },

                    title: {
                        text: '',
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    //
                    // subtitle: {
                    //     text: 'Показатели за выбранный период',
                    //     align: 'left'
                    // },

                    yAxis: {
                        title: {
                            text: ''
                        },
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },

                    xAxis: {
                        categories: xData,
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },

                    legend: {
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'top',
                        itemStyle: {
                            color: ''
                        },
                        itemHoverStyle: {
                            color: ''
                        },
                        itemHiddenStyle: {
                            color: ''
                        }
                    },

                    series: [{
                        name: dashboardParam,
                        data: yData,
                        color: colorBorder,    //цвет линии графика
                        fillColor: color,      // цвет заливки области
                        softThreshold: false,  // построение графиков не от нуля по y
                        marker: {              //
                            radius: 3          // размер точек-маркеров на графике
                        },                      //
                        dataLabels: {
                            enabled: true,
                            // rotation: -90,       // поворт подписи вертикально
                            color: '#000000',       //цвет шрифта метки-подписи каждого столбца
                            //                           align: 'right',         // расположение
                            format: '{point.y:.0f}' //формат подписи
                        }

                    }],

                })
            } else if (typeline_select === 'Гистограмма') {
                Highcharts.chart('container', {
                    chart: {
                        type: "column"
                    },
                    credits: {
                        enabled: false
                    },

                    title: {
                        text: '',
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    //
                    // subtitle: {
                    //     text: 'Показатели за выбранный период',
                    //     align: 'left'
                    // },

                    yAxis: {
                        title: {
                            text: ''
                        },
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },

                    xAxis: {
                        categories: xData,
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'top',
                        itemStyle: {
                            color: ''
                        },
                        itemHoverStyle: {
                            color: ''
                        },
                        itemHiddenStyle: {
                            color: ''
                        }
                    },
                    plotOptions: {
                        column: {
                            color: color,
                            borderColor: colorBorder
                        }
                    },

                    series: [{
                        name: dashboardParam,
                        data: yData,
                        softThreshold: false,  // построение графиков не от нуля по y
                        dataLabels: {
                            enabled: true,
                            // rotation: -90,       // поворт подписи вертикально
                            color: '#000000',       //цвет шрифта метки-подписи каждого столбца
                            //                           align: 'right',         // расположение
                            format: '{point.y:.0f}' //формат подписи
                        }
                    }
                    ]

                })
            } else if (typeline_select === 'Линия') {
                Highcharts.chart('container', {
                    chart: {
                        type: "line"
                    },
                    credits: {
                        enabled: false
                    },

                    title: {
                        text: '',
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    //
                    // subtitle: {
                    //     text: 'Показатели за выбранный период',
                    //     align: 'left'
                    // },

                    yAxis: {
                        title: {
                            text: ''
                        },
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },

                    xAxis: {
                        categories: xData,
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },

                    legend: {
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'top',
                        itemStyle: {
                            color: ''
                        },
                        itemHoverStyle: {
                            color: ''
                        },
                        itemHiddenStyle: {
                            color: ''
                        }
                    },

                    series: [{
                        name: dashboardParam,
                        data: yData,
                        color: colorBorder,
                        softThreshold: false,  // построение графиков не от нуля по y
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            format: '{point.y:.0f}' //формат подписи
                        },
                        marker: {
                            radius: 3
                        }
                    }],
                })
            } else if (typeline_select === 'Область с регрессом') {
                Highcharts.chart('container', {
                    chart: {
                        type: "area"
                    },
                    credits: {
                        enabled: false
                    },

                    title: {
                        text: '',
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    //
                    // subtitle: {
                    //     text: 'Показатели за выбранный период',
                    //     align: 'left'
                    // },

                    yAxis: {
                        title: {
                            text: ''
                        },
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },

                    xAxis: {
                        categories: xData,
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'top',
                        itemStyle: {
                            color: ''
                        },
                        itemHoverStyle: {
                            color: ''
                        },
                        itemHiddenStyle: {
                            color: ''
                        }
                    },

                    series: [{
                        name: dashboardParam,
                        data: yData,
                        color: colorBorder,    //цвет линии графика
                        fillColor: color,      // цвет заливки области
                        softThreshold: false,  // построение графиков не от нуля по y
                        marker: {              //
                            radius: 3          // размер точек-маркеров на графике
                        }                      //

                    },
                        {
                            name: 'Регресс',
                            data: yData1,
                            type: 'line',
                            color: 'red',
                            lineWidth: 2,
                            enableMouseTracking: false,
                            marker: {
                                enabled: false
                            }
                        }]

                });

            }else if (typeline_select === 'Гистограмма 3D'){
                Highcharts.chart('container', {
                    chart: {
                        type: 'column',
                        options3d: {
                            enabled: true,
                            alpha: 20,
                            beta: 10,
                            depth: 100
                        }
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: '',
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    // subtitle: {
                    //     text: 'Source: ' +
                    //         '<a href="https://www.ssb.no/en/statbank/table/08804/"' +
                    //         'target="_blank">SSB</a>',
                    //     align: 'left'
                    // },
                    plotOptions: {
                        column: {
                            depth: 30,
                            color: color
                        }
                    },
                    xAxis: {
                        categories: xData,
                        labels: {
                            skew3d: true,
                            style: {
                                fontSize: ''
                            }
                        }
                    },
                    yAxis: {
                        title: {
                            text: '',
                            margin: 15
                        },
                        labels: {
                            style: {
                                color: ''
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'center',
                        verticalAlign: 'bottom',
                        itemStyle: {
                            color: ''
                        },
                        itemHoverStyle: {
                            color: ''
                        },
                        itemHiddenStyle: {
                            color: ''
                        }
                    },
                    series: [{
                        name: dashboardParam,
                        data: yData,
                        softThreshold: false,  // построение графиков не от нуля по y
                        dataLabels: {
                            enabled: true,
                            // rotation: -90,       // поворт подписи вертикально
                            color: '#000000',       //цвет шрифта метки-подписи каждого столбца
                            //                           align: 'right',         // расположение
                            format: '{point.y:.0f}' //формат подписи
                        }
                    }]
                });

            } else if (typeline_select === 'Круг'){

                // Data retrieved from https://netmarketshare.com/
// Radialize the colors
//                 Highcharts.setOptions({
//                     colors: Highcharts.map(Highcharts.getOptions().colors, function (color) {
//                         return {
//                             radialGradient: {
//                                 cx: 0.5,
//                                 cy: 0.3,
//                                 r: 0.7
//                             },
//                             stops: [
//                                 [0, color],
//                                 [1, Highcharts.color(color).brighten(-0.3).get('rgb')] // darken
//                             ]
//                         };
//                     })
//                 });

// Build the chart
                Highcharts.chart('container', {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: xData2+'  '+dashboardParam+' сумма за '+periodValue+' дн.',
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
                    },
                    accessibility: {
                        point: {
                            valueSuffix: '%'
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.0f} %',
                                connectorColor: 'silver'
                            }
                        }
                    },
                    series: [{
                        name: 'Процент от общего',
                        data:[
                            {name: dashboardParam, y: yData2}
                        ]
                    }]
                });

            }else if (typeline_select === 'Кольцо 3D'){
                // Data retrieved from https://olympics.com/en/olympic-games/beijing-2022/medals
                Highcharts.chart('container', {
                    chart: {
                        type: 'pie',
                        options3d: {
                            enabled: true,
                            alpha: 45
                        }
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: xData2+'  '+dashboardParam+'  '+yData2,
                        align: 'left',
                        style:{
                            color: titlecolor
                        }
                    },
                    // subtitle: {
                    //     text: '3D donut in Highcharts',
                    //     align: 'left'
                    // },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
                    },
                    // accessibility: {
                    //     point: {
                    //         valueSuffix: '%'
                    //     },
                    plotOptions: {
                        pie: {
                            innerSize: 100,
                            depth: 45,
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.0f} %',
                                connectorColor: 'silver'
                            }
                        }
                    },
                    series: [{
                        name: dashboardParam,
                        data: [
                            [dashboardParam, yData2]

                        ]
                    }]
                });

            }else {

            }
        })

}
