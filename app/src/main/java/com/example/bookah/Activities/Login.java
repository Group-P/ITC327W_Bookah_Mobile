package com.example.bookah.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookah.Activities.SplashScreens.HelloSplash;
import com.example.bookah.AppUtilities.AppUtility;
import com.example.bookah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements  View.OnClickListener{

    //Variables
    Animation topAnimation;
    ImageView logo_image;
    TextInputLayout login_email, login_password;
    TextInputEditText et_loginEmail, et_loginPassword;
    TextView tvRegisterAccount;
    //CheckBox cb_StayLoggedIn;
    Button btnLogin /*,btnReset*/;

    //Firebase
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        logo_image = findViewById(R.id.login_logo);

        login_email = findViewById(R.id.login_email);
        et_loginEmail = findViewById(R.id.et_loginEmail);

        login_password = findViewById(R.id.login_password);
        et_loginPassword = findViewById(R.id.et_loginPassword);

        //cb_StayLoggedIn = findViewById(R.id.cb_StayLoggedIn);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvRegisterAccount = findViewById(R.id.tvRegisterAccount);
        tvRegisterAccount.setOnClickListener(this);

        /*btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);*/

        //Firebase
        auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        logo_image.startAnimation(topAnimation);

        //Functionality

        //Paper.init(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegisterAccount:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btnLogin:

                //All the validation code
                //if (Common.isConnectedToInternet(getBaseContext()))
                //{
                /*if (cb_StayLoggedIn.isChecked())
                {
                    Paper.book().write(Common.USER_KEY, AppUtility.getInputText(et_loginEmail));
                    Paper.book().write(Common.PWD_KEY, AppUtility.getInputText(et_loginPassword));
                }*/
                if (AppUtility.validateInput(new TextInputLayout[]{
                                login_email, login_password
                        }, getResources().getStringArray(R.array.signIn_errors),
                        et_loginEmail, et_loginPassword))
                {
                    userLogin();
                }
                //}
               /* else
                {
                    View toastView = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toastLayout));
                    AppUtility.ShowToast(getApplicationContext(), "Unable to connect!\nPlease check your internet connection", toastView, 2);
                }// end (Common.isConnectedToInternet(getBaseContext())) else*/

                break;

            /*case R.id.btnReset:
                startActivity(new Intent(Login.this, ForgotPassword.class));*/
        }
    }

    private void userLogin() {

        String email = AppUtility.getInputText(et_loginEmail);
        String password = AppUtility.getInputText(et_loginPassword);


        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            assert user != null;
                            if(user.isEmailVerified())
                            {
                                startActivity(new Intent(Login.this, HelloSplash.class));
                                //Common.currentUser = user;
                                finish();
                            }
                            else
                                {
                                    View toastView = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.toastLayout));
                                    AppUtility.ShowToast(getApplicationContext(), "Email not verified!\nPlease check email to verify account", toastView, 2);
                            }
                        }
                        else
                            {
                                View toastView = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.toastLayout));
                                AppUtility.ShowToast(getApplicationContext(), "Credentials are incorrect!\nPlease register", toastView, 2);
                        }
                    }
                });
    }
}