package com.huiche.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseFragment;
import com.huiche.utils.WindowUtils;
import com.huiche.view.CircleImageView;
import com.huiche.view.RoundImageView;
import com.huiche.view.WhewView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * U-U fragment
 * 
 * @author Administrator
 * 
 */
public class U_UFragment extends BaseFragment implements OnClickListener {
	private View convertView;
	private CircleImageView civUUIcon;
	private WhewView whewView_U_U;
//	private ImageButton imb_titleBack_UU;
	private TextView tv_titleText_UU;
//	private RelativeLayout rl_randomView_UU;
	private RelativeLayout rl_mainContent_UU;
	private Context mContext;
	private int screenWidth;
	private int screenHeight;
	private int iconWidth;
	private int iconHeight;
	private static final int UU = 1;
	private List<Point> pointAllList = new ArrayList<Point>();
	private ArrayList<Point> pointList = new ArrayList<Point>();

	public U_UFragment() {
	}

	@Override
	public View inflateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.fragment_uu, container, false);
		mContext = getActivity();
		return convertView;
	}

	@Override
	public void findViews() {
		civUUIcon = (CircleImageView) convertView.findViewById(R.id.civ_uuIcon);
		whewView_U_U = (WhewView) convertView.findViewById(R.id.whewView_U_U);
		tv_titleText_UU = (TextView) convertView
				.findViewById(R.id.tv_titleText_UU);
//		rl_randomView_UU = (RelativeLayout) convertView
//				.findViewById(R.id.rl_randomView_UU);
		rl_mainContent_UU = (RelativeLayout) convertView
				.findViewById(R.id.rl_mainContent_UU);

		// 设置显示比例为屏幕宽度1/3
		WindowUtils windowUtils = new WindowUtils(mContext);
		screenWidth = windowUtils.getdisplayWidth();
		screenHeight = windowUtils.getdisplayHeight();
		iconWidth = screenWidth / 3;
		iconHeight = screenWidth / 3;
		LayoutParams params = new LayoutParams(iconWidth,
				iconHeight);
		// 必须调用此方法才能居中，因代码动态设置LayoutParams原xml已定义的属性失效
		// params.setMargins(left, top, right, bottom);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		civUUIcon.setLayoutParams(params);
		civUUIcon.setImageResource(R.drawable.user_comments_head);
		whewView_U_U.setCenterRadius(iconWidth / 2);
		whewView_U_U.setMaxWidth(screenHeight/2);
		whewView_U_U.setSpeed(2);
		whewView_U_U.setAlphaSpeed(2);
		whewView_U_U.setRippleWidthScale(8);
//		SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//		music = soundPool.load(mContext, R.raw.hongbao_gq, 1);

	}

	@Override
	public void initData() {
		tv_titleText_UU.setText("发现");

	}

	@Override
	public void setListeners() {
		civUUIcon.setOnClickListener(this);

	}

	@Override
	public void initViews() {


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 开始U-U
		case R.id.civ_uuIcon:
			startUU();
			getRandomPoint2(6);
			break;

		default:
			break;
		}

	}

	/**
	 * 随机获取屏幕一点，随机显示图片
	 *
	 * @param count
	 *            要随机产生图片的数量
	 */
	private void getRandomPoint(int count) {
		// List<Rect> rectList=new ArrayList<Rect> ();
		Random random = new Random();
//		xList = new ArrayList<Integer>();
//		yList = new ArrayList<Integer>();
		pointList = new ArrayList<Point>();
		// 在屏幕里随机生成x,y,重复点不添加
		for (int i = 0; i < count; i++) {
			int x = random.nextInt(screenWidth - iconWidth);
			// xList.add(x);
			int y = random.nextInt(screenHeight - iconHeight);
			// yList.add(y);
			Point point = new Point(x, y);
			if (pointList.size() == 0) {
				pointList.add(point);
			} else {
				// 遍历集合中的点比较，重复点不添加
				for (int j = 0; j < pointList.size(); j++) {
					Point exitPoint = pointList.get(j);
					if (point.x >= exitPoint.x
							&& point.x <= exitPoint.x + iconWidth / 2
							&& point.y >= exitPoint.y
							&& point.y <= exitPoint.y + iconHeight / 2) {
						// 新增的点在原来的点区域内，不添加
						i--;// 重新添加随机点
					} else {
						pointList.add(point);
					}
				}

			}

		}
		// 在屏幕里随机生成y
		// for (int j = 0; j < count; j++) {
		// int y = random.nextInt(screenHeight - iconHeight);
		// yList.add(y);
		// }
		// for (int k = 0; k < xList.size(); k++) {
		// Rect rect=new
		// Rect(xList.get(k),yList.get(k),xList.get(k)+iconWidth/2,
		// yList.get(k)+iconHeight/2);
		// rectList.add(rect);
		// Point point = new Point();
		// point.set(xList.get(k), yList.get(k));
		// pointList.add(point);
		//
		// }
		// for test 随机显示的图片资源
		int[] imgRes = { R.drawable.shangj_beij11, R.drawable.shangj_beij11, R.drawable.shangj_beij11,
				R.drawable.add_button_blue, R.drawable.main_button,
				R.drawable.open_a_red_envelope };
		// civUUIcon.setImageResource(R.drawable.bb);
		for (int i = 0; i < imgRes.length; i++) {
			RoundImageView randomView = new RoundImageView(mContext);
			// 设置显示比例
			LayoutParams params = new LayoutParams(
					iconWidth / 2, iconHeight / 2);
			// 设置显示位置
			params.setMargins(pointList.get(i).x, pointList.get(i).y, 0, 0);
			randomView.setBorderThickness(2);
			randomView.setBorderOutsideColor(getResources().getColor(
					R.color.white));
			randomView.setLayoutParams(params);
			randomView.setImageResource(imgRes[i]);
			rl_mainContent_UU.addView(randomView);
		}
	}

	/**
	 * @param count
	 *            随机获取屏幕一点，随机显示图片
	 */
	private void getRandomPoint2(int count) {
		int randowWidth = screenWidth / 6;
		int randowHeight = screenWidth / 6;
		int startX = 0;
		int startY = 0;
		pointAllList.clear();
		pointList.clear();
		float civX = civUUIcon.getX();
		float civY = civUUIcon.getY();
		for (int i = 0; i < screenWidth / randowWidth; i++) {
			for (int j = 0; j < screenHeight / randowHeight; j++) {
				if (startX < screenWidth && startY < screenHeight) {
					// 不在中心圆内才添加，防止覆盖中心图片
					if (startX + randowWidth < civX
							|| startX > civX + iconWidth
							|| startY + randowHeight < civY
							|| startY > civY + iconHeight) {
						Point point = new Point(startX, startY);
						pointAllList.add(point);
						startY += randowHeight;
					}
				}
			}
			startX += randowWidth;
			startY = 0;
		}
		// 随机取出6点
		Random random = new Random();
		for (int k = 0; k < count; k++) {
			int pointIndex = random.nextInt(pointAllList.size());
			Point point = pointAllList.get(pointIndex);
			pointList.add(point);
			pointAllList.remove(pointIndex);
		}
		int[] imgRes = { R.drawable.shangj_beij11, R.drawable.shangj_beij11, R.drawable.shangj_beij11,
				R.drawable.add_button_blue, R.drawable.main_button,
				R.drawable.open_a_red_envelope };
		civUUIcon.setImageResource(R.drawable.user_comments_head);
		for (int i = 0; i < imgRes.length; i++) {
			RoundImageView randomView = new RoundImageView(mContext);
			// 设置显示比例
			LayoutParams params = new LayoutParams(randowWidth,
					randowHeight);
			// 设置显示位置
			params.setMargins(pointList.get(i).x, pointList.get(i).y, 0, 0);
			randomView.setBorderThickness(2);
			randomView.setBorderOutsideColor(getResources().getColor(
					R.color.white));
			randomView.setLayoutParams(params);
			randomView.setImageResource(imgRes[i]);
			rl_mainContent_UU.addView(randomView);
		}

	}

	/**
	 * 开始/结束U-U
	 */
	private void startUU() {
		if (!whewView_U_U.isStarting()) {
			whewView_U_U.start();
			mHandler.sendEmptyMessage(UU);
		} else {
			whewView_U_U.stop();
			// 结束进程
			mHandler.removeMessages(UU);
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == UU) {
				// 循环播放音效10s一次
				// mHandler.sendEmptyMessageDelayed(UU, 10000);
				// soundPool.play(music, 1, 1, 0, 0, 1);
			}

		}
	};

}
