package prototypez.github.io.sq.demo;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import prototypez.github.io.sq.activity.SqActivity;
import prototypez.github.io.sq.demo.entity.User;
import prototypez.github.io.sq.demo.fragment.LoginByPwdFragment;
import prototypez.github.io.sq.demo.fragment.LoginBySmsFragment;

/**
 * 登陆流程 Interface
 */
public class LoginActivity extends SqActivity {

    LoginByPwdFragment loginByPwdFragment;
    LoginBySmsFragment loginBySmsFragment;

    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginByPwdFragment = findOrCreateFragment(LoginByPwdFragment.class);
        loginBySmsFragment = findOrCreateFragment(LoginBySmsFragment.class);

        if (savedInstanceState == null) {
            push(loginByPwdFragment);
        } else {
            mUser = (User) savedInstanceState.getSerializable("user");
        }

        // Flow control logic

        loginByPwdFragment.setLoginCallback(new LoginByPwdFragment.LoginByPwdCallback() {
            @Override
            public void onLoginOk(boolean needSmsVerify, User user) {
                Toast
                        .makeText(
                                LoginActivity.this,
                                user.nickName + ", 检测到异地登录，为保证您的安全，请先验证短信验证码",
                                Toast.LENGTH_LONG)
                        .show();


                loginBySmsFragment.setParams(user);
                push(loginBySmsFragment);
            }
        });

        loginBySmsFragment.setLoginBySmsCallback(new LoginBySmsFragment.LoginBySmsCallback() {
            @Override
            public void onLoginOk(User user) {
                Toast
                        .makeText(
                                LoginActivity.this,
                                user.nickName + " 登录成功！",
                                Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", mUser);
    }
}


