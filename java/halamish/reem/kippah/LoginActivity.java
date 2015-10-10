package halamish.reem.kippah;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private final static int TIME_IN_SCREEN_MS = 800;
    private TextView tv_greet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        makeGreetingInvisible();

        final Context context = this;
        try {
            String uniqueID = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            ParseUser.logInInBackground(uniqueID, uniqueID, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        String username = user.getString("name");
                        if (username != null) {
                            greet_username(username);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent(context, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }, TIME_IN_SCREEN_MS);
                        }

                    } else {
                        startActivity(new Intent(context, SignUpActivity.class));
                        finish();
                    }
                }
            });

        }   catch (Exception e) {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        }
    }

    private void greet_username(String username) {
        if (Build.VERSION.SDK_INT < 11) {
            tv_greet.clearAnimation();
        } else {
            tv_greet.setAlpha(1f);
        }
        tv_greet.setText("הי " + username + "!");
    }

    private void makeGreetingInvisible() {
        float alpha = 0f;
        tv_greet = (TextView) findViewById(R.id.tv_login_greet_the_user);
        if (Build.VERSION.SDK_INT < 11)
        {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            tv_greet.startAnimation(animation);
        }
        else tv_greet.setAlpha(alpha);
    }
}

