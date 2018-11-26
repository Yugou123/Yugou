package com.ase.myapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.daobao.asus.dbbaseframe.mvp.presenter.BasePresenter;

public class Testpresenter extends BasePresenter<TestModel,TestActivity> {
    public Testpresenter(TestActivity view) {
        super(view);
        Log.d("TestP","super view");
    }

    @Override
    public TestModel binModel(Handler handler) {
        Log.d("TestP","bindModel");
        return new TestModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        switch (msg.what){
            case 1:
                Log.d("P","msg");
                mView.PositionResponse(msg);
                break;
            case 2:
                mView.DispMyLocation(msg);
                break;
//                mView.loginResponse(msg);
//                break;
//            case 2:
//                mView.loginResponse(msg);
//                mView.jump();
//                break;
        }
    }

    public void requestPosition(LocationClient locationClient){
        Log.d("P","M.requstPosition");
        mModel.requestPosition(locationClient);
    }
//    public void login(String a,String b){
//        mModel.login(a,b);
//    }
}
