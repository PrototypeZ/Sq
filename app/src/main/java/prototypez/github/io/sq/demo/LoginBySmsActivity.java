package prototypez.github.io.sq.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jyn.vcview.VerificationCodeView;

public class LoginBySmsActivity extends AppCompatActivity {

    VerificationCodeView mVerificationCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_sms);

        mVerificationCodeView = findViewById(R.id.code);

        mVerificationCodeView.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {
                
            }
        });
    }
}
