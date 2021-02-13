package com.example.bookah.AppUtilities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookah.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class AppUtility extends Application {

    public static void ShowToast(Context context, String message, View toastView, Integer results)
    {
        TextView tvMessage = toastView.findViewById(R.id.tvMessage);
        ImageView ivToast = toastView.findViewById(R.id.ivToast);
        tvMessage.setText(message);

        if(results == 1)
        {
            ivToast.setImageResource(R.drawable.ic_success);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setView(toastView);
            toast.show();
        }
        else if (results == 2)
        {
            ivToast.setImageResource(R.drawable.ic_error);
            tvMessage.setTextColor(Color.YELLOW);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setView(toastView);
            toast.show();
        }
        else if (results == 3)
        {
            ivToast.setImageResource(R.drawable.ic_info);
            tvMessage.setTextColor(Color.BLUE);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setView(toastView);
            toast.show();
        }
    }

    public static boolean isValidCellphone(CharSequence phone)
    {
        return (!TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches());
    }

    public static boolean isValidEmail(CharSequence email)
    {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidIDNumber(CharSequence idNumber)
    {
        Pattern pattern;

        final String ID_PATTERN = "(?<Year>[0-9][0-9])(?<Month>([0][1-9])|([1][0-2]))(?<Day>([0-2][0-9])|([3][0-1]))(?<Gender>[0-9])(?<Series>[0-9]{3})(?<Citizenship>[0-9])(?<Uniform>[0-9])(?<Control>[0-9])";

        pattern = Pattern.compile(ID_PATTERN);

        return (!TextUtils.isEmpty(idNumber) && idNumber.length()==13 && pattern.matcher(idNumber).matches());
    }

    public static boolean isValidPassword(CharSequence password)
    {
        Pattern pattern;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);

        return (!TextUtils.isEmpty(password) && pattern.matcher(password).matches());
    }

    public static String getInputText(TextInputEditText inputText)
    {
        return inputText.getText().toString().trim();
    }

    public static boolean validateInput(TextInputLayout[] inputLayouts, String[] inputErrors, TextInputEditText... inputTexts)
    {
        int errorCount = 0;

        for (int i = 0; i < inputTexts.length; ++i)
        {
            if(getInputText(inputTexts[i]).isEmpty())
            {
                inputLayouts[i].setError(inputErrors[i]);

                ++errorCount;
            }
            else
            {
                inputLayouts[i].setError(null);
            }
        }

        return errorCount == 0;
    }

}

