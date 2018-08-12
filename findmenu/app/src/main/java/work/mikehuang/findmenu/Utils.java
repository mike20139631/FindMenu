package work.mikehuang.findmenu;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    public static ArrayList<String> stringSplit2Array(String data,String split_char){
        data = data.trim();
        String[] separated = data.split(split_char);

//        StringTokenizer tokens = new StringTokenizer(currentString, ":");
//        String first = tokens.nextToken();// this will contain "Fruit"
//        String second = tokens.nextToken();// this will contain " they taste good"
        return new ArrayList<>(Arrays.asList(separated));
    }

    public static void goHome(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void goInitial(Context context) {
        //todo change class name
        context.startActivity(new Intent(context, MainActivity.class));
    }

    //產生google廣告
    public static void bindGoogleAds(AdView mAdView) {
        AdRequest adRequest;
        if (MachineInfo.is_test){//(測試)
            //繫結廣告(測試):
            adRequest = new AdRequest.Builder()
                    .addTestDevice("CECFA7172E8CC753EB42448496DAD1B4")  // An example device ID (可從 logcat 得知)
                    .build();
        }
        else{//(正式)
            //繫結廣告(正式)
            adRequest = new AdRequest.Builder().build();
        }
        mAdView.loadAd(adRequest);
        //Log.d(TAG,"is test device: "+adRequest.isTestDevice(this));
    }

    public static String getBackName(int index){
        switch (index){
            default:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return String.valueOf(R.string.go_map);
        }
    }

    public static boolean validCellPhone(String number)
    {
        return PhoneNumberUtils.isGlobalPhoneNumber("+912012185234");
        //return android.util.Patterns.PHONE.matcher(number).matches();
    }
}
