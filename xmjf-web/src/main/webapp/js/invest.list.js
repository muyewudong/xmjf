$(function () {
    /**
     * 页面加载完毕，填充表格数据
     *
     */
    loadInvestListData();

    $(".tab").click(function () {
        $(this).addClass("list_active");
        $(".tab").not(this).removeClass("list_active");
        var index = $(this).index();
        var isHistory = 0;
        var itemType = 0;
        if (index == 4) {
            isHistory = 1;
        }


        loadInvestListData(isHistory, null, index);


    })


});

/**
 * 是否为历史项目 isHistory
 * 项目类别itemType
 * 项目期限itemCycle
 * 分页页码pageNum
 * 每页数据大小pageSize
 */
function loadInvestListData(isHistory, itemType, itemCycle, pageNum, pageSize) {
    var params = {};
    params.isHistory = 0;//默认查询可投标项目
    if (!isEmpty(isHistory)) {
        params.isHistory = isHistory;
    }
    if (!isEmpty(itemType)) {
        params.itemType = itemType;
    }
    if (!isEmpty(itemCycle) && itemCycle != 0) {
        params.itemCycle = itemCycle;
    }
    if (!isEmpty(pageNum)) {
        params.pageNum = pageNum;
    }
    if (!isEmpty(pageSize)) {
        params.pageSize = pageSize;
    }

    $.ajax({
        type: "post",
        url: ctx + "/basItem/queryBasItemByParams",
        data: params,
        dataType: "json",
        success: function (data) {
            var paginator = data.paginator;
            var list = data.list;
            if (list.length > 0) {
                initTrHtml(list);
                initPageHtml(paginator);
                initItemScale();

            } else {
                $("#pages").html("<img style='margin-left: -70px;padding:40px;' " +
                    "src='/img/zanwushuju.png'>");
                $("#pcItemList").html("");
            }
        }
    })
}


/**
 *填充表格行记录数据
 */
function initTrHtml(list) {
    if (list.length > 0) {
        var trs = "";
        for (var i = 0; i < list.length; i++) {
            var tempData = list[i];
            trs = trs + "<tr>";
            trs = trs + "<td><strong>" + tempData.itemRate + "</strong><span>%";
            if (!isEmpty(tempData.itemAddRate)) {
                trs = trs + "+" + tempData.itemAddRate + "%";
            }
            trs = trs + "</span></td>";
            trs = trs + "<td><strong>" + tempData.itemCycle;
            if (tempData.itemCycleUnit == 1) {
                trs = trs + "天";
            }
            if (tempData.itemCycleUnit == 2) {
                trs = trs + "月";
            }
            if (tempData.itemCycleUnit == 3) {
                trs = trs + "季";
            }
            if (tempData.itemCycleUnit == 4) {
                trs = trs + "年";
            }
            trs = trs + "</strong></td>";
            trs = trs + "<td>" + tempData.itemName;
            if (tempData.itemIsnew == 0) {
                trs = trs + "<strong class='colorful' new>New</strong></td>";
            }
            if (tempData.itemIsnew == 1) {
                trs = trs + "<strong class='colorful' hot>Hot</strong></td>";
            }
            trs = trs + "<td class='trust_range'>";
            if (tempData.total > 90) {
                trs = trs + "A+";
            }
            if (tempData.total > 85 && tempData.total <= 90) {
                trs = trs + "A";
            }
            if (tempData.total <= 85 && tempData.total > 75) {
                trs = trs + "A-";
            }
            if (tempData.total <= 75 && tempData.total > 65) {
                trs = trs + "B";
            }
            trs = trs + "</td>";
            trs = trs + "<td class='company'>";
            trs = trs + "<img src='../img/logo.png' >";
            trs = trs + "</td>";


            //圆形进度框显示
            if(tempData.itemStatus==1){
                trs=trs+"<td>";
                trs=trs+"<strong class='countdown time' data-item='"+tempData.id+"' data-time='"+tempData.syTime+"'>";
                trs=trs+" <time class='hour'></time>&nbsp;:";
                trs=trs+"<time class='min'></time>&nbsp;:";
                trs=trs+" <time class='sec'></time>";
                trs=trs+"</td>";
            }else {
                trs=trs+"<td class='itemScale' data-val='"+tempData.itemScale+"'></td>";
            }



            trs = trs + "<td>";
            var status = tempData.itemStatus;
            var href = ctx + "/basItem/itemDetailPage?itemId=" + tempData.id;
            if (status == 1) {
                trs = trs + "<p><a href='" + href + "' ><input class=‘countdownButton’ valid type=‘button’ value='即将开标'></a></p>";
            }
            if (status == 10 || status == 13 || status == 18) {
                trs = trs + "<p class='left_money'>可投金额" + tempData.itemOngoingAccount + "元</p>" +
                    "<p><a href='" + href + "'><input valid type='button' value='立即投资'></a></p>";
            }
            if (status == 20) {
                trs = trs + "<p><a href='" + href + "'><input not_valid type='button' value='已抢完'></a></p>";
            }
            if (status == 30 || status == 31) {
                trs = trs + "<p><a href=" + href + "''><input not_valid type='button' value='还款中'></a></p>";
            }
            if (status == 32) {
                trs = trs + "<p style=‘position: relative’><a href='" + href + "' class=‘yihuankuan’><input not_valid type='button' value='已还款'></a></p>";
            }
            if (status == 23) {
                trs = trs + "<p><a href='" + href + "'><input not_valid type='button' value='已满标'></a></p>";
            }
            trs = trs + "</td>";
            trs = trs + "</tr>";
        }
        $("#pcItemList").html(trs);
    }
}

function initPageHtml(paginator) {
    var navigatepageNums = paginator.navigatepageNums;
    if (navigatepageNums.length > 0) {
        var lis = "";
        for (var j = 0; j < navigatepageNums.length; j++) {
            var page = navigatepageNums[j];

            var href = "javascript:toPageData(" + page + ")";
            if (page == paginator.pageNum) {
                lis = lis + "<li class='active'><a href='" + href + "' title='第" + page + "页' >" + page + "</a></li>";
            } else {
                lis = lis + "<li ><a href='" + href + "' title='第" + page + "页' >" + page + "</a></li>";
            }
        }
        $("#pages").html(lis);
    }
}


function toPageData(page) {
    var pageNum = page;
    var itemType = $("#itemType").val();
    var index = $(".list_active").index();
    var isHistory = 0;
    if (index == 4) {
        isHistory = 1;
    }
    loadInvestListData(isHistory, itemType, index, pageNum);
}


function initItemScale() {
    $(".itemScale").radialIndicator();
    var arrs = $(".itemScale");
    if (arrs.length > 0) {
        arrs.each(function () {
            var val = $(this).attr("data-val");
            var radialObj = $(this).data('radialIndicator');
            radialObj.option("barColor", "orange");
            radialObj.option("percentage", true);
            radialObj.option("barWidth", 10);
            radialObj.option("radius", 40);
            radialObj.value(val);
        })
    }
}


