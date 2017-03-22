<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC 框架测试</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
</head>
<body>
<h1>${test.name}</h1>
<h1>${test.time}</h1>

<div id="container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(function () {
        Highcharts.chart('container', {
            chart: {
                type: 'spline',
                inverted: true
            },
            title: {
                text: 'CPU 占用率实时图表'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                reversed: false,
                title: {
                    enabled: true,
                    text: '占用率'
                },
                labels: {
                    formatter: function () {
                        return this.value + '%';
                    }
                },
                maxPadding: 0.05,
                showLastLabel: true
            },
            yAxis: {
                title: {
                    text: '时序'
                },
                labels: {
                    formatter: function () {
                        return this.value + '';
                    }
                },
                lineWidth: 2
            },
            legend: {
                enabled: false
            },
            tooltip: {
                headerFormat: '<b>{series.name}</b><br/>',
                pointFormat: '{point.x}%'
            },
            plotOptions: {
                spline: {
                    marker: {
                        enable: false
                    }
                }
            },
            series: [{
                name: '占用率',
                data: ${showListStr}
            }]
        });
    });
</script>
</body>
</html>