package com.huiche.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.PopReportErrorAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.utils.WindowUtils;

import java.util.ArrayList;

public class ReportErrorFragment extends BaseFragment implements
        OnClickListener, OnItemClickListener {
    private View convertView;
    private TextView tv_reportErrorTitle;
    private ImageButton imb_reportErrorList;
    private EditText et_reportErrorDetail;
    private ArrayList<String> list;
    private ListView lv_reporterror;
    private boolean hasShowEdit;
    private int currentPosition = -1;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_reporterror,
                container, false);

        return convertView;
    }

    @Override
    public void findViews() {
        tv_reportErrorTitle = (TextView) convertView
                .findViewById(R.id.tv_reportErrorTitle);
        imb_reportErrorList = (ImageButton) convertView
                .findViewById(R.id.imb_reportErrorList);
        et_reportErrorDetail = (EditText) convertView
                .findViewById(R.id.et_reportErrorDetail);
        lv_reporterror = (ListView) convertView
                .findViewById(R.id.lv_reporterror);

    }

    @Override
    public void initData() {
        tv_reportErrorTitle.setText("请选择报错类型");
        et_reportErrorDetail.setHint("请输入您的内容！我们后台人员会及时联系您，并处理你的问题。");
        et_reportErrorDetail.setHintTextColor(getResources().getColor(
                R.color.text_color_hint));
        // 设置高度为屏幕的1/4
        WindowUtils windowUtils = new WindowUtils(getActivity());
        int height = windowUtils.getdisplayHeight();
        et_reportErrorDetail.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, height / 4));
        getProblemList();
        PopReportErrorAdapter mAdapter = new PopReportErrorAdapter(getActivity(), list);
        lv_reporterror.setAdapter(mAdapter);
        lv_reporterror.setOnItemClickListener(this);
    }

    @Override
    public void setListeners() {
        imb_reportErrorList.setOnClickListener(this);
    }

    @Override
    public void initViews() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imb_reportErrorList:
                showListView();
                break;

            default:
                break;
        }
    }

    /**
     * 初始化列表数据
     */
    private void getProblemList() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String str = "中山分店" + i;
            list.add(str);
        }

    }

    /**
     * 弹出listview
     */
    private void showListView() {
        if (lv_reporterror != null) {
            if (!lv_reporterror.isShown()) {
                lv_reporterror.setVisibility(View.VISIBLE);
                tv_reportErrorTitle.setText("请选择报错类型");
                if (et_reportErrorDetail.isShown()) {
                    et_reportErrorDetail.setVisibility(View.GONE);
                }
            } else {
                if (!et_reportErrorDetail.isShown() && hasShowEdit) {
                    if (currentPosition != -1) {
                        tv_reportErrorTitle.setText(list.get(currentPosition));
                    }
                    et_reportErrorDetail.setVisibility(View.VISIBLE);
                }
                lv_reporterror.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        hasShowEdit = true;
        et_reportErrorDetail.setVisibility(View.VISIBLE);
        lv_reporterror.setVisibility(View.INVISIBLE);
        et_reportErrorDetail.setText("");// 清空之前的内容
        tv_reportErrorTitle.setText(list.get(position));
        currentPosition = position;

    }

    /**
     * 报错类型
     *
     */
    public String getProblemType() {

        return tv_reportErrorTitle.getText().toString();

    }

    /**
     * 报错内容
     *
     */
    public String getProblemText() {

        return et_reportErrorDetail.getText().toString();

    }

}
