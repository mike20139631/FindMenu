package work.mikehuang.findmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

public class DetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private TextView tv_back;
    private TextView tv_res_name;
    private TextView tv_address;
    private TextView tv_phone_number;

    private ImageView btn_back;
    private ImageView iv_favorite_true;
    private ImageView iv_favorite_false;

    private RelativeLayout btn_favorite;
    private LinearLayout ll_map;
    private LinearLayout ll_phone;
    private LinearLayout ll_star;

    private WebView wv_test;

    private float star;//star(0-6)
    private String uid;
    private String url;//url
    private String back_name;//parent activity name
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
        star = 6f;
        back_name = "餐廳列表";
        uid = MachineData.list_rest.get(index).getUid();
        url = MachineData.list_rest.get(index).getUrl();
        res_name = MachineData.list_rest.get(index).getName();
        address = MachineData.list_rest.get(index).getName();
        phone = MachineData.list_rest.get(index).getName();

        //set Data to TextView
        tv_back.setText(back_name);
        tv_res_name.setText(res_name);
        tv_address.setText(address);
        tv_phone_number.setText(phone);

        //google Ads
        AdView mAdView = findViewById(R.id.adView);
        Utils.bindGoogleAds(mAdView);
    }

    private void initView(){
        tv_back = findViewById(R.id.tv_back);
        tv_res_name = findViewById(R.id.tv_res_name);
        tv_address = findViewById(R.id.tv_address);
        tv_phone_number = findViewById(R.id.tv_phone_number);

        btn_back = findViewById(R.id.btn_back);
        iv_favorite_true = findViewById(R.id.iv_favorite_true);
        iv_favorite_false = findViewById(R.id.iv_favorite_false);

        btn_favorite = findViewById(R.id.btn_favorite);
        ll_map = findViewById(R.id.ll_map);
        ll_phone = findViewById(R.id.ll_phone);
        ll_star = findViewById(R.id.ll_star);

        wv_test = findViewById(R.id.wv_test);
    }

}

