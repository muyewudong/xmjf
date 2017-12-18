<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>充值记录</title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/rechargeRecord.css">
    <link rel="stylesheet" href="/css/page.css">
    <link rel="stylesheet" href="/css/user_siderbar.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <script type="text/javascript" src="${ctx}/js/assets/require.js" data-main="${ctx}/js/index"></script>
    <script type="text/javascript" src="${ctx}/js/assets/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/js/assets/config.js"></script>
    <script type="text/javascript" >
        var ctx="${ctx}";
    </script>
    <script type="text/javascript" src="${ctx}/js/recharge.recode.js"></script>
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
    <div class="content fr">
        <div class="recharge-title">
            <div class="recharge-title-left">
                充值记录
            </div>
        </div>
        <div class="table-title">
            <div class="table-title-first">
                充值时间
            </div>
            <div class="table-title-center">
                充值金额
            </div>
            <div class="table-title-first">
                状态
            </div>
        </div>
        <div class="table-content" id="rechargeList">
		    <#--<div class="table-content-first">
			   2017-12-01
			 </div>
			 <div class="table-content-center">
			 1元
			 </div>
			 <div class="table-content-first">
			  成功
			 </div>-->

        </div>

        <div class="pages">
            <nav>
                <ul id="pages" style="margin:100px auto 140px;" class="pagination">

                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>