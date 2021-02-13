package com.example.bookah.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookah.Activities.SplashScreens.VerificationSplash;
import com.example.bookah.AppUtilities.AppUtility;
import com.example.bookah.Model.User;
import com.example.bookah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private View mProgressView;
    private View mRegisterFormView;
    private TextView tvLoad;

    //Variables
    Animation topAnimation;
    ImageView ivLogo;
    TextInputLayout reg_email, reg_firstName, reg_lastName, reg_phoneNumber,reg_id, reg_password,reg_confirmPassword;
    TextInputEditText et_regEmail, et_regFirstName, et_regLastName, et_regPhoneNumber, et_regId, et_regPassword, et_regConfirmPassword;
    Button btnRegister;
    TextView tvAlreadyRegistered;

    //Firebase
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        tvLoad = findViewById(R.id.tvLoad);

        //Hooks
        ivLogo =findViewById(R.id.register_logo);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        tvAlreadyRegistered = findViewById(R.id.tvAlreadyRegistered);
        tvAlreadyRegistered.setOnClickListener(this);

        reg_email = findViewById(R.id.reg_email);
        et_regEmail = findViewById(R.id.et_regEmail);

        reg_firstName = findViewById(R.id.reg_firstName);
        et_regFirstName = findViewById(R.id.et_regFirstName);

        reg_lastName = findViewById(R.id.reg_lastName);
        et_regLastName = findViewById(R.id.et_regLastName);

        reg_phoneNumber = findViewById(R.id.reg_phoneNumber);
        et_regPhoneNumber = findViewById(R.id.et_regPhoneNumber);

        reg_id = findViewById(R.id.reg_id);
        et_regId = findViewById(R.id.et_regId);

        reg_password = findViewById(R.id.reg_password);
        et_regPassword = findViewById(R.id.et_regPassword);

        reg_confirmPassword = findViewById(R.id.reg_confirmPassword);
        et_regConfirmPassword = findViewById(R.id.et_regConfirmPassword);

        //Firebase
        auth = FirebaseAuth.getInstance();

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        ivLogo.startAnimation(topAnimation);

        //Functionality
        et_regFirstName.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
            {
                reg_firstName.setError(null);
            }
        });

        et_regLastName.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
            {
                reg_lastName.setError(null);
            }
        });

        et_regId.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
            {
                reg_id.setError(null);
            }
            else
            {
                if(!AppUtility.getInputText(et_regId).isEmpty())
                {
                    if(!AppUtility.isValidIDNumber(AppUtility.getInputText(et_regId)))
                    {
                        et_regId.setText("");
                        reg_id.setError("Invalid ID Number Format!");
                    }
                }
                else
                {
                    reg_id.setError(getString(R.string.idNumber_error));
                }
            }
        });

        et_regEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
            {
                reg_email.setError(null);
            }
            else
            {
                if(!AppUtility.getInputText(et_regEmail).isEmpty())
                {
                    if(!AppUtility.isValidEmail(AppUtility.getInputText(et_regEmail)))
                    {
                        et_regEmail.setText("");
                        reg_email.setError("Invalid Email Format!");
                    }
                }
                else
                {
                    reg_email.setError(getString(R.string.email_error));
                }
            }
        });

        et_regPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
            {
                reg_password.setError(null);
            }
            else
            {
                if(!AppUtility.getInputText(et_regPassword).isEmpty())
                {
                    if(!AppUtility.isValidPassword(AppUtility.getInputText(et_regPassword)))
                    {
                        et_regPassword.setText("");
                        reg_password.setError("Password Too Simple");
                    }
                }
                else
                {
                    reg_password.setError(getString(R.string.password_error));
                }
            }
        });
        et_regConfirmPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus)
            {
                reg_confirmPassword.setError(null);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAlreadyRegistered:
                startActivity(new Intent(this, Login.class));
                break;

            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    public void registerUser()
    {
        String firstName = AppUtility.getInputText(et_regFirstName);
        String lastName = AppUtility.getInputText(et_regLastName);
        String idNumber = AppUtility.getInputText(et_regId);
        String phoneNumber = AppUtility.getInputText(et_regPhoneNumber);
        String email = AppUtility.getInputText(et_regEmail);
        String password = AppUtility.getInputText(et_regPassword);
        String confirm = AppUtility.getInputText(et_regConfirmPassword);

        //First name validation
        if(firstName.isEmpty())
        {
            et_regFirstName.setText("");
            reg_firstName.setError("Please provide a first name!");
            et_regFirstName.requestFocus();

        }

        //Last name validation
        if(lastName.isEmpty())
        {
            et_regLastName.setText("");
            reg_lastName.setError("Please provide a last name!");
            et_regLastName.requestFocus();
            return;
        }

        //ID number validation
        if (idNumber.isEmpty())
        {
            et_regId.setText("");
            reg_id.setError("Please enter an ID number");
            et_regId.requestFocus();
            return;
        }
        if (!AppUtility.isValidIDNumber(idNumber))
        {
            et_regId.setText("");
            reg_id.setError("Please enter a valid RSA ID number");
            et_regId.requestFocus();
            return;
        }

        //Phone number validation
        if (phoneNumber.isEmpty())
        {
            et_regPhoneNumber.setText("");
            reg_phoneNumber.setError("Please provide a phone number");
            et_regPhoneNumber.requestFocus();
            return;
        }

        if (!AppUtility.isValidCellphone(phoneNumber))
        {
            et_regPhoneNumber.setText("");
            reg_phoneNumber.setError("Invalid Cellphone Number!");
            et_regPhoneNumber.requestFocus();
            return;
        }

        //Email validation
        if (email.isEmpty())
        {
            et_regEmail.setText("");
            reg_email.setError("Please provide an email!");
            et_regEmail.requestFocus();
            return;
        }

        if (!AppUtility.isValidEmail(email))
        {
            et_regEmail.setText("");
            reg_email.setError("Please provide a valid email!");
            et_regEmail.requestFocus();
            return;
        }

        //Password validation
        if (password.isEmpty())
        {
            et_regPassword.setText("");
            reg_password.setError("Please provide a password");
            et_regPassword.requestFocus();
            return;
        }

        if (!AppUtility.isValidPassword(password))
        {
            et_regPassword.setText("");
            reg_password.setError("Password too simple");
            et_regPassword.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            et_regPassword.setText("");
            reg_password.setError("Password must be 6 characters or more ");
            et_regPassword.requestFocus();
            return;
        }

        if (!confirm.equals(password))
        {
            et_regPassword.setText("");
            et_regConfirmPassword.setText("");
            et_regPassword.requestFocus();
            et_regConfirmPassword.requestFocus();
            reg_password.setError("Password Mismatch!");
            reg_confirmPassword.setError("Password Mismatch!");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    AppUtility.getInputText(et_regFirstName),
                                    AppUtility.getInputText(et_regLastName),
                                    AppUtility.getInputText(et_regId),
                                    AppUtility.getInputText(et_regPhoneNumber),
                                    AppUtility.getInputText(et_regEmail),
                                    AppUtility.getInputText(et_regPassword)
                            );

                            showProgress(true);
                            tvLoad.setText(R.string.registerProgress_text);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification();

                                        View toastView = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.toastLayout));
                                        AppUtility.ShowToast(getApplicationContext(), "Registration successful!\nPlease Verify Account..." , toastView,1);

                                        startActivity(new Intent(Register.this, VerificationSplash.class));
                                        finish();
                                    }
                                    else
                                    {
                                        View toastView = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.toastLayout));
                                        AppUtility.ShowToast(getApplicationContext(), "Failed to register! Please try again..." , toastView,2);
                                        showProgress(false);
                                    }
                                }
                            });
                        }
                        else
                        {
                            View toastView = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.toastLayout));
                            AppUtility.ShowToast(getApplicationContext(), "Failed to register! User already exists" , toastView,2);
                        }
                    }
                });
    }

    /**
     * Shows the progress UI and hides the register form.
     */
    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);

            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}