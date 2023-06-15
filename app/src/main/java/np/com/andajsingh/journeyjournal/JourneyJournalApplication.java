package np.com.andajsingh.journeyjournal;

import android.app.Application;

/**
 * Created on 24/03/2022.
 */
public class JourneyJournalApplication extends Application {
    private static JourneyJournalApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static JourneyJournalApplication getFactApplicationContext() {
        return INSTANCE;
    }
}
