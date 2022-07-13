package com.econet.app.trans;

import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.Question;
import com.econet.app.ServerBeans.SaveQuestionBean;
import com.econet.app.ServerBeans.historydetail.Answerlist;
import com.econet.app.ServerBeans.historydetail.Basicinfo;
import com.econet.app.ServerBeans.historydetail.Healthinfo;
import com.econet.app.ServerBeans.historydetail.HistoryDetailBean;
import com.econet.app.ServerBeans.historydetail.Travellist;
import com.econet.app.beans.QuestionBean;
import com.econet.app.beans.QuestionFormBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * this class use to trans between  the server bean and local bean
 */
public class DataAdapter {

    public static SaveQuestionBean localToServer(QuestionFormBean localBean)
    {
        SaveQuestionBean saveQuestion =new SaveQuestionBean();
        saveQuestion.recordDate="2020-08-01";//new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        saveQuestion.questionList =new ArrayList<>();
        saveQuestion.travelList =new ArrayList<>();

        List<String> answerList =new ArrayList<>();
        //special for travel
        List<Question> travelQuestionList=new ArrayList<>();
        List<String> travelList=new ArrayList<>();

        for(int i=0;i<localBean.form.size();i++)
        {
            QuestionBean questionBean=localBean.form.get(i);
            if("01".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("1",answerList));
            }
            else if("02".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("2",answerList));
            }
            else if("03".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("3",answerList));
            }
            else if("04".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("4",answerList));
            }
            else if("05".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("5",answerList));
            }
            else if("06".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("6",answerList));
            }
            else if("07".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("7",answerList));
            }
            else if("08".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                if("A".equals(questionBean.getAnswer()))
                {
                    travelList.add("801");
                }
                if("B".equals(questionBean.getAnswer()))
                {
                    travelList.add("802");
                }
                travelQuestionList.add(new Question("8",travelList));
            }
            else if("09".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("9",travelList));
            }
            else if("10".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("10",travelList));
            }
            else if("11".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("11",travelList));
            }
            else if("12".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("12",travelList));
            }
            else if("13".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("13",travelList));
            }
            else if("14".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("14",travelList));
            }
            else if("15".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("15",travelList));
            }
            else if("16".equals(questionBean.getQuestionId()))
            {
                travelList =new ArrayList<>();
                travelList.add(questionBean.getAnswer());
                travelQuestionList.add(new Question("16",travelList));
            }
            else if("17".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                if("A".equals(questionBean.getAnswer()))
                {
                    answerList.add("1701");
                }
                if("B".equals(questionBean.getAnswer()))
                {
                    answerList.add("1702");
                }
                if("C".equals(questionBean.getAnswer()))
                {
                    answerList.add("1703");
                }
                saveQuestion.questionList.add(new Question("17",answerList));
            }
            else if("18".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                if("A".equals(questionBean.getAnswer()))
                {
                    answerList.add("1801");
                }
                if("B".equals(questionBean.getAnswer()))
                {
                    answerList.add("1802");
                }
                saveQuestion.questionList.add(new Question("18",answerList));
            }
            else if("19".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                if("A".equals(questionBean.getAnswer()))
                {
                    answerList.add("1901");
                }
                if("B".equals(questionBean.getAnswer()))
                {
                    answerList.add("1902");
                }
                saveQuestion.questionList.add(new Question("19",answerList));
            }
            else if("20".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                if("A".equals(questionBean.getAnswer()))
                {
                    answerList.add("2001");
                }
                if("B".equals(questionBean.getAnswer()))
                {
                    answerList.add("2002");
                }
                saveQuestion.questionList.add(new Question("20",answerList));
            }
            else if("21".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                answerList.add(questionBean.getAnswer());
                saveQuestion.questionList.add(new Question("21",answerList));
            }
            else if("22".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                if(questionBean.getAnswer().contains("A"))
                {
                    answerList.add("2201");
                }
                if(questionBean.getAnswer().contains("B"))
                {
                    answerList.add("2202");
                }
                if(questionBean.getAnswer().contains("C"))
                {
                    answerList.add("2203");
                }
                if(questionBean.getAnswer().contains("D"))
                {
                    answerList.add("2204");
                }
                if(questionBean.getAnswer().contains("E"))
                {
                    answerList.add("2205");
                }
                if(questionBean.getAnswer().contains("F"))
                {
                    answerList.add("2206");
                }
                if(questionBean.getAnswer().contains("G"))
                {
                    answerList.add("2207");
                }
                saveQuestion.questionList.add(new Question("22",answerList));
            }
            else if("23".equals(questionBean.getQuestionId()))
            {
                answerList=new ArrayList<>();
                if("A".equals(questionBean.getAnswer()))
                {
                    answerList.add("2301");
                }
                if("B".equals(questionBean.getAnswer()))
                {
                    answerList.add("2302");
                }
                if("C".equals(questionBean.getAnswer()))
                {
                    answerList.add("2303");
                }
                if("D".equals(questionBean.getAnswer()))
                {
                    answerList.add("2304");
                }
                saveQuestion.questionList.add(new Question("23",answerList));
            }
        }
        saveQuestion.travelList.add(travelQuestionList);
        return saveQuestion;
    }

    public static QuestionFormBean  serverToLocalHistoryDetail(HistoryDetailBean historyDetailBean)
    {
        QuestionFormBean formBean =new QuestionFormBean();
        formBean.form=new ArrayList<>();
        if(historyDetailBean!=null)
        {
            if(historyDetailBean.getResult()!=null)
            {
                if (historyDetailBean.getResult().getQuestionlist() != null) {
                    if (historyDetailBean.getResult().getQuestionlist().getBasicinfo() != null) {
                        List<Basicinfo> basicinfos = historyDetailBean.getResult().getQuestionlist().getBasicinfo();
                        for (int i = 0; i < basicinfos.size(); i++) {
                            Basicinfo basicinfo = basicinfos.get(i);
                            if ("1".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("01", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            } else if ("2".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("02", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            } else if ("3".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("03", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            } else if ("4".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("04", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            } else if ("5".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("05", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            } else if ("6".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("06", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            } else if ("7".equals(basicinfo.getQuestionid())) {
                                formBean.form.add(new QuestionBean("07", "0", basicinfo.getAnswerlist().get(0).getAnswer().toString()));
                            }
                        }
                    }
                }
                if(historyDetailBean.getResult().getQuestionlist().getHealthinfo()!=null)
                {
                    List<Healthinfo> healthinfos = historyDetailBean.getResult().getQuestionlist().getHealthinfo();
                    for(int i=0;i<healthinfos.size();i++)
                    {
                        Healthinfo healthinfo=healthinfos.get(i);
                        if("17".equals(healthinfo.getQuestionid()))
                        {
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            for(int j=0;j<answerlists.size();j++)
                            {
                                if("Yes".equals(answerlists.get(j).getIsSelected()))
                                {
                                    String answer=answerlists.get(j).getAnswer();
                                    if("Yes".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("17","1","A"));
                                    }else if("No".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("17","1","B"));
                                    }
                                    else if("Don't know".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("17","1","C"));
                                    }
                                }
                            }
                        }
                        else if("18".equals(healthinfo.getQuestionid()))
                        {
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            for(int j=0;j<answerlists.size();j++)
                            {
                                if("Yes".equals(answerlists.get(j).getIsSelected()))
                                {
                                    String answer=answerlists.get(j).getAnswer();
                                    if("Yes".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("18","1","A"));
                                    }else if("No".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("18","1","B"));
                                    }
                                }
                            }
                        }
                        else if("19".equals(healthinfo.getQuestionid()))
                        {
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            for(int j=0;j<answerlists.size();j++)
                            {
                                if("Yes".equals(answerlists.get(j).getIsSelected()))
                                {
                                    String answer=answerlists.get(j).getAnswer();
                                    if("Yes".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("19","1","A"));
                                    }else if("No".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("19","1","B"));
                                    }
                                }
                            }
                        }
                        else if("20".equals(healthinfo.getQuestionid()))
                        {
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            for(int j=0;j<answerlists.size();j++)
                            {
                                if("Yes".equals(answerlists.get(j).getIsSelected()))
                                {
                                    String answer=answerlists.get(j).getAnswer();
                                    if("Yes".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("20","1","A"));
                                    }else if("No".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("20","1","B"));
                                    }
                                }
                            }
                        }
                        else if("21".equals(healthinfo.getQuestionid()))
                        {
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            formBean.form.add(new QuestionBean("21","0",answerlists.get(0).getAnswer()));
                        }
                        else if("22".equals(healthinfo.getQuestionid()))
                        {
                            String result="";
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            for(int j=0;j<answerlists.size();j++)
                            {
                                if("Yes".equals(answerlists.get(j).getIsSelected()))
                                {
                                    String answer=answerlists.get(j).getAnswer();
                                    if("Fever, chills, or sweating".equals(answer))
                                    {
                                        result=result+"A";
                                    }
                                    if("Difficulty breathing (not severe)".equals(answer))
                                    {
                                        result=result+"B";
                                    }
                                    if("New or worsening cough".equals(answer))
                                    {
                                        result=result+"C";
                                    }
                                    if("Sore throat".equals(answer))
                                    {
                                        result=result+"D";
                                    }
                                    if("Aching throughout the body".equals(answer))
                                    {
                                        result=result+"E";
                                    }
                                    if("Vomiting or diarrhea".equals(answer))
                                    {
                                        result=result+"F";
                                    }
                                    if("None of the above".equals(answer))
                                    {
                                        result=result+"G";
                                    }
                                }
                            }
                            formBean.form.add(new QuestionBean("22","2",result));
                        }
                        else if("23".equals(healthinfo.getQuestionid()))
                        {
                            List<Answerlist> answerlists=healthinfo.getAnswerlist();
                            for(int j=0;j<answerlists.size();j++)
                            {
                                if("Yes".equals(answerlists.get(j).getIsSelected()))
                                {
                                    String answer=answerlists.get(j).getAnswer();
                                    if("Never received COVID-19 test".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("23","1","A"));
                                    }else  if("Positive for COVID-19 test".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("23","1","B"));
                                    }else if("Negative for COVID-19 test".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("23","1","C"));
                                    }else if("Pending results".equals(answer))
                                    {
                                        formBean.form.add(new QuestionBean("23","1","D"));
                                    }
                                }
                            }
                        }
                    }
                }
                //travel info
                if(historyDetailBean.getResult().getTravellist()!=null)
                {
                    List<List<Travellist>> travelinfoss = historyDetailBean.getResult().getTravellist();
                    for(int k=0;k<travelinfoss.size();k++)
                    {
                        List<Travellist> travelinfos=travelinfoss.get(k);
                        for(int i=0;i<travelinfos.size();i++)
                        {
                            Travellist travelinfo=travelinfos.get(i);
                            if("8".equals(travelinfo.getQuestionid()))
                            {
                                List<Answerlist> answerlists=travelinfo.getAnswerlist();
                                for(int j=0;j<answerlists.size();j++)
                                {
                                    if("Yes".equals(answerlists.get(j).getIsSelected()))
                                    {
                                        String answer=answerlists.get(j).getAnswer();
                                        if("Yes".equals(answer))
                                        {
                                            formBean.form.add(new QuestionBean("08","1","A"));
                                        }else if("No".equals(answer))
                                        {
                                            formBean.form.add(new QuestionBean("08","1","B"));
                                        }
                                    }
                                }
                            }
                            if("9".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("09","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("10".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("10","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("11".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("11","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("12".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("12","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("13".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("13","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("14".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("14","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("15".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("15","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                            if("16".equals(travelinfo.getQuestionid()))
                            {
                                formBean.form.add(new QuestionBean("16","0",travelinfo.getAnswerlist().get(0).getAnswer()));
                            }
                        }
                    }
                }
            }
        }
        return formBean;
    }
    public static String toJson(QuestionFormBean formBean)
    {
        String json = JSONObject.toJSONString(formBean);
        return json;
    }
    public static String toJson(SaveQuestionBean saveQuestionBean)
    {
        String json = JSONObject.toJSONString(saveQuestionBean);
        return json;
    }

}
