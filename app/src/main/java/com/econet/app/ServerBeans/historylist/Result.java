package com.econet.app.ServerBeans.historylist;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2020-06-02 21:8:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Result {

    @JsonProperty("recordList")
    private List<Recordlist> recordlist;
    private Page page;
    public void setRecordlist(List<Recordlist> recordlist) {
         this.recordlist = recordlist;
     }
     public List<Recordlist> getRecordlist() {
         return recordlist;
     }

    public void setPage(Page page) {
         this.page = page;
     }
     public Page getPage() {
         return page;
     }

}