package np.com.andajsingh.journeyjournal.presentation.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import np.com.andajsingh.journeyjournal.presentation.dashboard.viewmodel.DashboardViewModel;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import np.com.andajsingh.domain.models.Journey;
import np.com.andajsingh.journeyjournal.AppLogger;
import np.com.andajsingh.journeyjournal.R;

public class EditJourneyFragment extends Fragment {
    public final static String ARGS_JOURNEY_DATA = "journey_data";
    private TextInputEditText textInputEditTextJourneyTitle;
    private TextInputEditText textInputEditTextJourneyDescription;
    private TextInputEditText textInputEditTextJourneyDate;
    private MaterialButton btnUpdateJourney;
    private MaterialButton btnPickImage;
    private ImageView ivJourneyImage;
    private DashboardViewModel dashboardViewModel;
    private Journey journey;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        assignJourneyFromArguments();
        assignNavController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_journey, container, false);
        initViews(view);
        setDataToViews();
        initUpdateButtonAction();
        observeMutableLiveDatas();
        return view;
    }

    private void initViewModel() {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
    }

    private void assignJourneyFromArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            journey = (Journey) bundle.getSerializable(ARGS_JOURNEY_DATA);
        }
    }

    private void assignNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    private void initViews(View view) {
        textInputEditTextJourneyTitle = view.findViewById(R.id.text_input_et_journey_title);
        textInputEditTextJourneyDescription = view.findViewById(R.id.text_input_et_journey_description);
        textInputEditTextJourneyDate = view.findViewById(R.id.text_input_et_journey_date);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        ivJourneyImage = view.findViewById(R.id.iv_journey_image);
        btnUpdateJourney = view.findViewById(R.id.btn_add_journey);
    }

    private void setDataToViews() {
        if (journey == null) {
            navigateBackToHomeFragment();
        }
        try {
            textInputEditTextJourneyTitle.setText(journey.getTitle());
            textInputEditTextJourneyDescription.setText(journey.getDescription());
            textInputEditTextJourneyDate.setText(journey.getDate());
            File file = new File(journey.getImagePath());
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(file)
                    .into(ivJourneyImage);
            btnPickImage.setVisibility(View.GONE);
            btnUpdateJourney.setText(R.string.title_update_journey);
        } catch (Exception exception) {
            exception.printStackTrace();
            navigateBackToHomeFragment();
        }
    }

    private void navigateBackToHomeFragment() {
        navController.navigate(R.id.action_editJourneyFragment_to_homeFragment);
    }

    private void observeMutableLiveDatas() {
        observeTitleMutableLiveData();
        observeDescriptionMutableLiveData();
        observeDateMutableLiveData();
        observeImagePathMutableLiveData();
        observeIsJourneyUpdatedMutableLiveData();
    }

    private void observeTitleMutableLiveData() {
        dashboardViewModel.getIsJourneyTitleInvalidLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isTitleInvalid) {
                        if (isTitleInvalid) {
                            showToastWithMessage("Please enter a valid title");
                        }
                    }
                });
    }

    private void observeDescriptionMutableLiveData() {
        dashboardViewModel.getIsJourneyDescriptionInvalidLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isDescriptionInvalid) {
                        if (isDescriptionInvalid) {
                            showToastWithMessage("Please enter a valid description");
                        }
                    }
                }
        );
    }

    private void observeDateMutableLiveData() {
        dashboardViewModel.getIsJourneyDescriptionInvalidLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean dateIsInvalid) {
                        if (dateIsInvalid) {
                            showToastWithMessage("Please enter a valid date");
                        }
                    }
                }
        );
    }

    private void observeImagePathMutableLiveData() {
        dashboardViewModel.getIsJourneyImagePathInvalidLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isImagePathInvalid) {
                        if (isImagePathInvalid) {
                            showToastWithMessage("Please take photo or pick from gallery");
                        }
                    }
                }
        );
    }

    private void observeIsJourneyUpdatedMutableLiveData() {
        dashboardViewModel.getIsJourneyUpdatedMutableLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean journeyIsUpdated) {
                        if (journeyIsUpdated) {
                            dashboardViewModel.getIsJourneyUpdatedMutableLiveData().setValue(false);
                            navigateBackToHomeFragment();
                        }
                    }
                }
        );
    }

    private void initUpdateButtonAction() {
        btnUpdateJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClicked();
            }
        });
    }

    private void showToastWithMessage(String message) {
        if (message.isEmpty()) {
            return;
        }
        Toast.makeText(
                requireActivity(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    private void onUpdateClicked() {
        String title = textInputEditTextJourneyTitle.getText().toString().trim();
        AppLogger.log("Updated Journey Title ::: " + title);

        String description = textInputEditTextJourneyDescription.getText().toString().trim();
        AppLogger.log("Journey Description ::: " + description);

        String date = textInputEditTextJourneyDate.getText().toString().trim();
        AppLogger.log("Updated Journey Date ::: " + date);

        AppLogger.log("Updated Journey Image path ::: " + journey.getImagePath());
        dashboardViewModel.updateJourneyData(
                title,
                description,
                date,
                journey.getImagePath(),
                journey.getId()
        );
    }
}
