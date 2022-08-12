package com.econet.app.ServerBeans.historylist;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-02 21:8:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Page {

    @JsonProperty("currentPage")
    private String currentpage;
    @JsonProperty("pageSize")
    private String pagesize;
    @JsonProperty("totalSize")
    private String totalsize;
    public void setCurrentpage(String currentpage) {
         this.currentpage = currentpage;
     }
     public String getCurrentpage() {
         return currentpage;
     }

    public void setPagesize(String pagesize) {
         this.pagesize = pagesize;
     }
     public String getPagesize() {
         return pagesize;
     }

    public void setTotalsize(String totalsize) {
         this.totalsize = totalsize;
     }
     public String getTotalsize() {
         return totalsize;
     }

}