package com.econet.app.ServerBeans.historydetail;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2020-06-06 9:52:52
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Answerlist {

    private String id;
    private String answer;
    @JsonProperty("isSelected")
    private String isSelected;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setAnswer(String answer) {
         this.answer = answer;
     }
     public String getAnswer() {
         return answer;
     }

    public void setIsSelected(String isselected) {
         this.isSelected = isselected;
     }
     public String getIsSelected() {
         return isSelected;
     }

}