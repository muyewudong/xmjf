package com.shsxt.xm.api.query;


import java.io.Serializable;

public class BasItemQuery extends BaseQuery implements Serializable{

    private static final long serialVersionUID = -4007049132361729057L;
    private Integer itemCycle;//项目期限

    private Integer itemType;//项目类型

    private Integer isHistory;//是否是历史项目

    public Integer getItemCycle() {
        return itemCycle;
    }

    public void setItemCycle(Integer itemCycle) {
        this.itemCycle = itemCycle;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }
}
