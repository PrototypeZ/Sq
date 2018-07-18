package prototypez.github.io.sq.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyn.vcview.VerificationCodeView;

import prototypez.github.io.sq.demo.R;
import prototypez.github.io.sq.demo.entity.User;
import prototypez.github.io.sq.util.BundleBuilder;

public class LoginBySmsFragment extends Fragment {

    VerificationCodeView mVerificationCodeView;

    TextView mHint;

    LoginBySmsCallback mCallback;

    User mUser;

    public void setParams(User user) {
        setArguments(BundleBuilder.newInstance().putSerializable("user", user).build());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = (User) getArguments().getSerializable("user");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_by_sms, container, false);

        mVerificationCodeView = view.findViewById(R.id.code);
        mHint = view.findViewById(R.id.hint);

        mHint.setText(String.format("验证码已发送至: %s", mUser.phone));

        mVerificationCodeView.setOnCodeFinishListener(new VerificationCodeView.OnCodeFinishListener() {
            @Override
            public void onComplete(String content) {
                // mock success
                if (mCallback != null) {
                    mCallback.onLoginOk(new User(
                            mUser.id,
                            mUser.nickName,
                            mUser.phone,
                            "Shanghai",
                            29
                    ));
                }
            }
        });
        return view;

    }

    public void setLoginBySmsCallback(LoginBySmsCallback callback) {
        this.mCallback = callback;
    }


    public interface LoginBySmsCallback {
        void onLoginOk(User user);
    }
}
