package np.com.andajsingh.domain.repositories;

import np.com.andajsingh.domain.models.LoginModel;

/**
 * Created on 02/03/2022.
 */
public interface LoginRepository {
    LoginModel authenticateLogin(String email, String password);
}
