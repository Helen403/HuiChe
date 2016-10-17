package com.huiche.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/10 0010.
 * 金额正则，大于0，小数点后两位
 */
public final class IsMoneyUtils {


    public static boolean isMoney(String money) {
        //两位
//        Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
        //"^[0-9]+(.[0-9]{1})?$"
        //一位
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
        Matcher matcher = pattern.matcher(money);
        return matcher.matches();

    }


}
