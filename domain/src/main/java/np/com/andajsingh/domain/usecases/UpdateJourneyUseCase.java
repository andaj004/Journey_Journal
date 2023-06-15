package np.com.andajsingh.domain.usecases;

import np.com.andajsingh.domain.repositories.DashboardRepository;
import np.com.andajsingh.domain.models.Journey;

public class UpdateJourneyUseCase {
    private final DashboardRepository dashboardRepository;

    public UpdateJourneyUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public int updateJourney(Journey journey) {
        return dashboardRepository.updateJourney(journey);
    }
}
