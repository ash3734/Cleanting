package
        com.example.ash.cleantind_dgu_cse_se;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by ash on 2016-11-16.
 */

public class User extends Activity{
    public static String ID;
    public static String baseURL = "http://192.168.43.100/";

    public static void clearID(){
       ID="";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
