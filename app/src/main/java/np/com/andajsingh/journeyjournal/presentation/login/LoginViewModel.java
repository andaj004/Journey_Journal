package np.com.andajsingh.journeyjournal.presentation.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import np.com.andajsingh.data.repositories.LoginRepositoryImpl;
import np.com.andajsingh.domain.models.LoginModel;
import np.com.andajsingh.domain.usecases.LoginAuthenticateUseCase;
import np.com.andajsingh.journeyjournal.framework.login.LoginLocalDataSourceImpl;
import np.com.andajsingh.journeyjournal.framework.login.LoginRemoteDataSourceImpl;

/**
 * Created on 28/02/2022.
 */
public class LoginViewModel extends AndroidViewModel {
    final MutableLiveData<Boolean> isEmailOrPasswordEmptyLiveData = new MutableLiveData<>();
    final MutableLiveData<Boolean> isEmailIncorrectLiveData = new MutableLiveData<>();
    final MutableLiveData<Boolean> isPasswordIncorrectLiveData = new MutableLiveData<>();
    final MutableLiveData<LoginModel> loginModelLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    private LoginAuthenticateUseCase loginAuthenticateUseCase = new LoginAuthenticateUseCase(
            new LoginRepositoryImpl(
                    new LoginRemoteDataSourceImpl(),
                    new LoginLocalDataSourceImpl(getApplication())
            )
    );

    public void validateLoginCredentials(String emailAddress, String password) {
        if (emailAddress.isEmpty() || password.isEmpty()) {
            isEmailOrPasswordEmptyLiveData.setValue(true);
            return;
        } else {
            isEmailOrPasswordEmptyLiveData.setValue(false);
        }

        if (!emailAddress.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            isEmailIncorrectLiveData.setValue(true);
            return;
        } else {
            isEmailIncorrectLiveData.setValue(false);
        }
        LoginModel loginModel = loginAuthenticateUseCase.authenticateLogin(emailAddress, password);
        loginModelLiveData.setValue(loginModel);
    }
}
