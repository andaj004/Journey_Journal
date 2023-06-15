package np.com.andajsingh.domain.usecases;

import np.com.andajsingh.domain.repositories.DashboardRepository;

import java.util.List;

import np.com.andajsingh.domain.models.Journey;

public class FetchJourneysUseCase {
    private final DashboardRepository dashboardRepository;

    public FetchJourneysUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public List<Journey> getUserJourneys(long userId) {
        return dashboardRepository.getUserJourneys(userId);
    }
}
