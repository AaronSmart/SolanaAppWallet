package com.econet.app.homepage;

import java.util.HashMap;

/**
 * 该类用于定义所有的APP菜单
 * @author dai.jianhui
 */
public class CustomerMenu {
    public static HashMap<String, Integer> getMenu() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcpt_logo));
        return hashMap;
    }
}
