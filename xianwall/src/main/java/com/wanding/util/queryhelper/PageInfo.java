package com.wanding.util.queryhelper;

/**
 * Created by lacan on 2017/5/26.
 */
public class PageInfo implements Cloneable {

    private final int page;
    private final int rowsPerPage;
    private final long recordsTotal;

    public PageInfo(int page, int rowsPerPage, long recordsTotal) {
        this.page = page;
        this.rowsPerPage = rowsPerPage;
        this.recordsTotal = recordsTotal;
    }

    /**
     * 获得当前页码
     *
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 获得每页数量
     *
     * @return
     */
    public final int getRowsPerPage() {
        return rowsPerPage;
    }

    /**
     * 获得data总数
     *
     * @return
     */
    public long getRecordsTotal() {
        return recordsTotal;
    }

    /**
     * 获得page总数
     *
     * @return
     */
    public int getTotalPage() {
        return (int) Math.ceil((double) recordsTotal / (double) rowsPerPage);
    }


    @Override
    public String toString() {
        return "PageInfo{" +
                "page=" + page +
                ", rowsPerPage=" + rowsPerPage +
                ", recordsTotal=" + recordsTotal +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
