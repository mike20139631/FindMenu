package work.mikehuang.findmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ListView listV;
    List<Movie> movie_list = new ArrayList<>();
    private MyAdapter adapter;
    private LayoutInflater myInflater;
    private List<Movie> movies;

    private View ll_find_food;
    private View ll_find_drink;
    private View ll_find_any;
    private View ll_find_location;
    private ArrayList<db_restaurant> list_rest = new ArrayList<>();
    private HashMap<String,String> hashMap_tags = new HashMap<>();

    LinearLayout ll_list;
    private TextView tv_01;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myInflater = LayoutInflater.from(this);
        ll_list = findViewById(R.id.ll_list);
        tv_01 = findViewById(R.id.tv_01);
//        listV= findViewById(R.id.ListView01);


        movie_list.add(new Movie(1,"HBO電影台",""));
        movie_list.add(new Movie(0,"綠光戰警","7:00"));
        movie_list.add(new Movie(2,"鋼鐵人","9:00"));
        movie_list.add(new Movie(0,"蝙蝠俠:開戰時刻","11:00"));

        movie_list.add(new Movie(1,"衛視電影台",""));
        movie_list.add(new Movie(0,"海角七號","7:00"));
        movie_list.add(new Movie(2,"陣頭","9:00"));
        movie_list.add(new Movie(0,"星空","11:00"));

        adapter = new MyAdapter(this,movie_list);
//        listV.setAdapter(adapter);


        getTag();
        getRestaurant();

        //繫結廣告帳號(做一次即可)
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, getString(R.string.ads_id));
        AdView mAdView = findViewById(R.id.adView);
        //繫結廣告(正式)
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        //繫結廣告(測試):
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("CECFA7172E8CC753EB42448496DAD1B4")  // An example device ID (可從 logcat 得知)
                .build();
        mAdView.loadAd(request);
        Log.d(TAG,"is test device: "+request.isTestDevice(this));
    }

    private void getTag(){
        hashMap_tags.clear();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
        DatabaseReference myRef = database.getReference("tag");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    hashMap_tags.put(ds.getKey(),(String) ds.getValue());
                    //Log.d(TAG, "id is: " + ds.getKey() + ", Value is: " + ds.getValue());
                    //adapter.add(ds.child("name").getValue().toString());
                }
//                String ss = dataSnapshot.getValue(Integer.TYPE).toString();
//                Log.d(TAG, "Value is: " + ss);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void getRestaurant(){
        list_rest.clear();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
        DatabaseReference myRef = database.getReference("restaurant");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    db_restaurant new_rest = new db_restaurant((String) ds.child("menu").getValue(),
                            (String) ds.child("name").getValue(),
                            (String) ds.child("tag").getValue(),
                            (String) ds.child("uid").getValue(),
                            (String) ds.child("updatetime").getValue(),
                            (String) ds.child("url").getValue());
                    Log.d(TAG, "id is: " + ds.getKey() + ", Value is: " + ds.getValue());
                    //work.mikehuang.findmenu.db_restaurant new_rest = ds.getValue(work.mikehuang.findmenu.db_restaurant.class);
                    //Log.d(TAG, "id is: " + ds.getKey() + ", Value is: " + ds.getValue());
                    list_rest.add(new_rest);
                    //adapter.add(ds.child("name").getValue().toString());
                }
//                String ss = dataSnapshot.getValue(Integer.TYPE).toString();
//                Log.d(TAG, "Value is: " + ss);

                if (!list_rest.isEmpty()){
                    //todo 改用recycleview and callable方式
                    Log.d(TAG,"list size:"+list_rest.size());
                    TextView title;
                    View view;
                    for (db_restaurant rest : list_rest) {
                        view = myInflater.inflate(R.layout.list_item, null);
                        title = view.findViewById(R.id.title);
                        title.setText(rest.getName());
                        ll_list.addView(view);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public class db_restaurant {
        private String name;
        private String uid;
        private String tag;
        private String menu;
        private String url;
        private String updatetime;
        private ArrayList<String> list_tag;

        public db_restaurant(){}
        public db_restaurant(String menu,String name,String tag,String uid,String updatetime,String url){
            this.menu = menu;
            this.name = name;
            this.tag = tag;
            this.uid = uid;
            this.updatetime = updatetime;
            this.url = url;
        }

        public void setMenu(String menu){
            this.menu = menu;
        }
        public void setName(String name){
            this.name = name;
        }
        public void setTag(String tag){
            this.tag = tag;

        }
        public void setUid(String uid){
            this.uid = uid;

        }
        public void setUpdatetime(String updatetime){
            this.updatetime = updatetime;
        }
        public void setUrl(String url){
            this.url = url;
        }

        @PropertyName("menu")
        public String getMenu() {
            return menu;
        }
        @PropertyName("name")
        public String getName() {
            return name;
        }
        @PropertyName("tag")
        public String getTag() {
            return tag;
        }
        @PropertyName("uid")
        public String getUid() {
            return uid;
        }
        @PropertyName("updatetime")
        public String getUpdatetime() {
            return updatetime;
        }
        @PropertyName("url")
        public String getUrl() {
            return url;
        }
        public ArrayList<String> getList_tag() {
            return Utils.stringSplit2Array(getTag(),",");
        }
    }

    public class MyAdapter extends BaseAdapter {

        public MyAdapter(Context context, List<Movie> movie){

//            this.movies = movie;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

}

