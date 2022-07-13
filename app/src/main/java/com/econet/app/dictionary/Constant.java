package com.econet.app.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  dai.jianhui
 */
public class Constant {

    public class Solana
    {
        public static final String solMainNet="https://rpc.ankr.com/solana";
        //public static final String solMainNet="https://solana-api.projectserum.com";
        public static final String solscanNet="https://api.solscan.io/account/tokens";
        public static final String solscanNetTx="https://api.solscan.io/transaction";
        public static final String getSolscanNetTxMock="https://www.fastmock.site/mock/e7f30598bf9ac0d684b8e0a9368023ef/test/api/tx";
        public static final String getTokenAccountsByOwner ="{\"jsonrpc\": \"2.0\",     \"id\": 1,     \"method\": \"getTokenAccountsByOwner\",     \"params\": [       \"Gwz377J8Uy5U3oQW81aYroLQSNJSJ8ZEDPyY1jexQfFz\",       {           \"mint\":\"EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v\"       },       {         \"encoding\": \"jsonParsed\"       }     ]   }";


    }
    public class Url{
        public static final String  BASE_URL="https://122.192.9.52:20062/health/api/v1";
        public static final String  USER_SIGN_UP=BASE_URL+"/user/register";
        public static final String  USER_LOG_IN=BASE_URL+"/user/login";
        public static final String  USER_LOG_OUT=BASE_URL+"/user/logout";
        public static final String  USER_RESET_PASSWORD=BASE_URL+"/user/reset";
        public static final String  USER_INFO_DETAIL=BASE_URL+"/user/queryUserInfoDetail";
        public static final String  QUESTION_QUERY=BASE_URL+"/config/queryQuestionList";
        public static final String  SAVE_USER_QUESTION=BASE_URL+"/health/saveUserQuestion";
        public static final String  HISTORY_QUESTION_LIST_QUERY=BASE_URL+"/health/queryUserQuestionList";
        public static final String  QUERY_QUESTION_DETAIL=BASE_URL+"/health/queryUserQuestionDetail";

    }
    public class Auth
    {
        public static final String USER_TOKEN="x-auth-token";
        public static final String USER_ID="x-auth-id";
        public static final String FIRST_NAME="x-first-name";
        public static final String LAST_NAME="x-last-name";
        public static final String HEALTH_STATUS="x-health-status";
        public static final String HEALTH_STATUS_TIME="x-health-status-time";
        public static final String USER_PHONE="x-user-phone";
    }
    public class User
    {
        public static final String LOGIN_TYPE="x-login-type";
        public static final String LOGIN_ID="x-login-id";
        public static final String LOGIN_PASSWORD="x-login-password";
        public static final String IS_FIRST_FILL_QUESTIONNAIRE="x-first-fill-questionnaire";
    }
    //save the basic user info in order to auto fill next time
    public class Basic
    {
        public static final String BASIC_NAME="x-basic-name";
        public static final String BASIC_PHONE="x-basic-phone";
        public static final String BASIC_CITY="x-basic-city";
        public static final String BASIC_CITY_UPLOAD="x-basic-city-upload";
        public static final String BASIC_ADDRESS="x-basic-address";
        public static final String BASIC_ARRIVAL_DATE="x-basic-arrival-date";
        public static final String BASIC_KIN_PHONE="x-basic-kin-phone";
        public static final String BASIC_KIN_NAME="x-basic-kin-name";
    }

    public static class City
    {
        public static List<String> getCity()
        {
            List<String> list=new ArrayList<>();
            list.add("Bulawayo");
            list.add("Harare");
            list.add("Manicaland");
            list.add("Mashonaland Central");
            list.add("Mashonaland East");
            list.add("Mashonaland West");
            list.add("Masvingo");
            list.add("Matabeleland North");
            list.add("Matabeleland South");
            list.add("Midlands");
            return list;
        }
        public static List<String> getArea(String city)
        {
            List<String> list=new ArrayList<>();
            if("Bulawayo".equals(city))
            {
                list.add("Bulawayo");
            }else if("Harare".equals(city))
            {
                list.add("Harare");
            }else if("Manicaland".equals(city))
            {
                list.add("Buhera");
                list.add("Chimanimani");
                list.add("Chipinge");
                list.add("Makoni");
                list.add("Mutare");
                list.add("Mutasa");
                list.add("Nyanga");
            }else if("Mashonaland Central".equals(city))
            {
                list.add("Bindura");
                list.add("Guruve");
                list.add("Mazowe");
                list.add("Mbire");
                list.add("Mount Darwin");
                list.add("Muzarabani");
                list.add("Rushinga");
                list.add("Shamva");
            }else if("Mashonaland East".equals(city))
            {
                list.add("Chikomba");
                list.add("Goromonzi");
                list.add("Marondera");
                list.add("Mudzi");
                list.add("Murehwa");
                list.add("Mutoko");
                list.add("Seke");
                list.add("UMP (Uzumba-Maramba-Pfungwe)");
                list.add("Wedza (Hwedza)");
            }else if("Mashonaland West".equals(city))
            {
                list.add("Chegutu");
                list.add("Chinhoyi");
                list.add("Hurungwe");
                list.add("Kariba");
                list.add("Makonde");
                list.add("Mhondoro-Ngezi");
                list.add("Sanyati");
                list.add("Zvimba");
            }else if("Masvingo".equals(city))
            {
                list.add("Bikita");
                list.add("Chiredzi");
                list.add("Chivi");
                list.add("Gutu");
                list.add("Masvingo");
                list.add("Mwenezi");
                list.add("Zaka");
            }else if("Matabeleland North".equals(city))
            {
                list.add("Binga");
                list.add("Bubi");
                list.add("Hwange");
                list.add("Lupane");
                list.add("Nkayi");
                list.add("Tsholotsho");
                list.add("Umguza");
            }else if("Matabeleland South".equals(city)){

                list.add("Beitbridge");
                list.add("Bulilima");
                list.add("Gwanda");
                list.add("Insiza");
                list.add("Mangwe");
                list.add("Matobo");
                list.add("Umzingwane");
            }else if("Midlands".equals(city))
            {
                list.add("Chirumhanzu");
                list.add("Gokwe North");
                list.add("Gokwe South");
                list.add("Gweru");
                list.add("Kwekwe");
                list.add("Mberengwa");
                list.add("Shurugwi");
                list.add("Zvishavane");
            }
            return list;
        }
    }
}
