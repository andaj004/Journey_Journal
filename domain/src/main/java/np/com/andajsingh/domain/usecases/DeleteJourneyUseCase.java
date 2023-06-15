package np.com.andajsingh.domain.usecases;

import np.com.andajsingh.domain.repositories.DashboardRepository;
import np.com.andajsingh.domain.models.Journey;

public class DeleteJourneyUseCase {
    private DashboardRepository dashboardRepository;

    public DeleteJourneyUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public void deleteJourney(Journey journey) {
        dashboardRepository.deleteJourney(journey);
    }
}
