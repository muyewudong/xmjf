$(function () {
    $("#login").click(function () {
        var phone = $("#phone").val();
        var password = $("#password").val();
        if(isEmpty(phone)){
            layer.tips("手机号不能为空","#phone");
            return;
        }
        if(isEmpty(password)){
            layer.tips("密码不能为空","#password");
            return;
        }

        var params={};
        params.phone=phone;
        params.password = password;
        $.ajax({
            type:"post",
            url:ctx+"/user/userLogin",
            data:params,
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    setTimeout(function () {
                        window.location.href = ctx+"/index";
                    },1000)
                }else {
                    layer.tips(data.msg,"#login");
                }
            }
        })
    })
})