package com.huiche.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class RecyclerAdapter_CityRange extends RecyclerView.Adapter<RecyclerAdapter_CityRange.MyHolder> {
    private WindowManager windowManager;
    private Context mContext;
    private List<String> dataList;
    private onItemClickListener onItemClickListener;

    /**
     * 没有尾部加载更多使用该构造方法
     *
     * @param context
     * @param list
     */
    public RecyclerAdapter_CityRange(Context context, List<String> list) {
        mContext = context;
        this.dataList = list;
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 设置监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_city_range, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.textView.setText(dataList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.selectCityRange(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_range);
            int displayHeight = windowManager.getDefaultDisplay().getHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            params.height = displayHeight/13;
        }
    }

    /**
     * 回调接口
     */
    public interface onItemClickListener {
        void selectCityRange(int position);
    }

}
