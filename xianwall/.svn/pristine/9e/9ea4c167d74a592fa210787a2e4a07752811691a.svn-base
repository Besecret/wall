package com.wanding.util.queryhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lacan on 2017/5/26.
 */
public class Page<T> {

    protected final PageInfo pageInfo;

    protected final List<Sort> sortInfos = new ArrayList<Sort>();

    protected final List<T> datas;

    public Page(int currentPage, int rowsPerPage, long totalRecord, List<Sort> sortInfos, List<T> datas) {
        this.pageInfo = new PageInfo(currentPage, rowsPerPage, totalRecord);
        if (sortInfos != null) {
            this.sortInfos.addAll(sortInfos);
        }
        this.datas = datas;
    }

    /**
     * 返回当前页的排序信息
     *
     * @return
     */
    public List<Sort> getSortInfos() {
        return sortInfos;
    }

    /**
     * 返回当前分页信息
     */
    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public List<T> getDatas() {
        return datas;
    }

    public Map<String, Object> getResponseInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", datas);
        map.put("recordsTotal", pageInfo.getRecordsTotal());
        map.put("recordsFiltered", pageInfo.getRecordsTotal());
        return map;
    }

    public Map<String, Object> getResponseInfos() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", datas);
        map.put("total", pageInfo.getRecordsTotal());
        return map;
    }
}
