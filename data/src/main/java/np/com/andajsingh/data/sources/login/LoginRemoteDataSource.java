package np.com.andajsingh.data.sources.login;

import np.com.andajsingh.domain.models.LoginModel;

/**
 * Created on 02/03/2022.
 */
public interface LoginRemoteDataSource {
    boolean isInternetWorking();

    LoginModel authenticateLoginInRemoteServer(String email, String password);
}
