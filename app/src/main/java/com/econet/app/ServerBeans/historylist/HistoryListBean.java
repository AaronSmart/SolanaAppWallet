package com.econet.app.ServerBeans.historylist;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-02 21:8:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class HistoryListBean {

    @JsonProperty("resultCode")
    private int resultcode;
    @JsonProperty("resultMsg")
    private String resultmsg;
    private Result result;
    public void setResultcode(int resultcode) {
         this.resultcode = resultcode;
     }
     public int getResultcode() {
         return resultcode;
     }

     public void setResultmsg(String resultmsg) {
         this.resultmsg = resultmsg;
     }
     public String getResultmsg() {
         return resultmsg;
     }

    public void setResult(Result result) {
         this.result = result;
     }
     public Result getResult() {
         return result;
     }

}