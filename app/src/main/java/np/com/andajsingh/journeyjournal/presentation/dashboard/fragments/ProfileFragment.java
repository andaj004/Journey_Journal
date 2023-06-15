package np.com.andajsingh.journeyjournal.presentation.dashboard.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import np.com.andajsingh.journeyjournal.presentation.dashboard.viewmodel.DashboardViewModel;

import com.google.android.material.button.MaterialButton;

import np.com.andajsingh.domain.models.User;
import np.com.andajsingh.journeyjournal.R;

import np.com.andajsingh.journeyjournal.presentation.login.LoginActivity;

public class ProfileFragment extends Fragment {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvAddress;
    private MaterialButton materialButtonLogOut;
    private DashboardViewModel dashboardViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        initLogOutButtonAction();
        setDataToViewFromLiveData();
        return view;
    }

    private void initViewModel() {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
    }

    private void initViews(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddress = view.findViewById(R.id.tv_address);
        materialButtonLogOut = view.findViewById(R.id.btn_log_out);
    }

    private void initLogOutButtonAction() {
        materialButtonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                requireActivity().startActivity(intent);
            }
        });
    }

    private void setDataToViewFromLiveData() {
        User user = dashboardViewModel.getUserMutableLiveData().getValue();
        if (user == null) {
            return;
        }
        tvName.setText(user.getFullName());
        tvEmail.setText("Email : " + user.getEmailAddress());
        tvAddress.setText("Address : " + user.getAddress());
    }
}