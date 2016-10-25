package com.huiche.utils;


import com.huiche.bean.LocationBean;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON解析
 */
public class JSONUtil {

    private static String[] strs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 解析城市列表JSON
     */
    public static List<LocationBean.DataBean.CitylistBean> getCitysByJSON(List<LocationBean.DataBean.CitylistBean> data) {
        List<LocationBean.DataBean.CitylistBean> dataTmp = new ArrayList<>();
        int count = strs.length;
        int count2 = data.size();
        for (int i = 0; i < count; i++) {
            String s = strs[i];
            //特别字符用10086
            dataTmp.add(new LocationBean.DataBean.CitylistBean("10086", s));

            for (int j = 0; j < count2; j++) {
                String str = data.get(j).ci_name;
                str = str.substring(0, 1);
                str = getPinYinHeadChar(str);
                str = str.toUpperCase();
                if (s.equals(str)) {
                    dataTmp.add(data.get(j));
                }
            }
        }
        return dataTmp;
    }


    /**
     * 提取每个汉字的首字母
     */
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母

            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }


}
