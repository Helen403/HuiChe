package com.huiche.listener;

/**
 * recycler item回调
 *
 * @author Administrator
 */
public interface RecyclerViewOnItemListener {
    void onClick(int position);

    void onLongClick(int position);
}
