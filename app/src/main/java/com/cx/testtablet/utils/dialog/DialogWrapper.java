package com.cx.testtablet.utils.dialog;

/**
 * Created by Max on 2018-12-25 15:18.
 */
public class DialogWrapper {
    private Dialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(Dialog.Builder dialog) {
        this.dialog = dialog;
    }

    public Dialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(Dialog.Builder dialog) {
        this.dialog = dialog;
    }
}
