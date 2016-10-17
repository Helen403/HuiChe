package com.huiche.activity.mine;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.adapter.Adapter_CarBeautiful;
import com.huiche.adapter.Adapter_CarBeautiful2;
import com.huiche.adapter.Adapter_CityCar_2;
import com.huiche.adapter.Adapter_City_Car;
import com.huiche.base.BaseActivity;
import com.huiche.bean.CarBeautiful2Bean;
import com.huiche.bean.CarBeautifulBean;
import com.huiche.bean.CityCar2Bean;
import com.huiche.bean.CityCarBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/29.
 */
public class CarBeautifulActivity extends BaseActivity {

    ListView listView;
    Adapter_CarBeautiful adapter_carBeautiful;
    ImageButton imageLeft_titil_all;
    RelativeLayout ll,rl2;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_car_beautiful);
        listView = (ListView) findViewById(R.id.listview);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
        ll = (RelativeLayout) findViewById(R.id.ll);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
    }

    @Override
    public void initData() {
        ArrayList<CarBeautifulBean> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            data.add(new CarBeautifulBean());

        }
        adapter_carBeautiful = new Adapter_CarBeautiful(data);
        listView.setAdapter(adapter_carBeautiful);
    }

    @Override
    public void setListeners() {
        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(ll);
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow2(ll);
            }
        });
    }


    private void showPopupWindow(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_popwindow_view, null);

        ImageView iv_1 = (ImageView) view.findViewById(R.id.iv_1);


        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

        final PopupWindow popupWindow = new PopupWindow(view, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        ListView listview = (ListView) view.findViewById(R.id.listview_1);
        ArrayList<CityCarBean> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            data.add(new CityCarBean());
        }
        Adapter_City_Car adapter = new Adapter_City_Car(data);
        listview.setAdapter(adapter);


        ListView listview_2 = (ListView) view.findViewById(R.id.listview_2);
        ArrayList<CityCar2Bean> data2 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            data2.add(new CityCar2Bean());
        }
        Adapter_CityCar_2 adapter_cityCar_2 = new Adapter_CityCar_2(data2);
        listview_2.setAdapter(adapter_cityCar_2);
    }

    private void showPopupWindow2(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_popwindow_view2, null);

        ImageView iv_1 = (ImageView) view.findViewById(R.id.iv_1);


        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

        final PopupWindow popupWindow = new PopupWindow(view, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        ListView listview = (ListView) view.findViewById(R.id.listview);
        ArrayList<CarBeautiful2Bean> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(new CarBeautiful2Bean());
        }
        Adapter_CarBeautiful2 adapter = new Adapter_CarBeautiful2(data);
        listview.setAdapter(adapter);
    }


}
