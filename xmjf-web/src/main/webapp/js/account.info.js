$(function () {
    loadAccountInfoData();
    loadInvestInfoData();

})

function loadAccountInfoData() {
    $.ajax({
        type:"post",
        url:ctx+"/account/accountInfo",
        dataType:"json",
        success:function (data) {
            if(data.length>0){
                $(function () {
                    $('#pie_chart').highcharts({
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            spacing : [100, 0 , 40, 0]
                        },
                        title: {
                            floating:true,
                            text: '圆心显示的标题'
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: true,
                                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                    style: {
                                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                    }
                                },
                                point: {
                                    events: {
                                        mouseOver: function(e) {  // 鼠标滑过时动态更新标题
                                            // 标题更新函数，API 地址：https://api.hcharts.cn/highcharts#Chart.setTitle
                                            chart.setTitle({
                                                text: e.target.name+ '\t'+ e.target.y + ' %'
                                            });
                                        }
                                        //,
                                        // click: function(e) { // 同样的可以在点击事件里处理
                                        //     chart.setTitle({
                                        //         text: e.point.name+ '\t'+ e.point.y + ' %'
                                        //     });
                                        // }
                                    }
                                },
                            }
                        },
                        series: [{
                            type: 'pie',
                            innerSize: '80%',
                            name: '市场份额',
                            data: data
                        }]
                    }, function(c) {
                        // 环形图圆心
                        var centerY = c.series[0].center[1],
                            titleHeight = parseInt(c.title.styles.fontSize);
                        c.setTitle({
                            y:centerY + titleHeight/2
                        });
                        chart = c;
                    });
                });
            }else {
                layer.msg('资产详情暂无数据');
                layer.alert('墨绿风格，点击确认看深蓝', {
                    skin: 'layui-layer-molv'
                    ,closeBtn: 0
                });
            }
        }
    })
}


function loadInvestInfoData() {
    $.ajax({
        type:"post",
        url:ctx+"/busItemInvest/investInfo",
        dataType:"json",
        success:function (data) {
            var data1 = data.data1;
            var data2 = data.data2;
            if(data1.length>0){
                $('#line_chart').highcharts({

                    chart: {
                        type: 'spline'
                    },
                    title: {
                        text: '投资曲线图'
                    },
                    subtitle: {
                        text: '数据来源: www.shsxt.com'
                    },
                    xAxis: {
                        categories: data1
                    },
                    yAxis: {
                        title: {
                            text: '金额'
                        },
                        labels: {
                            formatter: function () {
                                return this.value + '元';
                            }
                        }
                    },
                    tooltip: {
                        crosshairs: true,
                        shared: true
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                radius: 4,
                                lineColor: '#666666',
                                lineWidth: 1
                            }
                        }
                    },
                    series: [{
                        name: '投资',
                        marker: {
                            symbol: 'square'
                        },
                        data: data2
                    }, {
                        name: '收益',
                        marker: {
                            symbol: 'diamond'
                        },
                        data: data2
                    }]

                })
            }else {
                layer.msg('资产详情暂无数据');
                layer.alert('墨绿风格，点击确认看深蓝', {
                    skin: 'layui-layer-molv'
                    ,closeBtn: 0
                });
            }
        }
    })


}