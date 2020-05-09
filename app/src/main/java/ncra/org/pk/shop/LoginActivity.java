package ncra.org.pk.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    private EditText phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpUI();
    }

    private void setUpUI() {
        phoneText = findViewById(R.id.phoneText);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneText);
        ccp.setHintExampleNumberEnabled(true);
    }

    public void startNextActivity(View view) {

        if (ccp.isValidFullNumber()) {
            Intent intent = new Intent(this, VerificationActivity.class);
            intent.putExtra("Number", ccp.getFullNumberWithPlus());
            startActivity(intent);
            finish();
        } else
            Toast.makeText(this, "Enter Valid Phone number", Toast.LENGTH_LONG).show();
    }
}
