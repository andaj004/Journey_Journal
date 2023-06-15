package np.com.andajsingh.journeyjournal.presentation.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import np.com.andajsingh.data.repositories.RegistrationRepositoryImpl;
import np.com.andajsingh.domain.models.RegistrationModel;
import np.com.andajsingh.domain.usecases.RegisterUserUseCase;
import np.com.andajsingh.journeyjournal.framework.registration.RegistrationLocalDataSourceImpl;
import np.com.andajsingh.journeyjournal.framework.registration.RegistrationRemoteDataSourceImpl;

/**
 * Created on 28/03/2022.
 */
public class RegistrationViewModel extends AndroidViewModel {
    private final MutableLiveData<RegistrationModel> registrationModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFullNameEmptyLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isEmailIncorrectLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isAddressEmptyLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPasswordIncorrectFormatLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showProgressDialogLiveData = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<RegistrationModel> getRegistrationModelLiveData() {
        return registrationModelLiveData;
    }

    public MutableLiveData<Boolean> getIsFullNameEmptyLiveData() {
        return isFullNameEmptyLiveData;
    }

    public MutableLiveData<Boolean> getIsEmailIncorrectLiveData() {
        return isEmailIncorrectLiveData;
    }

    public MutableLiveData<Boolean> getIsAddressEmptyLiveData() {
        return isAddressEmptyLiveData;
    }

    public MutableLiveData<Boolean> getIsPasswordIncorrectFormatLiveData() {
        return isPasswordIncorrectFormatLiveData;
    }

    public MutableLiveData<Boolean> getShowProgressDialogLiveData() {
        return showProgressDialogLiveData;
    }

    private RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase(
            new RegistrationRepositoryImpl(
                    new RegistrationLocalDataSourceImpl(getApplication()),
                    new RegistrationRemoteDataSourceImpl(getApplication())
            )
    );

    public void validateUserDataForRegistration(
            String fullName,
            String email,
            String address,
            String password,
            String confirmedPassword
    ) {
        showProgressDialogLiveData.setValue(true);
        if (fullName.isEmpty()) {
            isFullNameEmptyLiveData.setValue(true);
            showProgressDialogLiveData.setValue(false);
            return;
        } else {
            isFullNameEmptyLiveData.setValue(false);
        }

        if (email.isEmpty() || !email.matches("([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})")) {
            isEmailIncorrectLiveData.setValue(true);
            showProgressDialogLiveData.setValue(false);
            return;
        } else {
            isEmailIncorrectLiveData.setValue(false);
        }

        if (address.isEmpty()) {
            isAddressEmptyLiveData.setValue(true);
            showProgressDialogLiveData.setValue(false);
            return;
        } else {
            isAddressEmptyLiveData.setValue(false);
        }

        if (password.isEmpty() || confirmedPassword.isEmpty() || !password.equals(confirmedPassword)) {
            isPasswordIncorrectFormatLiveData.setValue(true);
            showProgressDialogLiveData.setValue(false);
            return;
        } else {
            isPasswordIncorrectFormatLiveData.setValue(false);
        }

        RegistrationModel registrationModel = registerUserUseCase
                .registerUser(fullName, email, address, password);
        showProgressDialogLiveData.setValue(false);
        registrationModelLiveData.setValue(registrationModel);
    }
}
