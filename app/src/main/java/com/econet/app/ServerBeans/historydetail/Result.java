package com.econet.app.ServerBeans.historydetail;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2020-06-06 9:52:52
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Result {

    @JsonProperty("recordId")
    private String recordid;
    @JsonProperty("recordDate")
    private String recorddate;
    @JsonProperty("recordStatus")
    private String recordstatus;
    @JsonProperty("questionList")
    private Questionlist questionlist;
    @JsonProperty("travelList")
    private List<List<Travellist>> travellist;
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

    public void setQuestionlist(Questionlist questionlist) {
         this.questionlist = questionlist;
     }
     public Questionlist getQuestionlist() {
         return questionlist;
     }

    public void setTravellist(List<List<Travellist>> travellist) {
         this.travellist = travellist;
     }
     public List<List<Travellist>> getTravellist() {
         return travellist;
     }

}