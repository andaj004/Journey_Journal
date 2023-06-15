package np.com.andajsingh.domain.usecases;

import np.com.andajsingh.domain.repositories.LoginRepository;
import np.com.andajsingh.domain.models.LoginModel;

/**
 * Created on 02/03/2022.
 */
public class LoginAuthenticateUseCase {
    private final LoginRepository loginRepository;

    public LoginAuthenticateUseCase(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginModel authenticateLogin(String email, String password) {
        return loginRepository.authenticateLogin(email, password);
    }
}
