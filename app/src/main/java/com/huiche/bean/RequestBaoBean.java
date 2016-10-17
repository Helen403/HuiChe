package com.huiche.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/5.
 */
public class RequestBaoBean implements Parcelable {


    /**
     * success : true
     * status : 0
     * msg : 获取揽客红包信息
     * rows : {"id":863,"context":"测试","businessStoreId":180,"payType":0,"image":"https://static.51ujf.cn/image/ad/subject/1473040831036.png","businessStoreName":"何康的店","businessStoreImage":"https://static.51ujf.cn/image/activities/1444881764082.jpg"}
     */

    public boolean success;
    public int status;
    public String msg;
    /**
     * id : 863
     * context : 测试
     * businessStoreId : 180
     * payType : 0
     * image : https://static.51ujf.cn/image/ad/subject/1473040831036.png
     * businessStoreName : 何康的店
     * businessStoreImage : https://static.51ujf.cn/image/activities/1444881764082.jpg
     */

    public RowsBean rows;

    public static class RowsBean implements Parcelable {

        public int id;
        public String context;
        public int businessStoreId;
        public int payType;
        public String image;
        public String businessStoreName;
        public String businessStoreImage;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.context);
            dest.writeInt(this.businessStoreId);
            dest.writeInt(this.payType);
            dest.writeString(this.image);
            dest.writeString(this.businessStoreName);
            dest.writeString(this.businessStoreImage);
        }

        public RowsBean() {
        }

        protected RowsBean(Parcel in) {
            this.id = in.readInt();
            this.context = in.readString();
            this.businessStoreId = in.readInt();
            this.payType = in.readInt();
            this.image = in.readString();
            this.businessStoreName = in.readString();
            this.businessStoreImage = in.readString();
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

    public RequestBaoBean() {
    }

    protected RequestBaoBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.status = in.readInt();
        this.msg = in.readString();
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Creator<RequestBaoBean> CREATOR = new Creator<RequestBaoBean>() {
        @Override
        public RequestBaoBean createFromParcel(Parcel source) {
            return new RequestBaoBean(source);
        }

        @Override
        public RequestBaoBean[] newArray(int size) {
            return new RequestBaoBean[size];
        }
    };
}
