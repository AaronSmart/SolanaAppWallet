package com.econet.app.ServerBeans.historylist;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-02 21:8:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Recordlist {

    @JsonProperty("recordId")
    private String recordid;
    @JsonProperty("recordDate")
    private String recorddate;
    @JsonProperty("recordStatus")
    private String recordstatus;
    @JsonProperty("recordTitle")
    private String recordTitle;
    public String getRecordTitle() {
        return recordTitle;
    }
    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }
    public void setRecordid(String recordid) {
         this.recordid = recordid;
     }
     public String getRecordid() {
         return recordid;
     }

    public void setRecorddate(String recorddate) {
         this.recorddate = recorddate;
     }
     public String getRecorddate() {
         return recorddate;
     }

    public void setRecordstatus(String recordstatus) {
         this.recordstatus = recordstatus;
     }
     public String getRecordstatus() {
         return recordstatus;
     }

}