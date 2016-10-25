package com.huiche.bean;

import java.util.List;

public class CityBean {


	/**
	 * status : 10028
	 * msg : 获取数据成功
	 * data : [{"ci_id":"76","ci_name":"广州"},{"ci_id":"77","ci_name":"深圳"},{"ci_id":"78","ci_name":"潮州"},{"ci_id":"79","ci_name":"东莞"},{"ci_id":"80","ci_name":"佛山"},{"ci_id":"81","ci_name":"河源"},{"ci_id":"82","ci_name":"惠州"},{"ci_id":"83","ci_name":"江门"},{"ci_id":"84","ci_name":"揭阳"},{"ci_id":"85","ci_name":"茂名"},{"ci_id":"86","ci_name":"梅州"},{"ci_id":"87","ci_name":"清远"},{"ci_id":"88","ci_name":"汕头"},{"ci_id":"89","ci_name":"汕尾"},{"ci_id":"90","ci_name":"韶关"},{"ci_id":"91","ci_name":"阳江"},{"ci_id":"92","ci_name":"云浮"},{"ci_id":"93","ci_name":"湛江"},{"ci_id":"94","ci_name":"肇庆"},{"ci_id":"95","ci_name":"中山"},{"ci_id":"96","ci_name":"珠海"}]
	 */

	public int status;
	public String msg;
	/**
	 * ci_id : 76
	 * ci_name : 广州
	 */

	public List<DataBean> data;

	public static class DataBean {
		public String ci_id;
		public String ci_name;
	}
}
