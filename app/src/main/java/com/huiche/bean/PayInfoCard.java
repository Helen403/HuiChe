package com.huiche.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class PayInfoCard implements Parcelable {

    /**
     * msg : success
     * rows : [{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1236,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"17","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1237,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"18","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1238,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"19","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1239,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"20","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1240,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"21","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1241,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"22","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1242,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"23","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1243,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"24","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1244,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"25","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1245,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"26","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1246,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"27","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1247,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"28","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:25"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1248,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"29","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1249,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"30","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1251,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"32","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1252,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"33","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1253,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"34","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1254,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"35","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1255,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"36","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1256,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"37","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1257,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"38","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1259,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"40","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1260,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"41","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1261,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"42","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1263,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"44","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1264,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"45","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1266,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"47","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1268,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"49","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1269,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"50","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1271,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"52","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1272,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"53","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1273,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"54","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1274,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"55","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1275,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"56","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1276,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"57","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1277,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"58","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1278,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"59","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1279,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"60","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1281,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"62","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1282,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"63","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1283,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"64","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1284,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"65","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1285,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"66","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1286,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"67","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1287,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"68","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1288,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"69","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1290,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"71","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1292,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"73","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1293,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"74","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1295,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"76","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1296,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"77","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1297,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"78","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1299,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"80","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1301,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"82","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1302,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"83","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1303,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"84","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1304,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"85","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1306,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"87","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1308,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"89","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1309,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"90","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1311,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"92","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1312,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"93","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1313,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"94","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1314,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"95","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1315,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"96","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1316,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"97","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1317,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"98","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1318,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"99","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1319,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"100","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1320,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"101","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1322,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"103","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1323,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"104","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1324,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"105","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1325,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"106","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1326,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"107","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1327,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"108","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1328,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"109","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1329,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"110","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1331,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"112","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"},{"cardNo":"1234567","coupName":"通用卷","coupid":635,"couponsTypeId":4,"couponsTypeImage":"http://static.51ujf.cn/ ","deduction":100,"details":"无","endTime":"2017-09-01 00:00:00","id":1332,"menid":2,"phone":"13432986550","price":30,"startTime":"2016-08-06 00:00:00","status":1,"statusName":"待使用","usecode":"113","validate":"2017-09-01 00:00:00","withTime":"2016-08-06 09:04:26"}]
     * status : 0
     * success : true
     * total : 80
     */

    private String msg;
    private int status;
    private boolean success;
    private int total;
    /**
     * cardNo : 1234567
     * coupName : 通用卷
     * coupid : 635
     * couponsTypeId : 4
     * couponsTypeImage : http://static.51ujf.cn/
     * deduction : 100
     * details : 无
     * endTime : 2017-09-01 00:00:00
     * id : 1236
     * menid : 2
     * phone : 13432986550
     * price : 30
     * startTime : 2016-08-06 00:00:00
     * status : 1
     * statusName : 待使用
     * usecode : 17
     * validate : 2017-09-01 00:00:00
     * withTime : 2016-08-06 09:04:25
     */

    private List<RowsBean> rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Parcelable {
        private String cardNo;
        private String coupName;
        private int coupid;
        private int couponsTypeId;
        private String couponsTypeImage;
        private int deduction;
        private String details;
        private String endTime;
        private int id;
        private int menid;
        private String phone;
        private int price;
        private String startTime;
        private int status;
        private String statusName;
        private String usecode;
        private String validate;
        private String withTime;
        private String businessStoreName;

        public String getBusinessStoreName() {
            return businessStoreName;
        }

        public void setBusinessStoreName(String businessStoreName) {
            this.businessStoreName = businessStoreName;
        }

        public static Creator<RowsBean> getCREATOR() {
            return CREATOR;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCoupName() {
            return coupName;
        }

        public void setCoupName(String coupName) {
            this.coupName = coupName;
        }

        public int getCoupid() {
            return coupid;
        }

        public void setCoupid(int coupid) {
            this.coupid = coupid;
        }

        public int getCouponsTypeId() {
            return couponsTypeId;
        }

        public void setCouponsTypeId(int couponsTypeId) {
            this.couponsTypeId = couponsTypeId;
        }

        public String getCouponsTypeImage() {
            return couponsTypeImage;
        }

        public void setCouponsTypeImage(String couponsTypeImage) {
            this.couponsTypeImage = couponsTypeImage;
        }

        public int getDeduction() {
            return deduction;
        }

        public void setDeduction(int deduction) {
            this.deduction = deduction;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMenid() {
            return menid;
        }

        public void setMenid(int menid) {
            this.menid = menid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getUsecode() {
            return usecode;
        }

        public void setUsecode(String usecode) {
            this.usecode = usecode;
        }

        public String getValidate() {
            return validate;
        }

        public void setValidate(String validate) {
            this.validate = validate;
        }

        public String getWithTime() {
            return withTime;
        }

        public void setWithTime(String withTime) {
            this.withTime = withTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cardNo);
            dest.writeString(this.coupName);
            dest.writeInt(this.coupid);
            dest.writeInt(this.couponsTypeId);
            dest.writeString(this.couponsTypeImage);
            dest.writeInt(this.deduction);
            dest.writeString(this.details);
            dest.writeString(this.endTime);
            dest.writeInt(this.id);
            dest.writeInt(this.menid);
            dest.writeString(this.phone);
            dest.writeInt(this.price);
            dest.writeString(this.startTime);
            dest.writeInt(this.status);
            dest.writeString(this.statusName);
            dest.writeString(this.usecode);
            dest.writeString(this.validate);
            dest.writeString(this.withTime);
            dest.writeString(this.businessStoreName);
        }

        public RowsBean() {
        }

        protected RowsBean(Parcel in) {
            this.cardNo = in.readString();
            this.coupName = in.readString();
            this.coupid = in.readInt();
            this.couponsTypeId = in.readInt();
            this.couponsTypeImage = in.readString();
            this.deduction = in.readInt();
            this.details = in.readString();
            this.endTime = in.readString();
            this.id = in.readInt();
            this.menid = in.readInt();
            this.phone = in.readString();
            this.price = in.readInt();
            this.startTime = in.readString();
            this.status = in.readInt();
            this.statusName = in.readString();
            this.usecode = in.readString();
            this.validate = in.readString();
            this.withTime = in.readString();
            this.businessStoreName = in.readString();
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel source) {
                return new RowsBean(source);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.status);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeInt(this.total);
        dest.writeList(this.rows);
    }

    public PayInfoCard() {
    }

    protected PayInfoCard(Parcel in) {
        this.msg = in.readString();
        this.status = in.readInt();
        this.success = in.readByte() != 0;
        this.total = in.readInt();
        this.rows = new ArrayList<RowsBean>();
        in.readList(this.rows, RowsBean.class.getClassLoader());
    }

    public static final Creator<PayInfoCard> CREATOR = new Creator<PayInfoCard>() {
        @Override
        public PayInfoCard createFromParcel(Parcel source) {
            return new PayInfoCard(source);
        }

        @Override
        public PayInfoCard[] newArray(int size) {
            return new PayInfoCard[size];
        }
    };
}
