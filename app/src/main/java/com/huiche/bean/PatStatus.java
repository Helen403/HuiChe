package com.huiche.bean;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PatStatus {

    /**
     * success : true
     * status : 0
     * msg : 下单成功!
     * rows : 317453
     */

    private boolean success;
    private int status;
    private String msg;
    private int rows;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
