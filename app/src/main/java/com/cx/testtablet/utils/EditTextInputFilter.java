package com.cx.testtablet.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextInputFilter implements InputFilter {
    Pattern mPattern = Pattern.compile("[^a-zA-Z0-9]");
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }

        Matcher matcher = mPattern.matcher(source);
        if (matcher.matches()) {
            return "";
        }

        return null;
    }
}
