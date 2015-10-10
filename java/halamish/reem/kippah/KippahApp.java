package halamish.reem.kippah;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by Re'em on 10/2/2015.
 */
public class KippahApp extends Application {
    private static final String
            APP_ID = "6Em9gIAajMoPPLsXRQWVHDOr1o94m7XfqNYhKAlZ",
            CLIENT_KEY = "oxawBREAO6ClE0grTVpriLjPFOU2jcohMBjXhrBq";
    private static final String TAG = "application";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "created!");
        Parse.initialize(this, APP_ID, CLIENT_KEY);
    }
}
