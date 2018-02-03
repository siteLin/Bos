package com.itheima.bos.domain.take_delivery;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

    private List<T> rows;
    private long total;

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "rows=" + rows +
                ", total=" + total +
                '}';
    }
}
