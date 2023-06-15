package np.com.andajsingh.data.sources.dashboard;

import java.util.List;

import np.com.andajsingh.domain.models.Journey;

public interface DashboardRemoteSource {
    boolean isInternetWorking();

    long addJourney(String title, String description, String date, String imagePath, long userId);

    List<Journey> getUserJourneys(long userId);

    void deleteJourney(Journey journey);

    int updateJourney(Journey journey);
}
