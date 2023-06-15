package np.com.andajsingh.data.repositories;

import np.com.andajsingh.data.sources.registration.RegistrationLocalDataSource;
import np.com.andajsingh.data.sources.registration.RegistrationRemoteDataSource;
import np.com.andajsingh.domain.models.RegistrationModel;
import np.com.andajsingh.domain.repositories.RegistrationRepository;

/**
 * Created on 28/03/2022.
 */
public class RegistrationRepositoryImpl implements RegistrationRepository {
    private RegistrationLocalDataSource registrationLocalDataSource;
    private RegistrationRemoteDataSource registrationRemoteDataSource;

    public RegistrationRepositoryImpl(
            RegistrationLocalDataSource registrationLocalDataSource,
            RegistrationRemoteDataSource registrationRemoteDataSource
    ) {
        this.registrationLocalDataSource = registrationLocalDataSource;
        this.registrationRemoteDataSource = registrationRemoteDataSource;
    }

    @Override
    public RegistrationModel registerUser(
            String fullName,
            String email,
            String address,
            String password
    ) {
        if (registrationRemoteDataSource.isInternetWorking()) {
            return registrationRemoteDataSource
                    .registerUserInRemoteServer(fullName, email, address, password);
        }
        return registrationLocalDataSource
                .registerUserInLocalDataBase(fullName, email, address, password);
    }
}
