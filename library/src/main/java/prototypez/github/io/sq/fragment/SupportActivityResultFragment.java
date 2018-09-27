package prototypez.github.io.sq.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import prototypez.github.io.sq.ActivityResult;

/**
 * Encapsulate startActivityForResult and onActivityResult with Fragment
 * <p>
 * Created by zhounl on 2017/6/19.
 */

public class SupportActivityResultFragment extends Fragment {

    private final PublishSubject<ActivityResult> mActivityResultSubject = PublishSubject.create();


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mActivityResultSubject.onNext(new ActivityResult(requestCode, resultCode, data));
    }

    // region getActivityResultObservable

    public static Observable<ActivityResult> getActivityResultObservable(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SupportActivityResultFragment fragment = (SupportActivityResultFragment) fragmentManager.findFragmentByTag(
                SupportActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new SupportActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, SupportActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        return fragment.mActivityResultSubject;
    }

    public static Observable<ActivityResult> getActivityResultObservable(Fragment f) {
        FragmentManager fragmentManager = f.getChildFragmentManager();
        SupportActivityResultFragment fragment = (SupportActivityResultFragment) fragmentManager.findFragmentByTag(
                SupportActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new SupportActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, SupportActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        return fragment.mActivityResultSubject;
    }

    // endregion

    // region insertActivityResult

    public static void insertActivityResult(AppCompatActivity activity, ActivityResult activityResult) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SupportActivityResultFragment fragment = (SupportActivityResultFragment) fragmentManager.findFragmentByTag(
                SupportActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new SupportActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, SupportActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.mActivityResultSubject.onNext(activityResult);
    }

    public static void insertActivityResult(Fragment f, ActivityResult activityResult) {
        FragmentManager fragmentManager = f.getChildFragmentManager();
        SupportActivityResultFragment fragment = (SupportActivityResultFragment) fragmentManager.findFragmentByTag(
                SupportActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new SupportActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, SupportActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.mActivityResultSubject.onNext(activityResult);
    }

    // endregion

    // region startActivityForResult

    public static void startActivityForResult(AppCompatActivity activity, Intent intent, int requestCode) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SupportActivityResultFragment fragment = (SupportActivityResultFragment) fragmentManager.findFragmentByTag(
                SupportActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new SupportActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, SupportActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment f, Intent intent, int requestCode) {
        FragmentManager fragmentManager = f.getChildFragmentManager();
        SupportActivityResultFragment fragment = (SupportActivityResultFragment) fragmentManager.findFragmentByTag(
                SupportActivityResultFragment.class.getCanonicalName());
        if (fragment == null) {
            fragment = new SupportActivityResultFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, SupportActivityResultFragment.class.getCanonicalName())
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    // endregion
}
