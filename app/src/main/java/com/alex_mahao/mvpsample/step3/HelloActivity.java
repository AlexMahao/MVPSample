package com.alex_mahao.mvpsample.step3;

/**
 * Created by Alex_MaHao on 2016/4/18.
 */
public class HelloActivity extends BasePresenterActivity<HelloVu> {

    @Override
    protected void onBindView() {

        vu.setMessage("ss");

    }


    @Override
    protected void onVuDestroy() {
        super.onVuDestroy();
    }

    @Override
    public Class<HelloVu> getVuClass() {
        return HelloVu.class;
    }
}
