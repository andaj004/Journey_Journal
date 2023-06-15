package np.com.andajsingh.data.repositories;

import np.com.andajsingh.data.sources.login.LoginLocalDataSource;
import np.com.andajsingh.data.sources.login.LoginRemoteDataSource;
import np.com.andajsingh.domain.models.LoginModel;
import np.com.andajsingh.domain.repositories.LoginRepository;

/**
 * Created on 02/03/2022.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private final LoginRemoteDataSource loginRemoteDataSource;
    private final LoginLocalDataSource loginLocalDataSource;

    public LoginRepositoryImpl(
            LoginRemoteDataSource loginRemoteDataSource,
            LoginLocalDataSource loginLocalDataSource
    ) {
        this.loginRemoteDataSource = loginRemoteDataSource;
        this.loginLocalDataSource = loginLocalDataSource;
    }

    @Override
    public LoginModel authenticateLogin(String email, String password) {
        boolean isInternetWorking = loginRemoteDataSource.isInternetWorking();
        if (isInternetWorking) {
           return loginRemoteDataSource.authenticateLoginInRemoteServer(email, password);
        } else {
            return loginLocalDataSource.authenticateLoginInLocalDatabase(email, password);
        }
    }
}
