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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.MediaView;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.vmax.android.ads.api.ImageLoader;
import com.vmax.android.ads.api.NativeImageDownload;
import com.vmax.android.ads.api.NativeImageDownloadListener;
import com.vmax.android.ads.api.VmaxAdSettings;
import com.vmax.android.ads.api.VmaxAdView;
import com.vmax.android.ads.common.VmaxAdListener;
import com.vmax.android.ads.exception.VmaxAdError;
import com.vmax.android.ads.nativeads.NativeAd;
import com.vmax.android.ads.nativeads.VmaxImage;
import com.vmax.android.ads.nativeads.VmaxNativeMediaView;
import com.vmax.android.ads.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;




/**Its Recommended To Use VMAX plugin For Android Studio To Add Your Dependencies
 and Manage Changes in AndroidManifest as Well as Proguard,
 However You Can Manually Do This By Referring To Our Documentation Or following this Demo Project  */


public class MainActivity extends AppCompatActivity {

    public   VmaxAdView vmaxAdView,adView;
    ListView listView;
    RelativeLayout adContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        loadNativeInfeed();

    }

    public void InitializeListView()
    {
        listView=(ListView)findViewById(R.id.list_view);
        InitData initData=new InitData(getApplicationContext());
        listviewadapter listviewadapter=new listviewadapter(getApplicationContext(),initData.fillList(),adContainer);
        listView.setAdapter(listviewadapter);
    }

    /** Method for adding Native Infeed */
    public void loadNativeInfeed()
    {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final RelativeLayout nativecustomLayout = (RelativeLayout) inflater
                .inflate(getResources().getIdentifier("native_ad_custom_layout", "layout",
                        getPackageName()), null);

        /** Initializing vmaxAdView with an Adspot, Repalce With the adspot Configured by you */
         vmaxAdView = new VmaxAdView(this, "9060b6e3", VmaxAdView.UX_NATIVE);
        VmaxAdSettings vmaxAdSettings = new VmaxAdSettings();
        vmaxAdSettings.setAdmobNativeExpressAdSize(Constants.NativeAdSize.NATIVE_AD_SIZE_FULL_WIDTH, 200);
        vmaxAdView.setAdSettings(vmaxAdSettings);
/** To Fetch Your AdvId you can check your device's Google settings under ads subMenu Or You can Run this app Once and check
 * the logs for 'AdRequested with url' under the tag vmax, from the url your Advid
 * would be one of the parameters in the post request eg. advid=2cf626f0-08ac-4a4d-933c-00ecd0256cf4*/

/** DON'T INCLUDE vmaxAdView.setTestDevices() WHILE GOING LIVE WITH YOUR PROJECT AS THIS SERVES ONLY TEST ADS;*/
        vmaxAdView.setTestDevices(VmaxAdView.TEST_via_ADVID,"REPLACE WITH YOUR ADVID");

        vmaxAdView.setAdListener(new VmaxAdListener() {
            @Override
            public void onAdError(VmaxAdError error) {

            }

            @Override
            public void onAdReady(VmaxAdView adView) {
                Toast.makeText(MainActivity.this, "Ad Cached", Toast.LENGTH_SHORT).show();
                final NativeAd nativeAd = adView.getNativeAd();
                nativecustomLayout.removeAllViews();
                if (nativeAd.getNativeAdPartner() != null && nativeAd.getNativeAdPartner().equals(Constants.AdPartner.VMAX_ADMOB) &&
                        nativeAd.getNativeAdType().equalsIgnoreCase(Constants.NativeAdType.VMAX_ADMOB_EXPRESS_AD)) {
                    /**To render Admob Express Ads */
                    nativecustomLayout.setVisibility(View.VISIBLE);
                    nativeAd.registerViewForInteraction(adView, nativecustomLayout, nativecustomLayout, null);
                    adContainer=nativecustomLayout;
                    MainActivity.this.adView=adView;
                    InitializeListView();
                } else {
                    HashSet<NativeImageDownload> nativeImageElementSet = new HashSet <> ();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    RelativeLayout fl_native = (RelativeLayout) inflater.inflate(getResources().getIdentifier("native_ad_custom_layout", "layout", getPackageName()), null);
                    List< View > listview = new ArrayList< >();
                    /**To download Ad Icon */
                    if (nativeAd.getIcon() != null) {
                        ImageView iconView = (ImageView) fl_native.findViewById(R.id.ad_image);
                        if (nativeAd.getIcon().getUrl() != null && !TextUtils.isEmpty(nativeAd.getIcon().getUrl())) {
                            String iconURL = nativeAd.getIcon().getUrl();
                            Log.d("vmax", "icon URL=" + iconURL);
                            nativeImageElementSet.add(new NativeImageDownload(iconURL, iconView, 48, 48));
                        }
                        listview.add(iconView);
                    }
                    /** To fetch Ad Title*/
                    if (nativeAd.getTitle() != null) {
                        TextView titleView = (TextView) fl_native.findViewById(R.id.vmax_custom_title);
                        titleView.setText(nativeAd.getTitle());
                        Log.e("nativeAd.getTitle()",nativeAd.getTitle());
                        listview.add(titleView);
                    }
                    /** To fetch Ad Description*/
                    if (nativeAd.getDesc() != null) {
                        TextView descView = (TextView) fl_native.findViewById(R.id.vmax_custom_desc);
                        descView.setText(nativeAd.getDesc());
                        Log.e("nativeAd.getDesc()",nativeAd.getDesc());
                        listview.add(descView);
                    }
                    /** To fetch CTA Text*/
                    if (nativeAd.getCtaText() != null) {
                        Button ctaNative = (Button) fl_native.findViewById(R.id.vmax_custom_cta);
                        ctaNative.setText(nativeAd.getCtaText());
                        Log.e("nativeAd.getDesc()",nativeAd.getDesc());
                        listview.add(ctaNative);
                    }

                    RelativeLayout media_layout = (RelativeLayout) fl_native.findViewById(R.id.vmax_custom_media_view);
                    ImageView otherImageView = (ImageView) fl_native.findViewById(R.id.vmax_custom_otherimg);
                    if (nativeAd.getMediaView() != null && nativeAd.getNativeAdPartner() != null && nativeAd.getNativeAdPartner().equals(Constants.AdPartner.VMAX_FACEBOOK)) {
                        /** To render Facebook MediaView*/
                        MediaView fbMediaView = (MediaView) nativeAd.getMediaView();
                        media_layout.removeAllViews();
                        media_layout.addView(fbMediaView);
                        media_layout.setVisibility(View.VISIBLE);
                        otherImageView.setVisibility(View.GONE);
                    } else if (nativeAd.getMediaView() != null && nativeAd.getNativeAdPartner() != null && nativeAd.getNativeAdPartner().equals(Constants.AdPartner.VMAX_NATIVE_AD)) {
                        /** To render Vmax MediaView*/
                        VmaxNativeMediaView vmaxNativeMediaView = (VmaxNativeMediaView) nativeAd.getMediaView();
                        media_layout.removeAllViews();
                        media_layout.addView(vmaxNativeMediaView);
//                        media_layout.setVisibility(View.VISIBLE);
                        otherImageView.setVisibility(View.GONE);
                    } else {
                        /** To render other Vmax Static Ad elements*/
                        media_layout.removeAllViews();
                        media_layout.setVisibility(View.GONE);
//                        otherImageView.setVisibility(View.VISIBLE);
                        VmaxImage imageMain = nativeAd.getImageMain();
                        VmaxImage imageMedium = nativeAd.getImageMedium();
                        VmaxImage imageTile = nativeAd.getImageTile();
                        VmaxImage imageBanner = nativeAd.getImageBanner();
                        if (imageMain != null && imageMain.getUrl() != null && !TextUtils.isEmpty(imageMain.getUrl())) {

                            String otherImageURL = imageMain.getUrl();
                            Log.d("vmax", "main URL=" + otherImageURL);
                            nativeImageElementSet.add(new NativeImageDownload(otherImageURL, otherImageView, 320, 200));

                        } else if (imageMedium != null && imageMedium.getUrl() != null && !TextUtils.isEmpty(imageMedium.getUrl())) {

                            String otherImageURL = imageMedium.getUrl();
                            Log.d("vmax", "medium URL=" + otherImageURL);
                            nativeImageElementSet.add(new NativeImageDownload(otherImageURL, otherImageView, 320, 200));

                        } else if (imageTile != null && imageTile.getUrl() != null && !TextUtils.isEmpty(imageTile.getUrl())) {

                            String otherImageURL = imageTile.getUrl();
                            Log.d("vmax", "tile URL=" + otherImageURL);
                            nativeImageElementSet.add(new NativeImageDownload(otherImageURL, otherImageView, 320, 200));

                        }
                        else if (imageBanner != null && imageBanner.getUrl() != null && !TextUtils.isEmpty(imageBanner.getUrl())) {

                            String otherImageURL = imageBanner.getUrl();
                            Log.d("vmax", "banner URL=" + otherImageURL);
                            nativeImageElementSet.add(new NativeImageDownload(otherImageURL, otherImageView, 320, 200));

                        }
                        listview.add(otherImageView);
                    }
                    ImageLoader imageLoader = new ImageLoader(nativeImageElementSet);
                    //setNativeImageDownloadListener() is optional, used to get notified when all image elements are successfully downloaded
                    imageLoader.setNativeImageDownloadListener(new NativeImageDownloadListener() {
                        @Override public void onTaskDone() {}
                        @Override public void onTaskError() {}
                    });
                    imageLoader.execute();
                    FrameLayout adchoiceViewLayout = (FrameLayout) fl_native.findViewById(R.id.vmax_custom_adChoiceView);

                    if (nativeAd.getNativeAdPartner() != null &&
                            nativeAd.getNativeAdPartner().equals(Constants.AdPartner.VMAX_FACEBOOK)) {
                        /** To Download AdChoice icon in case of Facebook Native Ad*/
                        if (nativeAd.getImageAdChoice() != null) {
                            ImageView adChoiceView = new ImageView(getApplicationContext());
                            if (nativeAd.getImageAdChoice().getUrl() != null && !TextUtils.isEmpty(nativeAd.getImageAdChoice().getUrl())) {
                                String adchoiceImageURL = nativeAd.getImageAdChoice().getUrl();
                                HashSet < NativeImageDownload > imageSet = new HashSet < > ();
                                imageSet.add(new NativeImageDownload(adchoiceImageURL, adChoiceView, 15, 15));
                                imageLoader = new ImageLoader(imageSet);
                                //setNativeImageDownloadListener() is optional, used to get notified when all image elements are sucessfully downloaded
                                imageLoader.setNativeImageDownloadListener(new NativeImageDownloadListener() {
                                    @Override
                                    public void onTaskDone() {

                                    }

                                    @Override
                                    public void onTaskError() {

                                    }
                                });
                                imageLoader.execute();
                            }
                            adChoiceView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String adChoiceActionUrl = nativeAd.getAdChoiceUrl();
                                    Uri uri = Uri.parse(adChoiceActionUrl);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    MainActivity.this.startActivity(intent);
                                }
                            });
                            adChoiceView.setVisibility(View.VISIBLE);
                            adchoiceViewLayout.addView(adChoiceView);
                            adchoiceViewLayout.setVisibility(View.VISIBLE);
                        }
                    } else if (nativeAd.getNativeAdPartner() != null &&
                            nativeAd.getNativeAdPartner().equals(Constants.AdPartner.VMAX_ADMOB)) {
                        /** To Download App install Icon in case of Admob Ad*/
                        adchoiceViewLayout.removeAllViews();
                        if (nativeAd.getAdChoiceView() != null) {
                            if (nativeAd.getAdChoiceView() instanceof NativeAppInstallAdView) {
                                NativeAppInstallAdView nativeAppInstallAdView = (NativeAppInstallAdView) nativeAd.getAdChoiceView();
                                adchoiceViewLayout.addView(nativeAppInstallAdView);
                            } else if (nativeAd.getAdChoiceView() instanceof NativeContentAdView) {
                                NativeContentAdView nativeContentAdView = (NativeContentAdView) nativeAd.getAdChoiceView();
                                adchoiceViewLayout.addView(nativeContentAdView);
                            }
                            adchoiceViewLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        adchoiceViewLayout.removeAllViews();
                        adchoiceViewLayout.setVisibility(View.GONE);
                    }

                    /** To register Clicks*/
                    nativeAd.registerViewForInteraction(adView, fl_native, fl_native, listview);
                    adContainer=fl_native;
                    MainActivity.this.adView=adView;
                    InitializeListView();

                }
            }

            @Override
            public void onAdDismissed(VmaxAdView adView) {}

            @Override
            public void onAdEnd(boolean isVideoCompleted, long reward) {}

        });

        vmaxAdView.cacheAd();
    }




    /** Handle vmaxAdView object for Activity Lifecycle changes */

    @Override
    protected void onPause() {
        if (vmaxAdView != null) {
            /** To Pause Refresh Of The Ad While Activity Isn't in Foreground */
            vmaxAdView.onPause();
        }

        super.onPause();
    }
    @Override
    protected void onResume() {
        if (vmaxAdView != null) {
            /** To Resume Refresh Of The Ad While Activity Comes Back To Foreground */

            vmaxAdView.onResume();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        if (vmaxAdView != null) {
       /** To Destroy vmaxAdView when Activity Is No Longer Available  */
            vmaxAdView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (vmaxAdView != null) {

            /** To Reconfigure vmaxAdview According To Configuration Changes*/
            vmaxAdView.onConfigurationChanged();
        }

    }

}
