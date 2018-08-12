package work.mikehuang.findmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private TextView tv_title;
    private TextView tv_res_name;
    private TextView tv_address;
    private TextView tv_phone_number;

    private ImageView btn_back;
    private ImageView iv_favorite_true;
    private ImageView iv_favorite_false;

    private ImageView iv_star_01;
    private ImageView iv_star_02;
    private ImageView iv_star_03;
    private ImageView iv_star_04;
    private ImageView iv_star_05;
    private ImageView iv_star_06;

    private RelativeLayout btn_favorite;
    private LinearLayout ll_map;
    private LinearLayout ll_phone;
    private LinearLayout ll_star;

    private WebView myWebView;

    private float star;//star(0-6)
    private String uid;
    private String url;//url
    private String res_name;//restaurant name
    private String address;//map address
    private String phone;//phone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int index = 0;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                //go home
                Utils.goHome(this);
//                newString= null;
            } else {
                index = extras.getInt("index");
            }
        } else {
//            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
            index = savedInstanceState.getInt("index");
        }

        //init views
        initView();

        //getData
        star = 0.6f;
        uid = MachineData.list_rest.get(index).getUid();
        url = MachineData.list_rest.get(index).getUrl();
        res_name = MachineData.list_rest.get(index).getName();
        address = MachineData.list_rest.get(index).getName();
        phone = "0932123456";//MachineData.list_rest.get(index).getName();

        //set Data to TextView
        //tv_title.setText(back_name);
        tv_res_name.setText(res_name);
        tv_address.setText(address);
        tv_phone_number.setText(phone);

        //set webView
        setWebView();

        //set stars
        setStars();

        //setOnClick event
        setOnClick();

        //google Ads
        AdView mAdView = findViewById(R.id.adView);
        Utils.bindGoogleAds(mAdView);


    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_res_name = findViewById(R.id.tv_res_name);
        tv_address = findViewById(R.id.tv_address);
        tv_phone_number = findViewById(R.id.tv_phone_number);

        btn_back = findViewById(R.id.btn_back);
        iv_favorite_true = findViewById(R.id.iv_favorite_true);
        iv_favorite_false = findViewById(R.id.iv_favorite_false);

        iv_star_01 = findViewById(R.id.iv_star_01);
        iv_star_02 = findViewById(R.id.iv_star_02);
        iv_star_03 = findViewById(R.id.iv_star_03);
        iv_star_04 = findViewById(R.id.iv_star_04);
        iv_star_05 = findViewById(R.id.iv_star_05);
        iv_star_06 = findViewById(R.id.iv_star_06);

        btn_favorite = findViewById(R.id.btn_favorite);
        ll_map = findViewById(R.id.ll_map);
        ll_phone = findViewById(R.id.ll_phone);
        ll_star = findViewById(R.id.ll_star);

        myWebView = findViewById(R.id.wv_test);
    }

    private void setStars() {
        final int max = 6;
        ArrayList<ImageView> iv_list = new ArrayList<>();
        iv_list.add(iv_star_01);
        iv_list.add(iv_star_02);
        iv_list.add(iv_star_03);
        iv_list.add(iv_star_04);
        iv_list.add(iv_star_05);
        iv_list.add(iv_star_06);

        //test
//        for (int j = 0; j <= 6;j++) {
//            star = j;
//
//
//            try {
//                Thread.sleep(1500);
//
//            }catch (InterruptedException ex){
//                ex.printStackTrace();
//            }
//        }

        float fraction = star % 1;//小數
        int c = (int) star;//整數
        for (int i = 0; i < max; i++) {
            if (i < c)
                iv_list.get(i).setImageDrawable(getDrawable(R.drawable.icon_star_red_full));
            else if (fraction > 0 && i == c)
                iv_list.get(i).setImageDrawable(getDrawable(R.drawable.icon_star_red_half));
            else
                iv_list.get(i).setImageDrawable(getDrawable(R.drawable.icon_star_red_emp));
        }

    }

    private void setOnClick() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iv_favorite_true.isShown()) {
                    iv_favorite_true.setVisibility(View.GONE);
                    iv_favorite_false.setVisibility(View.VISIBLE);
                } else {
                    iv_favorite_true.setVisibility(View.VISIBLE);
                    iv_favorite_false.setVisibility(View.GONE);
                }
//                btn_favorite.requestLayout();
//                btn_favorite.invalidate();
            }
        });
        ll_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindGoogleMap();
            }
        });
        ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup call out y/n?
                AlertDialog.Builder builder;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(DetailActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);//Theme_Material_Dialog_Alert
                } else {
                    builder = new AlertDialog.Builder(DetailActivity.this);
                }
                builder.setTitle("打電話")
                        .setMessage("您是否確定要撥出電話 " + phone + " ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with phone call
                                bindPhoneCall(phone);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public static final int REQUEST_PHONE_CALL = 900;

    private void bindPhoneCall(String phoneNum) {//phoneNum 10 digits / 13 digits
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android 6(API LEVEL 23) 以上才需要確認權限
            //確認是否有打電話的權限

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.CALL_PHONE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    //todo popup explanation dialog
                    AlertDialog.Builder builder;
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(DetailActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(DetailActivity.this);
                    }
                    builder.setTitle("給予權限")
                            .setMessage("請同意給予[撥號]權限，以便直接撥電話給店家\n\nFindMenu不會濫用權限，請放心！")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with 要求權限
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    //這邊的request permission 會跑去 onRequestPermissionsResult
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            REQUEST_PHONE_CALL);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {//make phone call
                // Permission has already been granted
                makePhoneCall("0932721726");//phoneNum

            }
        } else {
            makePhoneCall("037721726");//phoneNum
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    makePhoneCall(phone);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void makePhoneCall(String phoneNum) {
        if (Utils.validCellPhone(phoneNum)) {
//            try {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
//                startActivity(intent);
//            } catch (SecurityException ex) {
//                ex.printStackTrace();
//            }

            //ACTION_DIAL
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        } else {
            //popup number is not valid.
            AlertDialog.Builder builder;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(DetailActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(DetailActivity.this);
            }
            builder.setMessage("抱歉，電話號碼格式不正確。(請回報APP或自行撥打)")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }

    private void bindGoogleMap() {
        //todo bind google map

    }

    private void setWebView() {
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 設置可以支持縮放
        webSettings.setSupportZoom(true);
        // 設置出線縮放工具
        webSettings.setBuiltInZoomControls(true);
        // 設置可在大視野範圍內上下左右拖動，並且可以任意比例縮放
        webSettings.setUseWideViewPort(true);
        // 設置默認加載的可視範圍是大視野範圍
        webSettings.setLoadWithOverviewMode(true);

        myWebView.loadUrl(url);//url "https://www.example.com"
        myWebView.setWebViewClient(new MyWebViewClient());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;//不管怎樣都在這個WebView loading
//            if (Uri.parse(url).getHost().equals("https://www.example.com")) {
//                // This is my website, so do not override; let my WebView load the page
//                return false;
//            }
//            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}

