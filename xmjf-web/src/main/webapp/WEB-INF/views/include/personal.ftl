<#if userInfo??>
<div id="hasUserId">
    <div class='btn login'
         style="margin:36px auto 0; width: 164px;border:1px solid #fff" >
        <p>${userInfo.mobile}<img style="margin:15px 0 0 5px;" src="/img/xl-icon.png" alt=""></p>
    </div>
    <div id="option" class="option display">
        <div class="option-message">
            <a class="selected" href="${ctx}/user/site?-1?3" style="cursor:pointer;">消息中心</a>
        </div>
        <div class="option-two">
            <a class="selected2" href="${ctx}/user/log?3?3" style="cursor:pointer;">资金记录</a>
        </div>
        <div class="option-two">
            <a class="selected3" href="${ctx}/user/inviteCode?5?3" style="cursor:pointer;">邀请好友</a>
        </div>
        <div class="option-two">
            <a class="selected4" href="${ctx}/user/exit" id="exit" style="cursor:pointer;">我要退出</a>
        </div>
    </div>
</div>
<#else>
<div id="noUserId" style="width:142px;float: right">
    <a href="http://localhost:9091/login"><input class="btn register" id="loginPage"
                                                            type="button" value="登录"></a>
    <a href="http://localhost:9091/register"><input class="btn register" id="registerPage"
                                                               type="button" value="注册"></a>
</div>
</#if>