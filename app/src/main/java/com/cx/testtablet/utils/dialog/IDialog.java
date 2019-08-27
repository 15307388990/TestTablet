package com.cx.testtablet.utils.dialog;

import android.view.View;

/**
 * Created by Max on 2018-12-25 15:17.
 */
public interface IDialog {
    void dismiss();

    interface OnBuildListener {
        void onBuildChildView(IDialog dialog, View view, int layoutRes);
    }

    interface OnClickListener {
        void onClick(IDialog dialog);
    }

    interface OnDismissListener {
        /**
         * This method will be invoked when the dialog is dismissed.
         *
         * @param dialog the dialog that was dismissed will be passed into the
         *               method
         */
        void onDismiss(IDialog dialog);
    }
}
