$(function () {
    $('#rate').radialIndicator();
    var val = $('#rate').attr("data-val");
    var radialObj = $('#rate').data('radialIndicator');
    radialObj.option("barColor", "orange");
    radialObj.option("percentage", true);
    radialObj.option("barWidth", 10);
    radialObj.option("radius", 40);
    radialObj.value(val);

})


function toRecharge() {
  $.ajax({
      type:"post",
      url:ctx+"/user/userAuthCheck",
      dataType:"json",
      success:function (data) {
          if(data.code==200){
              window.location.href=ctx+"/account/rechargePage";
          }else {
              layer.confirm(data.msg, {
                  btn: ['执行认证','稍后认证'] //按钮
              }, function(){
                  window.location.href=ctx+"/user/auth";
              });
          }
      }
  })
}

function doInvest() {

    alert("1");

}
