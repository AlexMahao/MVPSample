package com.alex_mahao.mvpsample.step3;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Alex_MaHao on 2016/4/18.
 */
public abstract  class BasePresenterActivity<T extends Vu> extends Activity {

    //View 视图接口，保持对View层的控制
    protected T vu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //获取View层的实例
            vu = getVuClass().newInstance();

            //初始化View视图
            vu.init(getLayoutInflater(),null);

            //获取View视图并设置为当前显示布局
            setContentView(vu.getView());

            //回调，
            onBindView();


        }  catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        //销毁
        onVuDestroy();

        super.onDestroy();
        vu = null;

    }

    protected  void onVuDestroy(){}

    protected  void onBindView(){}

    public abstract Class<T> getVuClass();
}
