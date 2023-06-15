package np.com.andajsingh.domain.repositories;

import java.util.List;

import np.com.andajsingh.domain.models.Journey;

public interface DashboardRepository {
    long addJourney(String title, String description, String date, String imagePath, long userId);

    List<Journey> getUserJourneys(long userId);

    void deleteJourney(Journey journey);

    int updateJourney(Journey journey);
}
