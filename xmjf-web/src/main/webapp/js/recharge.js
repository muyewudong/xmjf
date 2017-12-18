$(function () {

    $(".verification-img").click(function () {
        swithPicCode();
    });

    function swithPicCode() {
        $("#validImg").attr("src", ctx+"/img/getPictureVerifyImage?time=" + new Date());
    }

    $("#rechargeBut").click(function () {

        var rechargeAmount = $("#rechargeAmount").val();
        var pictureCode = $("#pictureCode").val();
        var password =$("#password").val();

        if(isEmpty(rechargeAmount)){
            layer.tips("充值金额不能为空","#rechargeAmount");
            return;
        }
        if(isEmpty(pictureCode)){
            layer.tips("验证码不能为空","#pictureCode");
            return;
        }
        if(isEmpty(password)){
            layer.tips("交易密码不能为空","#password");
            return;
        }
        document.forms['fm'].submit();

    })

})