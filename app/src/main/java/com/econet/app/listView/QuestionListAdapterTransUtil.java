package com.econet.app.listView;

import com.econet.app.ServerBeans.historylist.Recordlist;

import java.util.ArrayList;
import java.util.List;

/**
 * according to requirement use this tool to change the history list view title,is's not good
 * @author dai.jianhui
 */
public class QuestionListAdapterTransUtil
{
    public static List<Recordlist> transRecordWithOrder(List<Recordlist> list,int mQuestionSize)
    {
        List<Recordlist> resultRecordlist=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            Recordlist record=list.get(i);
            Recordlist newRecord=new Recordlist();
            newRecord.setRecordid(record.getRecordid()+";Schedule "+(mQuestionSize+i+1));
            newRecord.setRecorddate(record.getRecorddate());
            newRecord.setRecordstatus(record.getRecordstatus());
            resultRecordlist.add(newRecord);
        }
        return resultRecordlist;
    }
}
