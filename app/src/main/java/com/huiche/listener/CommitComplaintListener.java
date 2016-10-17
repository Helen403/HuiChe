package com.huiche.listener;

/**
 * 提交投诉回调，返回输入的内容和类型
 *
 * @author Administrator
 */
public interface CommitComplaintListener {
    void sendEditText(String type, String content);

}

