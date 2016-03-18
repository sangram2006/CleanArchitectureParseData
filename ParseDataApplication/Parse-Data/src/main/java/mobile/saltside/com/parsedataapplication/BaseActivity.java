package mobile.saltside.com.parsedataapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * Created by sangram.
 */
public class BaseActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;
    private AnimatorSet mAnimSet;
    View mContent;
    private final int ANIMATION_TRANSITION_DURATION = 500;

    public void showLoadingDisplay() {
        mProgressBar.setVisibility(View.VISIBLE);
        mAnimSet = new AnimatorSet();
        mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "alpha", 1.0f, 0.5f));
        mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
        lockOrientation();
        lockTouchInput();
    }

    public void hideLoadingDisplay() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mAnimSet = new AnimatorSet();
        mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "alpha", 0.5f, 1.0f)// ,
        );
        unLockOrientation();
        unlockTouchInput();
        mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
    }

    protected void lockOrientation() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    int rotation = getWindowManager().getDefaultDisplay().getRotation();
                    if (rotation == android.view.Surface.ROTATION_90 || rotation == android.view.Surface.ROTATION_180) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    int rotation = getWindowManager().getDefaultDisplay().getRotation();
                    if (rotation == android.view.Surface.ROTATION_0 || rotation == android.view.Surface.ROTATION_90) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    }
                }
                break;
        }
    }

    /**
     * Suggested to unlock the orientation of the screen
     */
    protected void unLockOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    protected void lockTouchInput() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    protected void unlockTouchInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Some internal problem, Please Try Again")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // builder.dismiss();//do things
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
}
