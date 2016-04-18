package com.alex_mahao.mvpsample.step3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 对View进行实例化
 * Created by Alex_MaHao on 2016/4/18.
 */
public interface Vu {

    void init(LayoutInflater inflater, ViewGroup container);

    View getView();

}
