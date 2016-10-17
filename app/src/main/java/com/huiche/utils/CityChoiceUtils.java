package com.huiche.utils;

import com.huiche.bean.ProvinceInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 针对 黄毛给的破接口，处理城市选择的工具类
 *
 * @author Administrator
 */
public final class CityChoiceUtils {
    private static boolean flag = false;
    /***
     * @param list
     * @param province
     * 当前省份
     * @param listAssign
     * @param i
     * 遍历集合的 当前的positon
     * @return
     */
    private static List<String> listCity = new ArrayList<>();
    private static String[] arrCity = {"A", "B", "C", "D", "E", "F", "G", "H",
            "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z"};

    /***
     * 获取四个直辖市的内部区域。
     *
     * @param list
     * @param province
     * @param listAssign
     * @param i
     * @return
     */
    public static boolean checkInfo(List<ProvinceInfo> list, String province,
                                    List<String> listAssign, int i) {
        String ccc = province;
        flag = false;
        if (province.equals("北京") || province.equals("天津")
                || province.equals("上海") || province.equals("重庆")) {
            flag = true;
            listAssign.clear();
            for (int j = 0; j < list.get(i).getCityList().size(); j++) {
                listAssign.addAll(list.get(i).getCityList().get(j)
                        .getAreaList());
                int ddd = list.get(i).getCityList().get(j).getAreaList().size();
                String adb = listAssign.get(j);
            }
        } else if (province.equals("香港") || province.equals("澳门")
                || province.equals("台湾")) {
            listAssign.clear();
            listAssign.add("");
            listAssign.add(province);
            flag = true;
        }
        return flag;
    }

    private String province;

    /**
     * 根据城市名称获取 省份
     *
     * @param lists
     * @return
     */
    public String getCityList(List<ProvinceInfo> lists, String cityNew) {
        province = null;
        boolean flag = true;
        if (cityNew.equals("北京市") || cityNew.equals("上海市")
                || cityNew.equals("天津市") || cityNew.equals("重庆市")
                || cityNew.equals("香港市") || cityNew.equals("澳门市")
                || cityNew.equals("台湾市")) {
            province = cityNew;
            return province.split("市")[0];
        } else {
            for (int i = 0; i < lists.size(); i++) {
                if (!lists.get(i).getName().equals("省份") && flag) {
                    // if(lists.get(i).getCityList())
                    // cityProvince.cityOfProvince=lists.get(i).getName();
                    for (int j = 0; j < lists.get(i).getCityList().size(); j++) {
                        String city = lists.get(i).getCityList().get(j)
                                .getName();
                        // Log.i("zane100", city+"89898989898989898");
                        if (city.equals("市、县级市、县") || city.equals("地级市")
                                || city.equals("县") || city.equals("省直行政单位")
                                || city.equals("市")) {
                        } else {
//							Log.i("zane100", city + "89898989898989898");
                            // CityProvince cityProvince = new CityProvince();
                            // cityProvince.cityName = city;
                            // cityProvince.cityOfProvince =
                            // lists.get(i).getName();
                            if (city.equals(cityNew)) {
                                province = lists.get(i).getName();
//								Log.i("zane100", province + "_________________");
                                flag = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (province == null) {
//			Log.i("zane100", "++++++++++++++++++");
            return "";
        } else {
//			Log.i("zane100", province);
            return province;
        }

    }

    class CityProvince {
        private String cityName;
        private String cityOfProvince;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityOfProvince() {
            return cityOfProvince;
        }

        public void setCityOfProvince(String cityOfProvince) {
            this.cityOfProvince = cityOfProvince;
        }
    }
}
