package com.econet.app.ServerBeans.resetpassword;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-02 16:50:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ResetPasswordBean {

    @JsonProperty("resultCode")
    private int resultcode;
    @JsonProperty("resultMsg")
    private String resultmsg;
    private String result;
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

    public void setResult(String result) {
         this.result = result;
     }
     public String getResult() {
         return result;
     }

}