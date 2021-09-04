package com.econet.app.beans;

import java.io.Serializable;

public class QuestionBean  implements Serializable {
    private String questionId;
    private String questionType;//0是填空题 1是单选题 2是多选题
    private String answer; //通过分号进行数据分割

    public QuestionBean(String questionId, String questionType, String answer) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.answer = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
