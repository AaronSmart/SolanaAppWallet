package com.econet.app.ServerBeans;

import java.io.Serializable;
import java.util.List;

public class SaveQuestionBean implements Serializable {

    public String recordDate;
    public List<Question> questionList;
    public List<List<Question>>travelList;
}
