package np.com.andajsingh.journeyjournal.framework.login;

import np.com.andajsingh.data.sources.login.LoginRemoteDataSource;
import np.com.andajsingh.domain.models.LoginModel;

/**
 * Created on 03/03/2022.
 */
public class LoginRemoteDataSourceImpl implements LoginRemoteDataSource {
    @Override
    public boolean isInternetWorking() {
        return false;
    }

    @Override
    public LoginModel authenticateLoginInRemoteServer(String email, String password) {
        return null;
    }
}
