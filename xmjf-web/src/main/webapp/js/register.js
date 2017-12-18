/*
$(function () {

    // 切换图片验证码
    $(".validImg").click(function () {
        switchVerifyCode();
    });
    function  switchVerifyCode() {
        $(".validImg").attr("src",ctx+"/img/getPictureVerifyImage?time="+new Date());
    }
    $("#clickMes").click(function () {
        var phone=$("#phone").val();
        var code=$("#code").val();
        if(isEmpty(phone)){
            layer.tips('手机号不能为空!', '#phone');
            return;
        }
        if(isEmpty(code)){
            layer.tips('图形验证码不能为空!', '.validImg');
            return;
        }
        var _this=$(this);
        $.ajax({
            type:"post",
            url:ctx+"/sms/sendPhoneVerifyCode",
            data:{
                phone:phone,
                picVerifyCode:code,
                type:1
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    time(_this);
                }else{
                    layer.tips(data.msg, '.validImg');
                    // 重新切换验证码
                    switchVerifyCode();
                }
            }
        })
    });
    $("#register").click(function () {
        var phone=$("#phone").val();
        var code=$("#code").val();
        var verification=$("#verification").val();
        var password=$("#password").val();
        if(isEmpty(phone)){
            layer.tips('手机号不能为空!', '#phone');
            return;
        }
        if(isEmpty(code)){
            layer.tips('图形验证码不能为空!', '.validImg');
            return;
        }
        if(isEmpty(verification)){
            layer.tips('手机验证码不能为空!', '#verification');
            return;
        }

        if(isEmpty(password)){
            layer.tips('密码不能为空!', '#password');
            return;
        }

        $.ajax({
            type:"post",
            url:ctx+"/user/userRegister",
            data:{
                phone:phone,
                picVerifyCode:code,
                phoneVerifyCode:verification,
                password:password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    layer.tips("注册成功!", '#register');
                    // 定时跳转至登陆页面
                    setTimeout(function () {
                        window.location.href=ctx+"/login";
                    },2000);
                }else{
                    layer.tips(data.msg, '#register');
                }
            }
        })
    })
});
var wait=6;
function time(o) {
    if (wait == 0) {
        o.removeAttr("disabled");
        o.val('获取验证码');
        o.css("color", '#ffffff');
        o.css("background","#fcb22f");

        wait = 6;
    } else {
        o.attr("disabled", true);
        o.css("color", '#fff');
        o.css("background", '#ddd');
        o.val("重新发送(" + wait + "s)");
        wait--;
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}*/


$(function(){
    $(".validImg").click(function () {
        $(this).attr("src",ctx+"/img/getPictureVerifyImage?time="+new Date());
    })

    //获取短信验证码
    $("#clickMes").click(function () {
        var phone = $("#phone").val();
        var code = $("#code").val();
        if(isEmpty(phone)){
            layer.tips("手机号不能为空","#phone");
            return;
        }
        if(isEmpty(code)){
            layer.tips("验证码不能为空",".validImg");
            return;
        }

        var _this= $(this);
        /**
         * 发送ajax请求
         */

        $.ajax({
            type:"post",
            url:ctx+"/sms/sendPhoneSms",
            data:{
                phone:phone,
                picCode:code,
                type:1
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    time(_this);
                }else {
                    layer.tips(data.msg,"#clickMes");
                }
            }
        })

    })

    var wait=6;
    function time(o) {
        if (wait == 0) {
            o.removeAttr("disabled");
            o.val('获取验证码');
            o.css("color", '#ffffff');
            o.css("background","#fcb22f");
            wait = 6;
        } else {
            o.attr("disabled", true);
            o.css("color", '#fff');
            o.css("background", '#ddd');
            o.val("重新发送(" + wait + "s)");
            wait--;
            setTimeout(function() {
                time(o)
            }, 1000)
        }
    }


    $("#register").click(function () {
        var phone = $("#phone").val();
        var picCode = $("#code").val();
        var code = $("#verification").val();
        var password = $("#password").val();
        if(isEmpty(phone)){
            layer.tips("手机号不能未空","#phone");
            return;
        }
        if(isEmpty(picCode)){
            layer.tips("请输入图片验证码","#clickMes");
            return;
        }
        if(isEmpty(code)){
            layer.tips("请输入手机验证码","##verification");
            return;
        }
        if(isEmpty(password)){
            layer.tips("请输入密码","#password");
            return;
        }


        $.ajax({
            type:"post",
            url:ctx+"/user/register",
            data:{
                phone:phone,
                picCode:picCode,
                code:code,
                password:password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    layer.tips("注册成功","#register");
                    setTimeout(function () {
                        window.location.href=ctx+"/login";
                    },1000);
                }else {
                    layer.tips("注册失败","#register");
                }
            }
        })
    })
});


