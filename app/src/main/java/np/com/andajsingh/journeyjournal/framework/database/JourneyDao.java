package np.com.andajsingh.journeyjournal.framework.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface JourneyDao {
    @Insert
    long insertJourney(JourneyEntity journeyEntity);

    @Update
    int updateJourney(JourneyEntity journeyEntity);

    @Delete
    void deleteJourney(JourneyEntity journeyEntity);

    @Query("Select * from journey where user_id = :userId")
    List<JourneyEntity> getUserJourneys(long userId);

    @Query("Select * from journey where journey_id = :journeyId")
    JourneyEntity getJourneyById(long journeyId);
}
