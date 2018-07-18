package prototypez.github.io.sq.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import prototypez.github.io.sq.demo.R;
import prototypez.github.io.sq.demo.entity.User;

public class LoginByPwdFragment extends Fragment {


    // UI references.
    private EditText mPhoneView;
    private EditText mPasswordView;

    LoginByPwdCallback mCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_by_pwd, container, false);
        // Set up the login form.
        mPhoneView = view.findViewById(R.id.phone);

        mPasswordView = view.findViewById(R.id.password);

        Button signInButton = view.findViewById(R.id.sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mPhoneView.getText().toString();
                String pwd = mPasswordView.getText().toString();

                if (mCallback != null) {
                    // mock login ok
                    mCallback.onLoginOk(true, new User(
                            UUID.randomUUID().toString(),
                            "Jack",
                            mPhoneView.getText().toString()
                    ));
                }
            }
        });

        return view;
    }

    public void setLoginCallback(LoginByPwdCallback callback) {
        this.mCallback = callback;
    }


    public interface LoginByPwdCallback {

        void onLoginOk(boolean needSmsVerify, User user);

    }
}
