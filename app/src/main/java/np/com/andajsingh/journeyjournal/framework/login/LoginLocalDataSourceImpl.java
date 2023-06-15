package np.com.andajsingh.journeyjournal.framework.login;

import android.app.Application;

import np.com.andajsingh.journeyjournal.framework.database.JourneyJournalDatabase;
import np.com.andajsingh.journeyjournal.framework.database.LoginDao;
import np.com.andajsingh.journeyjournal.framework.database.UserEntity;

import np.com.andajsingh.data.sources.login.LoginLocalDataSource;
import np.com.andajsingh.domain.models.LoginModel;
import np.com.andajsingh.domain.models.User;

/**
 * Created on 03/03/2022.
 */
public class LoginLocalDataSourceImpl implements LoginLocalDataSource {
    private final Application application;

    public LoginLocalDataSourceImpl(Application application) {
        this.application = application;
    }

    @Override
    public LoginModel authenticateLoginInLocalDatabase(
            String email,
            String password
    ) {
        LoginDao loginDao = JourneyJournalDatabase.getInstance(application).getLoginDao();
        UserEntity userEntity = loginDao.getValidUser(email.toLowerCase(), password);
        if (userEntity == null) {
            return new LoginModel(false, "Login Failed");
        }
        User user = mapFrameWorkToDomain(userEntity);
        LoginModel loginModel = new LoginModel(
                true,
                "Login Success"
        );
        loginModel.setUser(user);
        return loginModel;
    }

    private User mapFrameWorkToDomain(UserEntity userEntity) {
        return new User(
                userEntity.userId,
                userEntity.fullName,
                userEntity.emailAddress,
                userEntity.address
        );
    }
}
