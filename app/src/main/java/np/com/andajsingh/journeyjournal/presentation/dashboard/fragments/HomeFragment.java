package np.com.andajsingh.journeyjournal.presentation.dashboard.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import np.com.andajsingh.journeyjournal.presentation.dashboard.viewmodel.DashboardViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import np.com.andajsingh.domain.models.Journey;
import np.com.andajsingh.journeyjournal.R;

import np.com.andajsingh.journeyjournal.presentation.dashboard.adapter.JourneyRecyclerAdapter;

public class HomeFragment extends Fragment implements JourneyRecyclerAdapter.JourneyRecyclerAdapterListener {
    private FloatingActionButton favAddFact;
    private RecyclerView journeysRecyclerView;
    private DashboardViewModel dashboardViewModel;
    private TextView tvNoData;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initWidgets(view);
        initButtonAction();
        observeMutableLiveDatas();
        fetchFacts();
        return view;
    }

    private void initViewModel() {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
    }

    private void initWidgets(View view) {
        favAddFact = view.findViewById(R.id.fav_add_fact);
        initRecyclerView(view);
        tvNoData = view.findViewById(R.id.tv_no_data);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    private void initRecyclerView(View view) {
        journeysRecyclerView = view.findViewById(R.id.journeys_recycler_view);
        journeysRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.item_decoration));
        if (itemDecorator != null) {
            journeysRecyclerView.addItemDecoration(itemDecorator);
        }
    }

    private void initButtonAction() {
        favAddFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_addFactFragment);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFacts();
            }
        });
    }

    private void observeMutableLiveDatas() {
        dashboardViewModel.getJourneyLiveData().observe(
                this,
                new Observer<List<Journey>>() {
                    @Override
                    public void onChanged(List<Journey> journeys) {
                        swipeRefreshLayout.setRefreshing(false);
                        journeysRecyclerView.setVisibility(View.GONE);
                        loadRecyclerViewWithValidFacts(journeys);
                    }
                }
        );

        dashboardViewModel.getIsJourneyDeletedMutableLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean journeyIsDeleted) {
                        if (journeyIsDeleted) {
                            fetchFacts();
                            dashboardViewModel.getIsJourneyDeletedMutableLiveData().setValue(false);
                        }
                    }
                }
        );
    }

    private void loadRecyclerViewWithValidFacts(List<Journey> journeys) {
        if (journeys.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
            journeysRecyclerView.setVisibility(View.VISIBLE);
        }
        JourneyRecyclerAdapter journeyRecyclerAdapter = new JourneyRecyclerAdapter(
                requireActivity(),
                journeys,
                this
        );
        journeysRecyclerView.setAdapter(journeyRecyclerAdapter);
        journeyRecyclerAdapter.notifyDataSetChanged();
    }

    private void fetchFacts() {
        tvNoData.setVisibility(View.GONE);
        journeysRecyclerView.setVisibility(View.VISIBLE);
        dashboardViewModel.fetchFacts();
    }

    @Override
    public void onJourneyEditButtonClicked(Journey journey) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EditJourneyFragment.ARGS_JOURNEY_DATA, journey);
        Navigation.findNavController(requireView()).navigate(
                R.id.action_homeFragment_to_editJourneyFragment,
                bundle
        );
    }

    @Override
    public void onJourneyDeleteButtonClicked(Journey journey) {
        dashboardViewModel.deleteJourney(journey);
    }

    @Override
    public void onJourneyShareButtonClicked(Journey journey) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, journey.getDescription());

        sendIntent.putExtra(Intent.EXTRA_TITLE, journey.getTitle());

        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(journey.getImagePath()));
        sendIntent.setType("image/jpeg");

        startActivity(Intent.createChooser(sendIntent, null));
    }
}