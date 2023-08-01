function myFunction2(groupParamId, periodValue, typeline_select, groupParamName) {

    let sdata = [];
    let dat = [];
    let xData = [];
    let yData = [];
    let dashboardParam = [];
    let showInLegend = [];
    let sumy = [];
    let resy = [];
    let colors = ['#b0b691', '#aba9a9', '#8BC34A', '#c3d037', '#AFB42B', '#B3E5FC', '#F5F5F5', '#DCEDC8', '#03A9F4', '#FFF9C4'];
    let colorsBorder = ['#2e3e9f', '#00796B', '#3d4fbb', '#5D4037', '#607D8B', '#757575', '#536DFE', '#336600', '#795548', '#388E3C'];
    let titlecolor = '#bb2020';
    let paramListId = [];
    let paramListIcon = [];
    let datId =[];

    axios.get('/api/dashboard/V7/' + groupParamId)
        .then((response) => {
            dat = response.data;
            for (let i=0; i < 10; i++) {
                if (i >= dat.length) {
                    dashboardParam.push('');
                    showInLegend[i]=false;
                } else {
                    dashboardParam.push(dat[i]);
                    showInLegend[i]=true;
                }
            }
            axios.get('/api/dashboard/V6/' + groupParamId + '/' + periodValue)
                .then((response) => {
                    sdata = response.data;

                    for (let i = 0; i < 10; i++) {
                        let idata = [];
                        if (i >= sdata.length) {
                            idata.push(0);
                            resy[i] = '';
                        } else {
                            idata = sdata[i];
                            resy[i] = Math.ceil(Number(idata[idata.length-1].value));
                        }
                        yData[i] = [];
                        sumy[i] = 0;

                        for (let j = 0; j < idata.length; j++) {
                            if (i === 0) {
                                xData.push(idata[j].date);
                            }
                            if (i >= sdata.length) {
                                yData[i].push(null);
                                sumy[i] = '';
                            } else {
                                yData[i].push(Number(idata[j].value));
                                sumy[i] = sumy[i] + Math.ceil(Number(idata[j].value));
                            }
                        }


                    }
                    // let col = colors[Math.ceil(Math.random() * 10)];
                    // let colorBorder = colorsBorder[Math.ceil(Math.random() * 10)];


                    if (typeline_select === 'Область' || typeline_select === 'Область с регрессом') {
                        Highcharts.chart('containergroup'+groupParamId, {
                            chart: {
                                type: "area",
                            },
                            credits: {
                                enabled: false
                            },

                            title: {
                                text: groupParamName,
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

                            series:
                                [{
                                    name: dashboardParam[0],
                                    data: yData[0],
                                    color: colorsBorder[0],
                                    showInLegend: showInLegend[0],
                                    // fillColor: colors[0],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                }, {
                                    name: dashboardParam[1],
                                    data: yData[1],
                                    color: colorsBorder[1],
                                    showInLegend: showInLegend[1],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[2],
                                    data: yData[2],
                                    color: colorsBorder[2],
                                    showInLegend: showInLegend[2],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[3],
                                    data: yData[3],
                                    color: colorsBorder[3],
                                    showInLegend: showInLegend[3],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[4],
                                    data: yData[4],
                                    color: colorsBorder[4],
                                    showInLegend: showInLegend[4],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[5],
                                    data: yData[5],
                                    color: colorsBorder[5],
                                    showInLegend: showInLegend[5],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[6],
                                    data: yData[6],
                                    color: colorsBorder[6],
                                    showInLegend: showInLegend[6],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[7],
                                    data: yData[7],
                                    color: colorsBorder[7],
                                    showInLegend: showInLegend[7],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[8],
                                    data: yData[8],
                                    color: colorsBorder[8],
                                    showInLegend: showInLegend[8],
                                    // fillColor: colors[1],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }
                                },{
                                    name: dashboardParam[9],
                                    data: yData[9],
                                    color: colorsBorder[9],
                                    showInLegend: showInLegend[9],
                                    // fillColor: colors[2],
                                    fillOpacity: 0.5,
                                    softThreshold: false,
                                    marker: {
                                        radius: 3
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        color: '#000000',
                                        format: '{point.y:.0f}' //формат подписи
                                    }


                                }]

                        })
                    }else if (typeline_select === 'Линия'){
                        Highcharts.chart('containergroup'+groupParamId, {
                            chart: {
                                type: "line"
                            },
                            credits: {
                                enabled: false
                            },

                            title: {
                                text: groupParamName,
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
                                name: dashboardParam[0],
                                data: yData[0],
                                color: colorsBorder[0],
                                showInLegend: showInLegend[0],
                                // fillColor: colors[0],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[1],
                                data: yData[1],
                                color: colorsBorder[1],
                                showInLegend: showInLegend[1],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[2],
                                data: yData[2],
                                color: colorsBorder[2],
                                showInLegend: showInLegend[2],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[3],
                                data: yData[3],
                                color: colorsBorder[3],
                                showInLegend: showInLegend[3],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[4],
                                data: yData[4],
                                color: colorsBorder[4],
                                showInLegend: showInLegend[4],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[5],
                                data: yData[5],
                                color: colorsBorder[5],
                                showInLegend: showInLegend[5],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[6],
                                data: yData[6],
                                color: colorsBorder[6],
                                showInLegend: showInLegend[6],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[7],
                                data: yData[7],
                                color: colorsBorder[7],
                                showInLegend: showInLegend[7],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[8],
                                data: yData[8],
                                color: colorsBorder[8],
                                showInLegend: showInLegend[8],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[9],
                                data: yData[9],
                                color: colorsBorder[9],
                                showInLegend: showInLegend[9],
                                // fillColor: colors[2],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            }]
                        })
                    } else if (typeline_select === 'Гистограмма') {
                        Highcharts.chart('containergroup'+groupParamId, {
                            chart: {
                                type: "column"
                            },
                            credits: {
                                enabled: false
                            },

                            title: {
                                text: groupParamName,
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
                                    color: '#AFB42B',
                                    borderColor: '#2e3e9f'
                                }
                            },

                            series: [{
                                name: dashboardParam[0],
                                data: yData[0],
                                color: colorsBorder[0],
                                showInLegend: showInLegend[0],
                                // fillColor: colors[0],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[1],
                                data: yData[1],
                                color: colorsBorder[1],
                                showInLegend: showInLegend[1],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[2],
                                data: yData[2],
                                color: colorsBorder[2],
                                showInLegend: showInLegend[2],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[3],
                                data: yData[3],
                                color: colorsBorder[3],
                                showInLegend: showInLegend[3],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[4],
                                data: yData[4],
                                color: colorsBorder[4],
                                showInLegend: showInLegend[4],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[5],
                                data: yData[5],
                                color: colorsBorder[5],
                                showInLegend: showInLegend[5],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[6],
                                data: yData[6],
                                color: colorsBorder[6],
                                showInLegend: showInLegend[6],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[7],
                                data: yData[7],
                                color: colorsBorder[7],
                                showInLegend: showInLegend[7],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[8],
                                data: yData[8],
                                color: colorsBorder[8],
                                showInLegend: showInLegend[8],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[9],
                                data: yData[9],
                                color: colorsBorder[9],
                                showInLegend: showInLegend[9],
                                // fillColor: colors[2],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            }]
                        })
                    }else if (typeline_select === 'Гистограмма 3D'){
                        Highcharts.chart('containergroup'+groupParamId, {
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
                                text: groupParamName,
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
                                    depth: 30,
                                    color: '#4CAF50'
                                }
                            },
                            xAxis: {
                                categories: xData,
                                labels: {
                                    skew3d: true,
                                    style: {
                                        fontSize: '',
                                        color: ''
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
                            series: [{
                                name: dashboardParam[0],
                                data: yData[0],
                                color: colorsBorder[0],
                                showInLegend: showInLegend[0],
                                // fillColor: colors[0],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[1],
                                data: yData[1],
                                color: colorsBorder[1],
                                showInLegend: showInLegend[1],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[2],
                                data: yData[2],
                                color: colorsBorder[2],
                                showInLegend: showInLegend[2],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[3],
                                data: yData[3],
                                color: colorsBorder[3],
                                showInLegend: showInLegend[3],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[4],
                                data: yData[4],
                                color: colorsBorder[4],
                                showInLegend: showInLegend[4],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[5],
                                data: yData[5],
                                color: colorsBorder[5],
                                showInLegend: showInLegend[5],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[6],
                                data: yData[6],
                                color: colorsBorder[6],
                                showInLegend: showInLegend[6],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[7],
                                data: yData[7],
                                color: colorsBorder[7],
                                showInLegend: showInLegend[7],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[8],
                                data: yData[8],
                                color: colorsBorder[8],
                                showInLegend: showInLegend[8],
                                // fillColor: colors[1],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            },{
                                name: dashboardParam[9],
                                data: yData[9],
                                color: colorsBorder[9],
                                showInLegend: showInLegend[9],
                                // fillColor: colors[2],
                                fillOpacity: 0.5,
                                softThreshold: false,
                                marker: {
                                    radius: 3
                                },
                                dataLabels: {
                                    enabled: true,
                                    color: '#000000',
                                    format: '{point.y:.0f}' //формат подписи
                                }
                            }]
                        });

                    } else if (typeline_select === 'Круг'){

                        // Data retrieved from https://netmarketshare.com/
// Radialize the colors
//                         Highcharts.setOptions({
//                             colors: Highcharts.map(Highcharts.getOptions().colors, function (color) {
//                                 return {
//                                     radialGradient: {
//                                         cx: 0.5,
//                                         cy: 0.3,
//                                         r: 0.7
//                                     },
//                                     stops: [
//                                         [0, color],
//                                         [1, Highcharts.color(color).brighten(-0.3).get('rgb')] // darken
//                                     ]
//                                 };
//                             })
//                         });

// Build the chart
                        Highcharts.chart('containergroup'+groupParamId, {
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
                                text: xData[xData.length-1]+' '+groupParamName+' сумма за '+periodValue+' дн.',
                                align: 'left',
                                style:{
                                    color: titlecolor
                                }
                            },
                            subtitle: {
                                text: dashboardParam[0]+' '+sumy[0]+'<br>'+
                                    dashboardParam[1]+' '+sumy[1]+'<br>'+
                                    dashboardParam[2]+' '+sumy[2]+'<br>'+
                                    dashboardParam[3]+' '+sumy[3]+'<br>'+
                                    dashboardParam[4]+' '+sumy[4]+'<br>'+
                                    dashboardParam[5]+' '+sumy[5]+'<br>'+
                                    dashboardParam[6]+' '+sumy[6]+'<br>'+
                                    dashboardParam[7]+' '+sumy[7]+'<br>'+
                                    dashboardParam[8]+' '+sumy[8]+'<br>'+
                                    dashboardParam[9]+' '+sumy[9]+'<br>',
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
                                name: 'Процент от '+groupParamName,
                                data:[
                                    {name: dashboardParam[0], y: sumy[0]},
                                    {name: dashboardParam[1], y: sumy[1]},
                                    {name: dashboardParam[2], y: sumy[2]},
                                    {name: dashboardParam[3], y: sumy[3]},
                                    {name: dashboardParam[4], y: sumy[4]},
                                    {name: dashboardParam[5], y: sumy[5]},
                                    {name: dashboardParam[6], y: sumy[6]},
                                    {name: dashboardParam[7], y: sumy[7]},
                                    {name: dashboardParam[8], y: sumy[8]},
                                    {name: dashboardParam[9], y: sumy[9]}

                                ]
                            }]
                        });

                    }else if (typeline_select === 'Кольцо 3D'){
                        Highcharts.chart('containergroup'+groupParamId, {
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
                                text: xData[xData.length-1]+' '+groupParamName,
                                align: 'left',
                                style:{
                                    color: titlecolor
                                }
                            },
                            subtitle: {
                                text: dashboardParam[0]+' '+resy[0]+'<br>'+
                                    dashboardParam[1]+' '+resy[1]+'<br>'+
                                    dashboardParam[2]+' '+resy[2]+'<br>'+
                                    dashboardParam[3]+' '+resy[3]+'<br>'+
                                    dashboardParam[4]+' '+resy[4]+'<br>'+
                                    dashboardParam[5]+' '+resy[5]+'<br>'+
                                    dashboardParam[6]+' '+resy[6]+'<br>'+
                                    dashboardParam[7]+' '+resy[7]+'<br>'+
                                    dashboardParam[8]+' '+resy[8]+'<br>'+
                                    dashboardParam[9]+' '+resy[9]+'<br>',
                                align: 'left',
                                style:{
                                    color: titlecolor
                                }
                            },
                            // subtitle: {
                            //     text: '',
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
                                name: 'Процент от '+groupParamName,
                                data: [
                                    [dashboardParam[0], resy[0]],
                                    [dashboardParam[1], resy[1]],
                                    [dashboardParam[2], resy[2]],
                                    [dashboardParam[3], resy[3]],
                                    [dashboardParam[4], resy[4]],
                                    [dashboardParam[5], resy[5]],
                                    [dashboardParam[6], resy[6]],
                                    [dashboardParam[7], resy[7]],
                                    [dashboardParam[8], resy[8]],
                                    [dashboardParam[9], resy[9]]
                                ]
                            }]
                        });

                    }else {

                        axios.get('/api/dashboard/V10/' + groupParamId)
                            .then((response) => {
                                datId = response.data;
                                for (let i = 0; i < 10; i++) {
                                    if (i >= datId.length) {
                                        paramListId.push('');
                                        paramListIcon.push('');
                                    } else {
                                        paramListId.push(datId[i].id);
                                        paramListIcon.push(datId[i].icon);
                                    }
                                }
                                let text = document.getElementById('containergroup' + groupParamId);
                                let string =
                                "<div class=\"text-lg-center\"><h5 style=\"color:#bb2020\"><b>" + groupParamName + "  " + xData[xData.length - 1] + "<b/></h5></div>\n"

                                for (let z = 0; z < dat.length; z++) {
                                    string +=
                                        "                                   <div class=\"col-sm-6 card-body br\">\n" +
                                        "                                        <div class=\"row\">\n" +
                                        "                                            <div class=\"col-4\">\n" +
                                        "                                                <i class=\"material-icons-two-tone text-primary mb-1\">"+paramListIcon[z]+"</i>\n" +
                                        "                                            </div>\n" +
                                        "                                            <div class=\"col-4 text-md-start\">\n" +
                                        "                                                <a  href=\"/dash/dashboard/" + paramListId[z] + "\"><h6>" + dashboardParam[z] + "</h6></a>\n" +
                                        "                                                <span>" + resy[z] + "</span>\n" +
                                        "                                            </div>\n" +
                                        "                                        </div>\n" +
                                        "                                    </div>\n"
                                }


                                text.innerHTML = string;

                            });
                    }

                });
        });
}