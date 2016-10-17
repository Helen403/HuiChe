package com.huiche.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/5.
 */
public class RedBaoDetailFillBean implements Parcelable {


    /**
     * success : true
     * status : 0
     * msg :
     * rows : {"integralQuota":12,"businessStoreId":180,"businessName":"何康的店","count":2,"getIntegralQuota":7.4,"businessStoreImage":"https://static.51ujf.cn/image/activities/1444881764082.jpg","context":"我","image":"https://static.51ujf.cn/image/ad/subject/1473047386503.png","integral":7.4,"getCount":1}
     */

    public boolean success;
    public int status;
    public String msg;
    /**
     * integralQuota : 12
     * businessStoreId : 180
     * businessName : 何康的店
     * count : 2
     * getIntegralQuota : 7.4
     * businessStoreImage : https://static.51ujf.cn/image/activities/1444881764082.jpg
     * context : 我
     * image : https://static.51ujf.cn/image/ad/subject/1473047386503.png
     * integral : 7.4
     * getCount : 1
     */

    public RowsBean rows;

    public static class RowsBean implements Parcelable {

        public int integralQuota;
        public int businessStoreId;
        public String businessName;
        public int count;
        public double getIntegralQuota;
        public String businessStoreImage;
        public String context;
        public String image;
        public double integral;
        public int getCount;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.integralQuota);
            dest.writeInt(this.businessStoreId);
            dest.writeString(this.businessName);
            dest.writeInt(this.count);
            dest.writeDouble(this.getIntegralQuota);
            dest.writeString(this.businessStoreImage);
            dest.writeString(this.context);
            dest.writeString(this.image);
            dest.writeDouble(this.integral);
            dest.writeInt(this.getCount);
        }

        public RowsBean() {

        }
        protected RowsBean(Parcel in) {
            this.integralQuota = in.readInt();
            this.businessStoreId = in.readInt();
            this.businessName = in.readString();
            this.count = in.readInt();
            this.getIntegralQuota = in.readDouble();
            this.businessStoreImage = in.readString();
            this.context = in.readString();
            this.image = in.readString();
            this.integral = in.readDouble();
            this.getCount = in.readInt();
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
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status);
        dest.writeString(this.msg);
        dest.writeParcelable(this.rows, flags);
    }

    public RedBaoDetailFillBean() {
    }

    protected RedBaoDetailFillBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.status = in.readInt();
        this.msg = in.readString();
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Creator<RedBaoDetailFillBean> CREATOR = new Creator<RedBaoDetailFillBean>() {
        @Override
        public RedBaoDetailFillBean createFromParcel(Parcel source) {
            return new RedBaoDetailFillBean(source);
        }

        @Override
        public RedBaoDetailFillBean[] newArray(int size) {
            return new RedBaoDetailFillBean[size];
        }
    };
}
