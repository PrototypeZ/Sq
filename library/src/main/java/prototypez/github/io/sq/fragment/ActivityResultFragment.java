package prototypez.github.io.sq.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;


import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import prototypez.github.io.sq.ActivityResult;

/**
 * Created by zhounl on 2017/6/19.
 */

public class ActivityResultFragment extends Fragment {

    private final BehaviorSubject<ActivityResult> mActivityResultSubject = BehaviorSubject.create();


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mActivityResultSubject.onNext(new ActivityResult(requestCode, resultCode, data));
    }

    // region getActivityResultObservable

    public static Observable<ActivityResult> getActivityResultObservable(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        ActivityResultFragment fragment = (ActivityResultFragment) fragmentManager.findFragmentByTag(
                ActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, ActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        return fragment.mActivityResultSubject;
    }

    public static Observable<ActivityResult> getActivityResultObservable(Fragment f) {
         FragmentManager fragmentManager = f.getChildFragmentManager();
        ActivityResultFragment fragment = (ActivityResultFragment) fragmentManager.findFragmentByTag(
                ActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, ActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        return fragment.mActivityResultSubject;
    }

    // endregion

    // region insertActivityResult

    public static void insertActivityResult(Activity activity, ActivityResult activityResult) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        ActivityResultFragment fragment= (ActivityResultFragment) fragmentManager.findFragmentByTag(
                ActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, ActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.mActivityResultSubject.onNext(activityResult);
    }

    public static void insertActivityResult(Fragment f, ActivityResult activityResult) {
        FragmentManager fragmentManager = f.getChildFragmentManager();
        ActivityResultFragment fragment = (ActivityResultFragment) fragmentManager.findFragmentByTag(
                ActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, ActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.mActivityResultSubject.onNext(activityResult);
    }

    // endregion

    // region startActivityForResult

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        ActivityResultFragment fragment = (ActivityResultFragment) fragmentManager.findFragmentByTag(
                ActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, ActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment f, Intent intent, int requestCode) {
        FragmentManager fragmentManager = f.getChildFragmentManager();
        ActivityResultFragment fragment = (ActivityResultFragment) fragmentManager.findFragmentByTag(
                ActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, ActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    // endregion
}
