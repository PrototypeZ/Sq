package prototypez.github.io.sq;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import prototypez.github.io.sq.fragment.ActivityResultFragment;
import prototypez.github.io.sq.fragment.SupportActivityResultFragment;

public class Sq {

    // region setResult
    // 无论从 Fragment 还是从 Activity 启动 startActivityForResult, 都只能在 Activity 内部 setResult

    public static void setResult(AppCompatActivity activity, int resultCode) {
        setResult(activity, resultCode, null);
    }

    public static void setResult(Activity activity, int resultCode) {
        setResult(activity, resultCode, null);
    }

    public static void setResult(AppCompatActivity activity, int resultCode, Intent resultData) {

        if (resultData == null) {
            resultData = new Intent();
        }

        Bundle requestData = activity.getIntent().getExtras();

        if (requestData != null) {
            resultData.putExtra(SqConstant.KEY_REQUEST_DATA, requestData);
        }

        activity.setResult(resultCode, resultData);
    }

    public static void setResult(Activity activity, int resultCode, Intent resultData) {

        if (resultData == null) {
            resultData = new Intent();
        }

        Bundle requestData = activity.getIntent().getExtras();

        if (requestData != null) {
            resultData.putExtra(SqConstant.KEY_REQUEST_DATA, requestData);
        }

        activity.setResult(resultCode, resultData);
    }

    // endregion

    // region obtainActivityResult

    public synchronized static Observable<ActivityResult> obtainActivityResult(AppCompatActivity activity) {
        return SupportActivityResultFragment.getActivityResultObservable(activity);
    }

    public synchronized static Observable<ActivityResult> obtainActivityResult(android.support.v4.app.Fragment v4Fragment) {
        return SupportActivityResultFragment.getActivityResultObservable(v4Fragment);
    }


    public synchronized static Observable<ActivityResult> obtainActivityResult(Activity activity) {
        return ActivityResultFragment.getActivityResultObservable(activity);
    }

    public synchronized static Observable<ActivityResult> obtainActivityResult(Fragment fragment) {
        return ActivityResultFragment.getActivityResultObservable(fragment);
    }

    // endregion

    // region insertActivityResult
    public synchronized static void insertActivityResult(AppCompatActivity activity, ActivityResult activityResult) {
        SupportActivityResultFragment.insertActivityResult(activity, activityResult);
    }

    public synchronized static void insertActivityResult(android.support.v4.app.Fragment v4Fragment, ActivityResult activityResult) {
        SupportActivityResultFragment.insertActivityResult(v4Fragment, activityResult);
    }

    public synchronized static void insertActivityResult(Activity activity, ActivityResult activityResult) {
        ActivityResultFragment.insertActivityResult(activity, activityResult);
    }

    public synchronized static void insertActivityResult(Fragment fragment, ActivityResult activityResult) {
        ActivityResultFragment.insertActivityResult(fragment, activityResult);
    }
    // endregion

    // region startActivityForResult

    public static void startActivityForResult(AppCompatActivity activity, Intent intent, int requestCode) {
        startActivityForResult(activity, intent, requestCode, null);
    }

    public static void startActivityForResult(android.support.v4.app.Fragment v4Fragment, Intent intent, int requestCode) {
        startActivityForResult(v4Fragment, intent, requestCode, null);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        startActivityForResult(activity, intent, requestCode, null);
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode) {
        startActivityForResult(fragment, intent, requestCode, null);
    }

    public static void startActivityForResult(AppCompatActivity activity, Intent intent, int requestCode, Bundle requestContextData) {
        if (requestContextData != null && requestContextData.size() > 0) {
            intent.putExtra(SqConstant.KEY_REQUEST_CONTEXT_DATA, requestContextData);
        }
        SupportActivityResultFragment.startActivityForResult(activity, intent, requestCode);
    }

    public static void startActivityForResult(android.support.v4.app.Fragment v4Fragment, Intent intent, int requestCode, Bundle requestContextData) {
        if (requestContextData != null && requestContextData.size() > 0) {
            intent.putExtra(SqConstant.KEY_REQUEST_CONTEXT_DATA, requestContextData);
        }
        SupportActivityResultFragment.startActivityForResult(v4Fragment, intent, requestCode);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle requestContextData) {
        if (requestContextData != null && requestContextData.size() > 0) {
            intent.putExtra(SqConstant.KEY_REQUEST_CONTEXT_DATA, requestContextData);
        }
        ActivityResultFragment.startActivityForResult(activity, intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, Bundle requestContextData) {
        if (requestContextData != null && requestContextData.size() > 0) {
            intent.putExtra(SqConstant.KEY_REQUEST_CONTEXT_DATA, requestContextData);
        }
        ActivityResultFragment.startActivityForResult(fragment, intent, requestCode);
    }

    // endregion
}
