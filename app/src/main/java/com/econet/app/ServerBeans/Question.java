package com.econet.app.ServerBeans;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable
{
    public String questionId;
    public List<String> answerList;

    public Question(String questionId, List<String> answerList) {
        this.questionId = questionId;
        this.answerList = answerList;
    }
}