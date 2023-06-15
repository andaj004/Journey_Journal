package np.com.andajsingh.journeyjournal.presentation.dashboard.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import np.com.andajsingh.journeyjournal.presentation.dashboard.viewmodel.DashboardViewModel;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import np.com.andajsingh.domain.models.User;
import np.com.andajsingh.journeyjournal.R;

public class DashboardActivity extends AppCompatActivity {
    public static String EXTRA_DATA_USER = "extra_data_user";
    private BottomNavigationView bottomNavigationView;
    private FragmentContainerView navHostContainerView;
    private NavHostFragment navHostFragment;
    private Toolbar toolbar;
    private DashboardViewModel dashboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initViewModel();
        assignBundleDataToViewModelLiveData();
        initViews();
        setUpToolBarWithNavigationComponent();
        setUpBottomNavigationViewWithNavController();
        showViewsBasedOnNavigationArguments();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Do you want to exit?", Toast.LENGTH_SHORT).show();
    }


    private void initViewModel() {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    private void assignBundleDataToViewModelLiveData() {
        if (getIntent() != null) {
            User user = (User) getIntent().getSerializableExtra(EXTRA_DATA_USER);
            if (user != null) {
                dashboardViewModel.getUserMutableLiveData().setValue(user);
            }
        }
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navHostContainerView = findViewById(R.id.nav_host_fragment);
        toolbar = findViewById(R.id.toolbar_dashboard);
    }

    private void setUpToolBarWithNavigationComponent() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            AppBarConfiguration appBarConfiguration =
                    new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupWithNavController(
                    toolbar, navController, appBarConfiguration);
        }
    }

    private void setUpBottomNavigationViewWithNavController() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }

    private void showViewsBasedOnNavigationArguments() {
        NavController navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(
                    @NonNull NavController navController,
                    @NonNull NavDestination navDestination,
                    @Nullable Bundle arguments
            ) {
                if (arguments == null) {
                    return;
                }
                showToolbarBasedOnArguments(arguments);
                showBottomNavigationViewBasedOnArguments(arguments);
            }
        });
    }

    private void showToolbarBasedOnArguments(Bundle arguments) {
        boolean showToolBar = arguments.getBoolean(
                getString(R.string.args_show_toolbar),
                false
        );
        if (showToolBar) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    private void showBottomNavigationViewBasedOnArguments(Bundle arguments) {
        boolean showBottomNavigationView = arguments.getBoolean(
                getString(R.string.args_show_bottom_navigation_view),
                false
        );
        if (showBottomNavigationView) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }
}