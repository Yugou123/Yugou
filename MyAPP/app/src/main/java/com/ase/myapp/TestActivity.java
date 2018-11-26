package com.ase.myapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.daobao.asus.dbbaseframe.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity<Testpresenter> implements Contract.View, View.OnClickListener {
    public LocationClient locationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
//    private Button bt;
    private FloatingActionButton floatingActionButton;
    private FloatingActionButton floatingActionButton1;
    private EditText editText;
    @Override
    public Testpresenter binPresenter() {
        return new Testpresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient=new LocationClient(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
        mapView=findViewById(R.id.bmapView);
//        bt=findViewById(R.id.bt1);
        floatingActionButton=findViewById(R.id.flbt);
        floatingActionButton1=findViewById(R.id.flbt1);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        askforacc();
//        Button bt=findViewById(R.id.login);
//        bt.setOnClickListener(this);
    }

    private void askforacc() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(TestActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(TestActivity.this,permissions,1);
        }else{
            Log.d("Testa","requestPosition");
            mPresenter.requestPosition(locationClient);
//            bt.setOnClickListener(this);
            floatingActionButton.setOnClickListener(this);
            floatingActionButton1.setOnClickListener(this);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ACT","destroy");
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ACT","pause");
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ACT","resume");
        mapView.onResume();
    }

    @Override
    public void PositionResponse(Message message) {
        baiduMap.animateMapStatus((MapStatusUpdate) message.obj);
//        pTv.setText((CharSequence) message.obj);
        Log.d("Testa","s");
    }

    @Override
    public void DispMyLocation(Message message) {
        baiduMap.setMyLocationData((MyLocationData) message.obj);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意才能使用",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    mPresenter.requestPosition(locationClient);
                }else{
                    Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.flbt:
                locationClient.stop();
                locationClient=new LocationClient(getApplicationContext());
                mPresenter.requestPosition(locationClient);
                break;
            case R.id.flbt1:
//                String edtext;
//                edtext=editText.getText().toString();
                Intent intent=new Intent(TestActivity.this,InerActivity.class);
//                intent.putExtra("search",edtext);
                startActivity(intent);
                break;
                default:
                    break;
        }


    }

//    @Override
//    public void onClick(View view) {
//        EditText a=findViewById(R.id.acc);
//        EditText p=findViewById(R.id.pas);
//        String ac=a.getText().toString();
//        String pa=p.getText().toString();
//        mPresenter.login(ac,pa);
//    }
}
