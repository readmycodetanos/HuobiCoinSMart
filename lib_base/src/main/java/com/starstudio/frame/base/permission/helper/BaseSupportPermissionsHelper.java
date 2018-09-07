package com.starstudio.frame.base.permission.helper;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.starstudio.frame.base.permission.RationaleDialogFragmentCompat;

/**
 * Implementation of {@link PermissionHelper} for Support Library host classes.
 */
public abstract class BaseSupportPermissionsHelper<T> extends PermissionHelper<T> {

    public BaseSupportPermissionsHelper(@NonNull T host) {
        super(host);
    }

    public abstract FragmentManager getSupportFragmentManager();

    @Override
    public void showRequestPermissionRationale(@NonNull String rationale,
                                               int positiveButton,
                                               int negativeButton,
                                               int requestCode,
                                               @NonNull String... perms) {
        RationaleDialogFragmentCompat
                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms)
                .showAllowingStateLoss(getSupportFragmentManager(), RationaleDialogFragmentCompat.TAG);
    }
}
