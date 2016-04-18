package com.alex_mahao.mvpsample.step3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex_mahao.mvpsample.R;

/**
 * Created by Alex_MaHao on 2016/4/18.
 */
public class HelloVu implements Vu {

    private View view;
    private TextView tv_hello;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {

        //初始化视图
        view = inflater.inflate(R.layout.activity_main,container,false);

        //查找控件
        tv_hello = ((TextView) view.findViewById(R.id.tv_hello));

    }

    @Override
    public View getView() {
        return view;
    }


    //View层的方法，更新UI
    public void setMessage(String msg){
        tv_hello.setText(msg);
    }


}
