package com.econet.app.ServerBeans.historydetail;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2020-06-06 9:52:52
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Basicinfo {

    @JsonProperty("questionId")
    private String questionid;
    @JsonProperty("questionGroup")
    private String questiongroup;
    @JsonProperty("questionTitle")
    private String questiontitle;
    @JsonProperty("questionType")
    private String questiontype;
    @JsonProperty("isMandatory")
    private String ismandatory;
    private String advice;
    @JsonProperty("answerList")
    private List<Answerlist> answerlist;
    public void setQuestionid(String questionid) {
         this.questionid = questionid;
     }
     public String getQuestionid() {
         return questionid;
     }

    public void setQuestiongroup(String questiongroup) {
         this.questiongroup = questiongroup;
     }
     public String getQuestiongroup() {
         return questiongroup;
     }

    public void setQuestiontitle(String questiontitle) {
         this.questiontitle = questiontitle;
     }
     public String getQuestiontitle() {
         return questiontitle;
     }

    public void setQuestiontype(String questiontype) {
         this.questiontype = questiontype;
     }
     public String getQuestiontype() {
         return questiontype;
     }

    public void setIsmandatory(String ismandatory) {
         this.ismandatory = ismandatory;
     }
     public String getIsmandatory() {
         return ismandatory;
     }

    public void setAdvice(String advice) {
         this.advice = advice;
     }
     public String getAdvice() {
         return advice;
     }

    public void setAnswerlist(List<Answerlist> answerlist) {
         this.answerlist = answerlist;
     }
     public List<Answerlist> getAnswerlist() {
         return answerlist;
     }

}