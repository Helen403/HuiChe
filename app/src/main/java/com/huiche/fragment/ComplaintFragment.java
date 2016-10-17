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


public class ComplaintFragment extends BaseFragment implements
        OnItemClickListener, OnClickListener {
	private View convertView;
	private WindowUtils windowUtils;
	private TextView tv_complaintTitle;
	private ImageButton imb_complaintList;
	private EditText et_complaintDetail;
	private ListView lv_complaint;
	private PopReportErrorAdapter mAdapter;
	private ArrayList<String> list;
	private boolean hasShowEdit;
	private int currentPosition=-1;

	@Override
	public View inflateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_complaint, container,
				false);

		return convertView;
	}

	@Override
	public void findViews() {
		tv_complaintTitle = (TextView) convertView
				.findViewById(R.id.tv_complaintTitle);
		imb_complaintList = (ImageButton) convertView
				.findViewById(R.id.imb_complaintList);
		et_complaintDetail = (EditText) convertView
				.findViewById(R.id.et_complaintDetail);
		lv_complaint = (ListView) convertView.findViewById(R.id.lv_complaint);

	}

	@Override
	public void initData() {
		tv_complaintTitle.setText("请选择投诉问题");
		et_complaintDetail.setHint("请输入您的内容！我们后台人员会及时联系您，并处理你的问题。");
		et_complaintDetail.setHintTextColor(getResources().getColor(
				R.color.text_color_hint));
		// 设置高度为屏幕的1/4
		windowUtils = new WindowUtils(getActivity());
		int height = windowUtils.getdisplayHeight();
		et_complaintDetail.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, height / 4));
		getProblemList();
		mAdapter = new PopReportErrorAdapter(getActivity(), list);
		lv_complaint.setAdapter(mAdapter);
		lv_complaint.setOnItemClickListener(this);
	}

	@Override
	public void setListeners() {
		imb_complaintList.setOnClickListener(this);
	}

	@Override
	public void initViews() {


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.imb_complaintList:
			showListView();
			break;

		default:
			break;
		}
	}

	/*/**
     * 初始化列表数据
     */private void getProblemList() {
		list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			String str = "何康大保健中山分店" + i;
			list.add(str);
		}

	}

	/*/**
     * 弹出listview
     */private void showListView() {
		if (lv_complaint != null) {
			if (!lv_complaint.isShown()) {
				lv_complaint.setVisibility(View.VISIBLE);
				tv_complaintTitle.setText("请选择投诉问题");
				if (et_complaintDetail.isShown()) {
					et_complaintDetail.setVisibility(View.GONE);
				}
			} else {
				if (!et_complaintDetail.isShown() && hasShowEdit) {
					if(currentPosition!=-1){
						tv_complaintTitle.setText(list.get(currentPosition));
					}
					et_complaintDetail.setVisibility(View.VISIBLE);
				}
				lv_complaint.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		hasShowEdit = true;
		et_complaintDetail.setVisibility(View.VISIBLE);
		lv_complaint.setVisibility(View.INVISIBLE);
		et_complaintDetail.setText("");// 清空之前的内容
		tv_complaintTitle.setText(list.get(position));
		currentPosition=position;
	}

	/*/**
     * 投诉类型
     *
     * @return
     */public String getProblemType() {

		return tv_complaintTitle.getText().toString();

	}

	/*/**
     * 投诉内容
     *
     * @return
     */public String getProblemText() {

		return et_complaintDetail.getText().toString();

	}
}
