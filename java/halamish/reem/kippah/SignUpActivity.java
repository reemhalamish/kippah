package halamish.reem.kippah;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signIn = (Button) findViewById(R.id.btn_signup_ok);
        final Context context = this;
        final EditText userInput = (EditText) findViewById(R.id.edt_signup_nickname);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userInput.getText().toString();
                if (name == null || name.length() == 0) {
                    userInput.setHint("אפשר להתפרע אם רוצים, כל כינוי שהוא!");
                    return;
                }
                String uniqueID = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                final ParseUser user = new ParseUser();
                user.setUsername(uniqueID);
                user.setPassword(uniqueID);
                user.put("name", name);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) { // no errors!
                            Intent mainIntent = new Intent(context, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                            dlgAlert.setTitle("לא הצלחנו לרשום אותך");
                            dlgAlert.setMessage(e.getMessage());
                            dlgAlert.create().show();
                        }
                    }
                });
            }

        });
    }
}
