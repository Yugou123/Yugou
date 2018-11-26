package com.ase.myapp;

import android.os.Message;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;

public interface Contract {
    interface View{
        void PositionResponse(Message message);
        void DispMyLocation(Message message);
       // void loginResponse(Message msg);
       // void jump();
    }
    interface Model{
        void requestPosition(LocationClient locationClient);
       // void login(String a,String p);
    }
}
