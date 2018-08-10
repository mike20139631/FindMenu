package work.mikehuang.findmenu;

import android.content.Context;
import android.util.DisplayMetrics;

public class UnitUtil {

        public static DisplayMetrics getDisplayMetrics(Context context){
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm;
        }

        public static float dp2px(Context context, float dpValue){
            return Math.round(dpValue * getDisplayMetrics(context).density);
        }

        public static float px2dp(Context context, float pxValue){
            return Math.round(pxValue / getDisplayMetrics(context).density);
        }

        public static float sp2px(Context context, float pxValue){
            return Math.round(pxValue * getDisplayMetrics(context).scaledDensity);
        }

        public static float px2sp(Context context, float pxValue){
            return Math.round(pxValue / getDisplayMetrics(context).scaledDensity);
        }

        public static int getScreenWidthPx(Context context){
            return getDisplayMetrics(context).widthPixels;
        }

        public static int getScreenHeightPx(Context context){
            return getDisplayMetrics(context).heightPixels;
        }


}
