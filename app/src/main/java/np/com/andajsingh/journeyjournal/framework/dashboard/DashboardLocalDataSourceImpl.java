package np.com.andajsingh.journeyjournal.framework.dashboard;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import np.com.andajsingh.data.sources.dashboard.DashboardLocalSource;
import np.com.andajsingh.domain.models.Journey;
import np.com.andajsingh.journeyjournal.framework.database.JourneyDao;
import np.com.andajsingh.journeyjournal.framework.database.JourneyEntity;
import np.com.andajsingh.journeyjournal.framework.database.JourneyJournalDatabase;

public class DashboardLocalDataSourceImpl implements DashboardLocalSource {
    private Application application;

    public DashboardLocalDataSourceImpl(Application application) {
        this.application = application;
    }

    @Override
    public long addJourney(
            String title,
            String description,
            String date,
            String imagePath,
            long userId
    ) {
        JourneyEntity journeyEntity = new JourneyEntity();
        journeyEntity.title = title;
        journeyEntity.description = description;
        journeyEntity.date = date;
        journeyEntity.imagePath = imagePath;
        journeyEntity.userId = userId;
        JourneyDao journeyDao = JourneyJournalDatabase.getInstance(application).getJourneyDao();
        try {
            return journeyDao.insertJourney(journeyEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Journey> getUserJourneys(long userId) {
        JourneyDao journeyDao = JourneyJournalDatabase.getInstance(application).getJourneyDao();
        List<Journey> journeys = new ArrayList<>();
        try {
            List<JourneyEntity> journeyEntities = journeyDao.getUserJourneys(userId);
            for (JourneyEntity journeyEntity: journeyEntities) {
                Journey journey = new Journey(
                        journeyEntity.journeyId,
                        journeyEntity.title,
                        journeyEntity.description,
                        journeyEntity.date,
                        journeyEntity.imagePath
                );
                journeys.add(journey);
            }
            return journeys;
        } catch (Exception exception) {
            exception.printStackTrace();
            return journeys;
        }
    }

    @Override
    public void deleteJourney(Journey journey) {
        try {
            JourneyDao journeyDao = JourneyJournalDatabase.getInstance(application).getJourneyDao();
            JourneyEntity journeyEntity = journeyDao.getJourneyById(journey.getId());
            journeyDao.deleteJourney(journeyEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int updateJourney(Journey journeyDataToUpdate) {
        try {
            JourneyDao journeyDao = JourneyJournalDatabase.getInstance(application).getJourneyDao();
            JourneyEntity journeyById = journeyDao.getJourneyById(journeyDataToUpdate.getId());
            journeyById.title = journeyDataToUpdate.getTitle();
            journeyById.description = journeyDataToUpdate.getDescription();
            journeyById.date = journeyDataToUpdate.getDate();
            journeyById.imagePath = journeyDataToUpdate.getImagePath();
            return journeyDao.updateJourney(journeyById);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
