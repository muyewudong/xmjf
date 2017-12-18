<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>资产详情</title>
    <link rel="stylesheet" href="${ctx}/css/reset.css">
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <link rel="stylesheet" href="${ctx}/css/user_siderbar.css">
    <link rel="stylesheet" href="${ctx}/css/assets.css">


</head>
<body>

<div class="top_wrap">
    <div class="top">
        <p class="fl phone">欢迎致电：021-67690939</p>
    </div>
</div>
<div class="header">
    <div class="contain">
        <a href="/index?0?0" class="logo">
        </a>
        <div class="header_nav" id="indexNav">
            <a href="${ctx}/index">首页</a>
            <a href="${ctx}/basItem/basItemListPage">我要投资</a>
            <a href="${ctx}/security?0?2">安全保障</a>
            <a href="${ctx}/account/accountInfoPage">我的账户</a>
            <a href="${ctx}/introduce?0?4">关于我们</a>
        </div>
        <div class="header_button">
        <#include "../include/personal.ftl">
        </div>
    </div>
</div>

<div class="container clear">

<#include "../include/user_siderbar.ftl">

    <div class="assets fr">
        <div class="assets_detail">
            <div class="center_text">
                <p class="num"></p>
            </div>
            <h2>资产详情</h2>
            <div id="pie_chart" style="width:100%;height:398px;">
            </div>


        </div>

        <div class="trend_detail">
            <h2 id="title">投资趋势图</h2>

            <div class="date">
                <span id="earnings">收益</span>
                <span id='investment' class='active'>投资</span>
            </div>
            <div id="line_chart" style="width:100%;height:368px;"></div>
        </div>

    </div>
</div>

<script type="text/javascript" src="${ctx}/js/assets/jquery-3.1.0.min.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/assets/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/js/assets/exporting.js"></script>
<script type="text/javascript" src="${ctx}/js/assets/highcharts-zh_CN.js"></script>
<#--
    <script type="text/javascript" src="${ctx}/js/assets/require.js" data-main="${ctx}/js/index"></script>
-->
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/assets/config.js"></script>
<script type="text/javascript">
    var ctx="${ctx}";
</script>
<script type="text/javascript" src="${ctx}/js/account.info.js"></script>


</body>
</html>