package np.com.andajsingh.data.sources.registration;

import np.com.andajsingh.domain.models.RegistrationModel;

/**
 * Created on 28/03/2022.
 */
public interface RegistrationLocalDataSource {
    RegistrationModel registerUserInLocalDataBase(
            String fullName,
            String email,
            String address,
            String password
    );
}
