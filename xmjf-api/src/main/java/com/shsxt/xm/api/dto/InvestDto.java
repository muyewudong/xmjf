package com.shsxt.xm.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvestDto implements Serializable{

    private static final long serialVersionUID = 6483544450144888182L;
    private String month;
    private BigDecimal total;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "InvestDto{" +
                "month='" + month + '\'' +
                ", total=" + total +
                '}';
    }
}
