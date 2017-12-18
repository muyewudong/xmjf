$(function () {
    $("#identityNext").click(function () {
        var realName = $("#realName").val();
        var idCard = $("#card").val();
        var password = $("#_ocx_password").val();
        var confirmPassword = $("#_ocx_password1").val();

        if(isEmpty(realName)){
            layer.tips("真实姓名不能为空","#realName");
            return;
        }
        if(isEmpty(idCard)){
            layer.tips("身份证不能为空","#card");
            return;
        }
        if(isEmpty(password)){
            layer.tips("密码不能为空","#_ocx_password");
            return;
        }
        if(isEmpty(password)||isEmpty(confirmPassword)||(password!=confirmPassword)){
            layer.tips("两次密码输入不一致","#_ocx_password1");
            return;
        }

        $.ajax({
            type: "post",
            url: ctx + "/user/userAuth",
            data: {
                realName: realName,
                idCard: idCard,
                password:password,
                confirmPassword:confirmPassword
             },
            dataType:"json",
            success:function(data) {
                if(data.code==200){

                    layer.alert('恭喜你,认证通过！', {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0
                    });
                    setTimeout(function () {
                        window.location.href=ctx+"/index";

                    },1000)

                }else {
                    layer.alert('很抱歉，认证失败！', {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0
                    });
                }
            }
        })
    })
})