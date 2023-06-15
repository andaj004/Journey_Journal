package np.com.andajsingh.domain.usecases;

import np.com.andajsingh.domain.repositories.RegistrationRepository;
import np.com.andajsingh.domain.models.RegistrationModel;

/**
 * Created on 28/03/2022.
 */
public class RegisterUserUseCase {
    private final RegistrationRepository registrationRepository;

    public RegisterUserUseCase(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public RegistrationModel registerUser(
            String fullName,
            String email,
            String address,
            String password
    ) {
        return registrationRepository.registerUser(fullName, email, address, password);
    }
}
