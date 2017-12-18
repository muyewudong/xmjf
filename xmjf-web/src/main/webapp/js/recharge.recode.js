$(function () {

    loadRechargeRecodesData();


})


function loadRechargeRecodesData(pageNum, pageSize) {
    var params = {};
    var pageSize=5;
    if (!isEmpty(pageNum)) {
        params.pageNum = pageNum;
    }
    if (!isEmpty(pageSize)) {
        params.pageSize = pageSize;
    }

    $.ajax({
        type: "post",
        url: ctx + "/account/queryRechargeRecodesByUserId",
        data: params,
        dataType: "json",
        success: function (data) {
            var recodeList = data.list;
            var paginator = data.paginator;
            if (recodeList.length > 0) {
                initDivsHtml(recodeList);
                initNavigatePages(paginator);
            } else {
                layer.alert('记录不存在', {
                    skin: 'layui-layer-molv' //样式类名
                    , closeBtn: 0
                })
            }
        }
    })
}


function initDivsHtml(recodeList) {
    if (recodeList.length > 0) {
        var divs = "";
        for (var i = 0; i < recodeList.length; i++) {
            var tempData = recodeList[i];
            var auditTime = tempData.auditTime;
            if (auditTime == null) {
                auditTime = tempData.addtime;
            }
            divs = divs + "<div class='table-content-first'>" + auditTime + "</div>";
            var actualAmount = tempData.actualAmount;
            if (actualAmount == 0) {
                actualAmount = tempData.rechargeAmount;
            }
            divs = divs + "<div class='table-content-center'>" + actualAmount + "元" + "</div>";
            divs = divs + "<div class='table-content-first'>";
            if (tempData.status == 0) {
                divs = divs + "支付失败";
            }
            if (tempData.status == 1) {
                divs = divs + "支付完成";
            }
            if (tempData.status == 2) {
                divs = divs + "审核中";
            }
            divs = divs + "</div>";
        }
        $("#rechargeList").html(divs);
    }
}

function initNavigatePages(paginator) {
     var navigatepageNums=paginator.navigatepageNums;// 数组
     if(navigatepageNums.length>0){
         var lis="";
         if(!paginator.isFirstPage){
             lis=lis+"<li class='tab'><a href='javascript:toPageData(1)' title='首页' >首页</a></li>";
         }
         if(paginator.hasPreviousPage){
             lis=lis+"<li class='tab'><a href='javascript:toPageData("+(paginator.pageNum-1)+")' title='上一页' >上一页</a></li>";
         }
         for(var i=0;i<navigatepageNums.length;i++){
             var page=navigatepageNums[i];
             var href="javascript:toPageData("+page+")";
             if(paginator.pageNum==page){
                 lis=lis+"<li class='tab active'><a  href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
             }else{
                 lis=lis+"<li class='tab'><a href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
             }
         }
         if(paginator.hasNextPage){
             lis=lis+"<li class='tab'><a href='javascript:toPageData("+(paginator.pageNum+1)+")' title='下一页' >下一页</a></li>";
         }

         if(!paginator.isLastPage){
             lis=lis+"<li class='tab'><a href='javascript:toPageData("+(paginator.lastPage)+")' title='末页' >末页</a></li>";
         }
         $("#pages").html(lis);
     }

   /* var navigatepageNums = paginator.navigatepageNums;
    if (navigatepageNums.length > 0) {
        var lis = "";
        for (var j = 0; j < navigatepageNums.length; j++) {
            var page = navigatepageNums[j];

            var href = "javascript:toPageData(" + page + ")";
            if (page == paginator.pageNum) {
                lis = lis + "<li class='tab active'><a href='" + href + "' title='第" + page + "页' >" + page + "</a></li>";
            } else {
                lis = lis + "<li class='tab'><a href='" + href + "' title='第" + page + "页' >" + page + "</a></li>";
            }
        }
        $("#pages").html(lis);
    }*/

}


function toPageData(pageNum) {
    loadRechargeRecodesData(pageNum);
}