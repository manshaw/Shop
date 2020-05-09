package ncra.org.pk.shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String PACAKGE_NAME = "ncra.org.pk.shop";
    SharedPreferences prefs = null;
    private AlphaAnimation alphaUp;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences(PACAKGE_NAME, MODE_PRIVATE);
        logo = findViewById(R.id.logo);
        alphaUp = new AlphaAnimation(0.0f, 1.0f);
        alphaUp.setDuration(2500);
        logo.startAnimation(alphaUp);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (!prefs.getBoolean("SignedIn", false)) {
                            startLoginActivity();
                        } else {
                            startMainActivity();
                        }
                    }
                }, 3000);
    }

    private void startMainActivity() {
        Intent nextActivity;
        nextActivity = new Intent(SplashActivity.this, MainActivity.class);
        nextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(nextActivity);
        finish();
    }

    private void startLoginActivity() {
        Intent nextActivity;
        nextActivity = new Intent(SplashActivity.this, LoginActivity.class);
        nextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(nextActivity);
        finish();
    }
}

