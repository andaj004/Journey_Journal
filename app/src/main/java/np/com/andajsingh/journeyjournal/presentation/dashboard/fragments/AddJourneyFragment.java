package np.com.andajsingh.journeyjournal.presentation.dashboard.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import np.com.andajsingh.journeyjournal.presentation.dashboard.viewmodel.DashboardViewModel;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import np.com.andajsingh.journeyjournal.AppLogger;
import np.com.andajsingh.journeyjournal.R;


public class AddJourneyFragment extends Fragment {
    private final static int PERMISSION_CODE_JOURNEY_JOURNAL = 1001;
    private TextInputEditText textInputEditTextJourneyTitle;
    private TextInputEditText textInputEditTextJourneyDescription;
    private TextInputEditText textInputEditTextJourneyDate;
    private MaterialButton btnAddJourney;
    private MaterialButton btnPickImage;
    private ImageView ivJourneyImage;
    private static final int REQUEST_IMAGE_CAPTURE = 1001;
    private static final int REQUEST_IMAGE_PICK_GALLERY = 1002;
    private String journeyImagePath = "";
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
        View view = inflater.inflate(R.layout.fragment_add_journey, container, false);
        initViews(view);
        initButtonsActions();
        observeMutableLiveDatas();
        return view;
    }

    private void initViewModel() {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
    }

    private void initViews(View view) {
        textInputEditTextJourneyTitle = view.findViewById(R.id.text_input_et_journey_title);
        textInputEditTextJourneyDescription = view.findViewById(R.id.text_input_et_journey_description);
        textInputEditTextJourneyDate = view.findViewById(R.id.text_input_et_journey_date);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        ivJourneyImage = view.findViewById(R.id.iv_journey_image);
        btnAddJourney = view.findViewById(R.id.btn_add_journey);
    }

    private void initButtonsActions() {
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForStorageAndCameraPermissions();
            }
        });

        btnAddJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddJourneyButtonClicked();
            }
        });
    }

    private void observeMutableLiveDatas() {
        observeTitleMutableLiveData();
        observeDescriptionMutableLiveData();
        observeDateMutableLiveData();
        observeImagePathMutableLiveData();
        observeIsJourneyAddedMutableLiveData();
    }

    private void onAddJourneyButtonClicked() {
        String title = textInputEditTextJourneyTitle.getText().toString().trim();
        AppLogger.log("Journey Title ::: " + title);

        String description = textInputEditTextJourneyDescription.getText().toString().trim();
        AppLogger.log("Journey Description ::: " + description);

        String date = textInputEditTextJourneyDate.getText().toString().trim();
        AppLogger.log("Journey Date ::: " + date);

        AppLogger.log("Journey Image path ::: " + journeyImagePath);
        dashboardViewModel.addJourneyData(title, description, date, journeyImagePath);
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

    private void observeIsJourneyAddedMutableLiveData() {
        dashboardViewModel.getIsJourneyAddedMutableLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean journeyIsAdded) {
                        if (journeyIsAdded) {
                            navigateToHomeFragment();
                        }
                    }
                }
        );
    }

    private void navigateToHomeFragment() {
        dashboardViewModel.getIsJourneyAddedMutableLiveData().setValue(false);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_addJourneyFragment_to_homeFragment);
        }
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

    private void askForStorageAndCameraPermissions() {
        //TODO ask camera and storage permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isCameraPermissionNotGranted() ||
                    isReadExternalStoragePermissionNotGranted() ||
                    isWriteExternalStoragePermissionNotGranted()
            ) {
                String[] permissionToBeGranted = {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                requestPermissions(
                        permissionToBeGranted,
                        PERMISSION_CODE_JOURNEY_JOURNAL
                );
            } else {
                showAlertDialogForImagePickOrCapture();
            }
        } else {
            showAlertDialogForImagePickOrCapture();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isCameraPermissionNotGranted() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isReadExternalStoragePermissionNotGranted() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isWriteExternalStoragePermissionNotGranted() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE_JOURNEY_JOURNAL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAlertDialogForImagePickOrCapture();
            } else {
                showToastWithMessage("Journey Journal needs Camera and Storage permission to record your journey");
            }
        }
    }

    private void showAlertDialogForImagePickOrCapture() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    dispatchTakePictureIntent();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    pickImageFromGallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile == null) {
                AppLogger.log("Failed to Create Photo File");
                return;
            }
            Uri photoURI = FileProvider.getUriForFile(
                    requireActivity(),
                    "np.com.rishavchudal.journeyjournal.fileprovider",
                    photoFile
            );
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        journeyImagePath = image.getAbsolutePath();
        return image;
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        startActivityForResult(intent, REQUEST_IMAGE_PICK_GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            decodeImageFileToSaveInImageView();
        } else if (requestCode == REQUEST_IMAGE_PICK_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri contentURI = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = requireActivity().getContentResolver().query(contentURI, filePathColumn, null, null, null);
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                journeyImagePath = cursor.getString(columnIndex);
            }
            cursor.close();
            decodeImageFileToSaveInImageView();
        }
    }

    private void decodeImageFileToSaveInImageView() {
        File file = new File(journeyImagePath);
        Glide.with(requireActivity())
                .asBitmap()
                .load(file)
                .into(ivJourneyImage);
    }
}