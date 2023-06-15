package np.com.andajsingh.domain.usecases;

import np.com.andajsingh.domain.repositories.DashboardRepository;

public class AddJourneyUseCase {
    private DashboardRepository dashboardRepository;

    public AddJourneyUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public long addJourney(String title, String description, String date, String imagePath, long userId) {
        return dashboardRepository.addJourney(title, description, date, imagePath, userId);
    }
}
