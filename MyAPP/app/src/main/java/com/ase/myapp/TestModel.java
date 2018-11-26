package com.ase.myapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.daobao.asus.dbbaseframe.mvp.model.BaseModel;

public class TestModel extends BaseModel implements Contract.Model{
    private boolean isFirstLocate=true;
    public TestModel(Handler handler) {
        super(handler);
    }
    @Override
    public void requestPosition(LocationClient locationClient){
        Log.d("M","requsetPosition");
        initLocation(locationClient);
        locationClient.start();
        locationClient.registerLocationListener(new MyLocationListener());
    }


    private class MyLocationListener  implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            LatLng latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(latLng);
            MapStatusUpdate update1=MapStatusUpdateFactory.zoomTo(18f);
            MyLocationData.Builder builder=new MyLocationData.Builder();
            builder.longitude(bdLocation.getLongitude());
            builder.latitude(bdLocation.getLatitude());
            MyLocationData data=builder.build();
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
            currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
            currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
            currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");


            Message message=new Message();
            Message message1=new Message();
            Message message2=new Message();
            message.what=1;
            message1.what=1;
            message2.what=2;
            message1.obj=update1;
            message.obj=update;
            message2.obj=data;
            sendMessage(message);
            sendMessage(message1);
            sendMessage(message2);
            Log.d("xinxi", String.valueOf(currentPosition));
        }
    }


    private void initLocation(LocationClient locationClient) {
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setIsNeedAltitude(true);
        option.setIsNeedLocationDescribe(true);
        option.setCoorType("bd09ll");
        option.setNeedDeviceDirect(true);
//        option.setScanSpan(5000);
        option.setOpenAutoNotifyMode(3000,1,LocationClientOption.LOC_SENSITIVITY_HIGHT);
        locationClient.setLocOption(option);
    }

}
