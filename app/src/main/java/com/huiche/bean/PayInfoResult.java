package com.huiche.bean;

/**
 * Created by Administrator on 2016/8/22.
 */
public class PayInfoResult {

    /**
     * code : 0
     * value : {"msg":"2","obj":{"appId":"wx32d54bb13d72e48b","nonceStr":"29558b22a8","packages":"prepay_id=wx20160822103558333128a1150997336947","paySign":"a9ee31208b10bd756816a9369744578f","signType":"MD5","timeStamp":"1471833358"},"success":false}
     */

    private RowsBean rows;
    /**
     * rows : {"code":0,"value":{"msg":"2","obj":{"appId":"wx32d54bb13d72e48b","nonceStr":"29558b22a8","packages":"prepay_id=wx20160822103558333128a1150997336947","paySign":"a9ee31208b10bd756816a9369744578f","signType":"MD5","timeStamp":"1471833358"},"success":false}}
     * status : 0
     * success : true
     */

    private int status;
    private boolean success;

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
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

    public static class RowsBean {
        private int code;
        /**
         * msg : 2
         * obj : {"appId":"wx32d54bb13d72e48b","nonceStr":"29558b22a8","packages":"prepay_id=wx20160822103558333128a1150997336947","paySign":"a9ee31208b10bd756816a9369744578f","signType":"MD5","timeStamp":"1471833358"}
         * success : false
         */

        private ValueBean value;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public ValueBean getValue() {
            return value;
        }

        public void setValue(ValueBean value) {
            this.value = value;
        }

        public static class ValueBean {
            private String msg;
            /**
             * appId : wx32d54bb13d72e48b
             * nonceStr : 29558b22a8
             * packages : prepay_id=wx20160822103558333128a1150997336947
             * paySign : a9ee31208b10bd756816a9369744578f
             * signType : MD5
             * timeStamp : 1471833358
             */

            private ObjBean obj;
            private boolean success;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public ObjBean getObj() {
                return obj;
            }

            public void setObj(ObjBean obj) {
                this.obj = obj;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public static class ObjBean {
                private String appId;
                private String nonceStr;
                private String packages;
                private String paySign;
                private String signType;
                private String timeStamp;

                public String getAppId() {
                    return appId;
                }

                public void setAppId(String appId) {
                    this.appId = appId;
                }

                public String getNonceStr() {
                    return nonceStr;
                }

                public void setNonceStr(String nonceStr) {
                    this.nonceStr = nonceStr;
                }

                public String getPackages() {
                    return packages;
                }

                public void setPackages(String packages) {
                    this.packages = packages;
                }

                public String getPaySign() {
                    return paySign;
                }

                public void setPaySign(String paySign) {
                    this.paySign = paySign;
                }

                public String getSignType() {
                    return signType;
                }

                public void setSignType(String signType) {
                    this.signType = signType;
                }

                public String getTimeStamp() {
                    return timeStamp;
                }

                public void setTimeStamp(String timeStamp) {
                    this.timeStamp = timeStamp;
                }
            }
        }
    }
}
