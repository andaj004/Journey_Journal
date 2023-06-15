package np.com.andajsingh.journeyjournal.presentation.dashboard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import np.com.andajsingh.data.repositories.DashboardRepositoryImpl;
import np.com.andajsingh.domain.models.Journey;
import np.com.andajsingh.domain.models.User;
import np.com.andajsingh.domain.usecases.AddJourneyUseCase;
import np.com.andajsingh.domain.usecases.DeleteJourneyUseCase;
import np.com.andajsingh.domain.usecases.FetchJourneysUseCase;
import np.com.andajsingh.domain.usecases.UpdateJourneyUseCase;
import np.com.andajsingh.journeyjournal.framework.dashboard.DashboardLocalDataSourceImpl;
import np.com.andajsingh.journeyjournal.framework.dashboard.DashboardRemoteDataSourceImpl;

/**
 * Created on 22/03/2022.
 */
public class DashboardViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Journey>> journeysLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyTitleInvalidLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyDescriptionInvalidLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyDateInvalidLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyImagePathInvalidLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyAddedMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyDeletedMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isJourneyUpdatedMutableLiveData = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Journey>> getJourneyLiveData() {
        return journeysLiveData;
    }

    public MutableLiveData<Boolean> getIsJourneyTitleInvalidLiveData() {
        return isJourneyTitleInvalidLiveData;
    }

    public MutableLiveData<Boolean> getIsJourneyDescriptionInvalidLiveData() {
        return isJourneyDescriptionInvalidLiveData;
    }

    public MutableLiveData<Boolean> getIsJourneyImagePathInvalidLiveData() {
        return isJourneyImagePathInvalidLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsJourneyAddedMutableLiveData() {
        return isJourneyAddedMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsJourneyDeletedMutableLiveData() {
        return isJourneyDeletedMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsJourneyUpdatedMutableLiveData() {
        return isJourneyUpdatedMutableLiveData;
    }

    private final AddJourneyUseCase addJourneyUseCase = new AddJourneyUseCase(
            new DashboardRepositoryImpl(
                    new DashboardLocalDataSourceImpl(getApplication()),
                    new DashboardRemoteDataSourceImpl(getApplication())
            )
    );

    private final FetchJourneysUseCase fetchJourneysUseCase = new FetchJourneysUseCase(
            new DashboardRepositoryImpl(
                    new DashboardLocalDataSourceImpl(getApplication()),
                    new DashboardRemoteDataSourceImpl(getApplication())
            )
    );

    private final DeleteJourneyUseCase deleteJourneyUseCase = new DeleteJourneyUseCase(
            new DashboardRepositoryImpl(
                    new DashboardLocalDataSourceImpl(getApplication()),
                    new DashboardRemoteDataSourceImpl(getApplication())
            )
    );

    private final UpdateJourneyUseCase updateJourneyUseCase = new UpdateJourneyUseCase(
            new DashboardRepositoryImpl(
                    new DashboardLocalDataSourceImpl(getApplication()),
                    new DashboardRemoteDataSourceImpl(getApplication())
            )
    );

    public void fetchFacts() {
        List<Journey> journeys = fetchJourneysUseCase.getUserJourneys(userMutableLiveData.getValue().getUserId());
        journeysLiveData.setValue(journeys);
    }

    public void addJourneyData(String title, String description, String date, String imagePath) {
        if (title.isEmpty()) {
            isJourneyTitleInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyTitleInvalidLiveData.setValue(false);
        }

        if (description.isEmpty()) {
            isJourneyDescriptionInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyDescriptionInvalidLiveData.setValue(false);
        }

        if (date.isEmpty()) {
            isJourneyDateInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyDateInvalidLiveData.setValue(false);
        }

        if (imagePath.isEmpty()) {
            isJourneyImagePathInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyImagePathInvalidLiveData.setValue(false);
        }

        long userId = userMutableLiveData.getValue().getUserId();
        long rowId = addJourneyUseCase.addJourney(
                title,
                description,
                date,
                imagePath,
                userId);
        isJourneyAddedMutableLiveData.setValue(rowId > 0);
    }

    public void deleteJourney(Journey journey) {
        deleteJourneyUseCase.deleteJourney(journey);
        isJourneyDeletedMutableLiveData.setValue(true);
    }

    public void updateJourneyData(
            String title,
            String description,
            String date,
            String imagePath,
            long journeyId
    ) {
        if (title.isEmpty()) {
            isJourneyTitleInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyTitleInvalidLiveData.setValue(false);
        }

        if (description.isEmpty()) {
            isJourneyDescriptionInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyDescriptionInvalidLiveData.setValue(false);
        }

        if (date.isEmpty()) {
            isJourneyDateInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyDateInvalidLiveData.setValue(false);
        }

        if (imagePath.isEmpty()) {
            isJourneyImagePathInvalidLiveData.setValue(true);
            return;
        } else {
            isJourneyImagePathInvalidLiveData.setValue(false);
        }
        Journey journey = new Journey(
                journeyId,
                title,
                description,
                date,
                imagePath
        );
        long rowId = updateJourneyUseCase.updateJourney(journey);
        isJourneyUpdatedMutableLiveData.setValue(rowId > 0);
    }
}
