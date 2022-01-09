package com.zlgg.study.common.elasticsearch;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 23:02
 * Description:
 */
public class Pager {
    /**
     * 记录数
     */
    private Long total = 0L;
    /**
     * 页码
     */
    private Integer pageNum = 1;
    /**
     * 每页要显示的记录数
     */
    private Integer pageSize = 10;
    /**
     * 页数
     */
    private Integer pages;
    /**
     * 当前页的记录数
     */
    private Integer size;

    public Pager() {
    }

    public Pager(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 查询起始位置
     */
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 总页数
     */
    public Long getPageCount() {
        Long pageCount = total / pageSize;
        if (total % pageSize != 0) {
            pageCount++;
        }
        return pageCount;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "total=" + total +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pages=" + pages +
                ", size=" + size +
                '}';
    }

}
