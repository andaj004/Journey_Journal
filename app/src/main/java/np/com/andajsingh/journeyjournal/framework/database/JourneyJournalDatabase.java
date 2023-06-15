package np.com.andajsingh.journeyjournal.framework.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created on 23/03/2022.
 */
@Database(
        entities = {UserEntity.class, JourneyEntity.class},
        version = 1
)
public abstract class JourneyJournalDatabase extends RoomDatabase {
    public static final String DB_NAME = "db-journey-journal";
    private static JourneyJournalDatabase INSTANCE;

    public static JourneyJournalDatabase getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    application.getApplicationContext(),
                    JourneyJournalDatabase.class,
                    DB_NAME
            ).allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract LoginDao getLoginDao();
    public abstract RegistrationDao getRegistrationDao();
    public abstract JourneyDao getJourneyDao();
}
