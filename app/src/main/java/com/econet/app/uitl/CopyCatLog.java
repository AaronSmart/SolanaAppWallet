package com.econet.app.uitl;

import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCatLog 
{
    public static final String NAME = "copycatmelog.txt";
    public static final String WHITE_LIST_DIR = "kikenn";
    private File file;

    public CopyCatLog() 
    {
        File temp = new File(Environment.getExternalStorageDirectory(), WHITE_LIST_DIR);
        
        if (!temp.exists()) 
        {
            temp.mkdirs();
        }
        this.file = new File(temp, NAME);
    }
    
    public  boolean addLogString(String logString)
    {
        try 
        {
            if (!TextUtils.isEmpty(logString)) 
            {
                FileWriter fileWriter = new FileWriter(this.file,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.append("\n"+logString);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.flush();
                fileWriter.close();
                return true;
            }

        } catch (Exception e) 
        {
            e.printStackTrace();
        }
		return false;
    }

    public boolean cleanLogString() 
    {
        try
        {
            FileWriter fileWriter = new FileWriter(this.file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            return true;
        } catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    public String readLog()
    {
        String s = "";
        if (this.file.exists()) 
        {
            FileReader fileReader;
			try 
			{
				fileReader = new FileReader(this.file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
		        s = bufferedReader.readLine();
		        bufferedReader.close();
		        fileReader.close();
		        return s;
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
        }
		return null;
    }
}