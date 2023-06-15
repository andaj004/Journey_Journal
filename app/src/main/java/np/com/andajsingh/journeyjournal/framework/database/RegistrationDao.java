package np.com.andajsingh.journeyjournal.framework.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created on 28/03/2022.
 */
@Dao
public interface RegistrationDao {

    @Query("Select exists (Select * from user where email_address = :emailAddress)")
    boolean isUserAlreadyPresent(String emailAddress);

    @Insert
    long insertUser(UserEntity userEntity);

    @Query("Select * from user where email_address = :emailAddress")
    UserEntity getRegisteredUser(String emailAddress);
}
