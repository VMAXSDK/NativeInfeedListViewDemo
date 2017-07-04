package com.vmax.demo.nativeInfeedList;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.vmax.android.ads.api.VmaxAdView;





/**Its Recommended To Use VMAX plugin For Android Studio To Add Your Dependencies
 and Manage Changes in AndroidManifest as Well as Proguard,
 However You Can Manually Do This By Referring To Our Documentation Or following this Demo Project  */


public class MainActivity extends AppCompatActivity {

    public   VmaxAdView vmaxAdView,adView;
    ListView listView;
    RelativeLayout adContainer;
    LayoutInflater layoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutInflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        vmaxAdView=(VmaxAdView) layoutInflater.inflate(R.layout.vmax_custom_native_layout,null);


        InitializeListView();


    }

    public void InitializeListView()
    {
        listView=(ListView)findViewById(R.id.list_view);
        InitData initData=new InitData(getApplicationContext());
        listviewadapter listviewadapter=new listviewadapter(getApplicationContext(),initData.fillList(),vmaxAdView);
        listView.setAdapter(listviewadapter);
    }





    /** Handle vmaxAdView object for Activity Lifecycle changes */


    @Override
    protected void onDestroy() {
        if (vmaxAdView != null) {
       /** To Destroy vmaxAdView when Activity Is No Longer Available  */
            vmaxAdView.onDestroy();
        }
        super.onDestroy();
    }



}
