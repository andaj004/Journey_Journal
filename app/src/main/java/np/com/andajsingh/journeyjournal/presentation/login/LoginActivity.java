package np.com.andajsingh.journeyjournal.presentation.login;

import static np.com.andajsingh.journeyjournal.R.color.white;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import np.com.andajsingh.journeyjournal.presentation.dashboard.activity.DashboardActivity;
import np.com.andajsingh.journeyjournal.presentation.registration.RegistrationActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.wajahatkarim3.roomexplorer.RoomExplorer;

import np.com.andajsingh.domain.models.LoginModel;
import np.com.andajsingh.domain.models.User;
import np.com.andajsingh.journeyjournal.framework.database.JourneyJournalDatabase;

import np.com.andajsingh.journeyjournal.R;

/**
 * Created on 28/02/2022.
 */
public class LoginActivity extends AppCompatActivity {
    private final static String tagName = "LoginMvvmActivity";
    private TextView tvTitle;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private LoginViewModel loginViewModel;
    private TextView tvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(tagName, "On Create is called");
        bindViewModel();
        initViews();
        assignPageTitle();
        initLoginButtonAction();
        initRegisterClickAction();
        observeMutableLiveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tagName, "On Start is called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tagName, "On Resume is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tagName, "On Pause is called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tagName, "On Stop is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tagName, "On Destroy is called");
    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(LoginActivity.this)
                .setTitle(R.string.title_exit)
                .setMessage(R.string.msg_exit)
                .setPositiveButton(R.string.title_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exitApplication();
                    }
                })
                .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        materialAlertDialogBuilder.show();
    }

    private void bindViewModel() {
        loginViewModel = new ViewModelProvider(LoginActivity.this)
                .get(LoginViewModel.class);
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_title);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
    }

    private void assignPageTitle() {
        tvTitle.setText(R.string.login_title);
    }

    private void initLoginButtonAction() {
        btnLogin.setTextColor(ContextCompat.getColor(this, white));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClicked();
            }
        });

        btnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(tagName, "Button is Long Clicked");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "rishavchudal@ismt.edu.np");
                intent.putExtra(Intent.EXTRA_SUBJECT, "This is a dummy message");
                intent.putExtra(Intent.EXTRA_TEXT, "Dummy test message");
                startActivity(intent);
                return true;
            }
        });
    }

    private void onLoginButtonClicked() {
        Log.d(tagName, "Button is clicked");
        String email = etEmail.getText().toString().trim();
        Log.d(tagName, "Email ::: " + email);
        String password = etPassword.getText().toString().trim();
        Log.d(tagName, "Password ::: " + password);
        loginViewModel.validateLoginCredentials(email, password);
    }

    private void initRegisterClickAction() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvRegister.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RoomExplorer.show(LoginActivity.this, JourneyJournalDatabase.class, JourneyJournalDatabase.DB_NAME);
                return false;
            }
        });
    }

    private void observeMutableLiveData() {
        observeIsEmailOrPasswordEmptyLiveData();
        observeIsEmailIncorrect();
        observeIsPasswordIncorrectLiveData();
        observeIsLoginSuccessLiveData();
    }

    private void observeIsEmailOrPasswordEmptyLiveData() {
        loginViewModel.isEmailOrPasswordEmptyLiveData.observe(
                LoginActivity.this,
                new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEmpty) {
                if (isEmpty) {
                    Log.i(tagName, "observeIsEmailOrPasswordEmptyLiveData: " + isEmpty);
                    Toast.makeText(LoginActivity.this, "Email or Password is Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void observeIsEmailIncorrect() {
        loginViewModel.isEmailIncorrectLiveData.observe(
                LoginActivity.this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isEmailIncorrect) {
                        if (isEmailIncorrect) {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Email is incorrect",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }

    private void observeIsPasswordIncorrectLiveData() {
        loginViewModel.isPasswordIncorrectLiveData.observe(
                LoginActivity.this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isPasswordIncorrect) {
                        if (isPasswordIncorrect) {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Password is incorrect",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }

    private void observeIsLoginSuccessLiveData() {
        loginViewModel.loginModelLiveData.observe(
                LoginActivity.this,
                new Observer<LoginModel>() {
                    @Override
                    public void onChanged(LoginModel loginModel) {
                        onLoginModelChanged(loginModel);
                    }
                });
    }

    private void onLoginModelChanged(LoginModel loginModel) {
        if (loginModel.isLoginSuccess()) {
            Toast.makeText(
                    LoginActivity.this,
                    "Login Success",
                    Toast.LENGTH_SHORT
            ).show();
            startDashboard(loginModel.getUser());
            return;
        }
        Toast.makeText(
                LoginActivity.this,
                loginModel.getMessage(),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void startDashboard(User user) {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        intent.putExtra(DashboardActivity.EXTRA_DATA_USER, user);
        startActivity(intent);
        finish();
    }

    private void exitApplication() {
        finishAndRemoveTask();
    }
}
