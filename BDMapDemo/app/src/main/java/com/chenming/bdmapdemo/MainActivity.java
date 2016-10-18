package com.chenming.bdmapdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;


public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    BaiduMap mBaiduMap ;
    //地图切换button
    Button [] mapTypeButtons = new Button[3];
    TextView textView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.bmapView);
        textView = (TextView)findViewById(R.id.text) ;

        //获取地图
        mBaiduMap = mMapView.getMap();
        //设置地图类型
        for(int i=0;i<mapTypeButtons.length;i++){
            //学习 获取动态id
            int buttonId = getResources().getIdentifier("mapTypeButton"+i,"id",getPackageName() ) ;
            mapTypeButtons[i] = (Button)findViewById(buttonId);
            mapTypeButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ( v.getId() ){
                        case R.id.mapTypeButton0:
                            //普通地图
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                            break;
                        case R.id.mapTypeButton1:
                            //卫星地图
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                            break;
                        case R.id.mapTypeButton2:
                            //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                            break;
                    }
                }
            });
        }

        //地图上标注（维度 经度）所在的点
        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        //在上面覆盖文字
        //定义文字所显示的坐标点
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(30)
                .fontColor(0xFFFF00FF)
                .text("在这里上车")
                .rotate(0)
                .position(point);
        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);


        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                /**
                 * 地图单击事件回调函数
                 * @param point 点击的地理坐标
                 */
                StringBuffer sb = new StringBuffer(256);
                sb.append("维度 : ");
                sb.append(latLng.latitude);
                sb.append("经度 : ");
                sb.append(latLng.longitude);
                textView.setText(sb.toString());
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                /**
                 * 地图内 Poi 单击事件回调函数
                 * @param poi 点击的 poi 信息
                 */
                return false;
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
