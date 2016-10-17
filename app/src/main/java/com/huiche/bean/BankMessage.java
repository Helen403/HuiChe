package com.huiche.bean;

/**
 * 银行卡信息
 *
 * @author Administrator
 */
public class BankMessage {
    public String fBankName;
    public String fBankNo;
    public String fName;
    public String id;

    public BankMessage() {

    }

    public String getfBankName() {
        return fBankName;
    }

    public void setfBankName(String fBankName) {
        this.fBankName = fBankName;
    }

    public String getfBankNo() {
        return fBankNo;
    }

    public void setfBankNo(String fBankNo) {
        this.fBankNo = fBankNo;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BankMessage [fBankName=" + fBankName + ", fBankNo=" + fBankNo
                + ", fName=" + fName + ", id=" + id + "]";
    }


}
