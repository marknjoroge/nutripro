package com.virtualeatery.controllers;

import static androidx.core.content.PermissionChecker.checkCallingOrSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.virtualeatery.Constants;
import com.virtualeatery.R;
import com.virtualeatery.models.db.DBHandler;
import com.virtualeatery.models.db.MealsTakenModel;
import com.virtualeatery.models.db.NutrientsModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    public static final Integer RECORD_AUDIO_REQUEST_CODE = 1;
    private final int CAMERA_PIC_REQUEST = 2;
    private final int PICK_IMAGE = 5;

    private final String TAG = "SearchMealFragmentTag";
    private String API_KEY;
    private final Constants constants = new Constants();
    private DBHandler dbHandler = new DBHandler(getContextOfApplication());
    private final ProcessRequests processRequests = new ProcessRequests();
    SpeechRecognizer speechRecognizer;
    TextView caloriesToday;
    TextView carbsToday;
    TextView proteinsToday;
    TextView fatsToday;
    EditText queryInput;

    private int[] valuesToday;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        Log.d("SearchMealFragmentTag", String.valueOf(contextOfApplication));
        return contextOfApplication;
    }

    public SearchFragment() {
        // Required empty public constructor
        contextOfApplication = getActivity();
        Log.d("SearchMealFragmentTag1", String.valueOf(contextOfApplication));
    }

    @Override
    public void onAttach(Context activity){
        super.onAttach(activity);
        contextOfApplication = activity;
        Log.d("SearchMealFragmentTag2", String.valueOf(contextOfApplication));
        dbHandler = new DBHandler(activity);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        valuesToday = getDayInfo();

        queryInput = (EditText) rootView.findViewById(R.id.foodNameInput);

        API_KEY = getString(R.string.spoonacular_api_key);

        Button editConsumedFood = rootView.findViewById(R.id.editConsumedFoodsBtn);
        Button searchFrmCamera = rootView.findViewById(R.id.searchFromCamera);
        Button searchInfoBtn = rootView.findViewById(R.id.searchFoodBtn);

        caloriesToday = rootView.findViewById(R.id.caloriesToday);
        carbsToday = rootView.findViewById(R.id.carbsToday);
        fatsToday = rootView.findViewById(R.id.fatsToday);
        proteinsToday = rootView.findViewById(R.id.proteinsToday);

        updateDayInfo();

        editConsumedFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked");
                Intent intent;
                intent = new Intent(getActivity(), AllMealsActivity.class);

                startActivityForResult(intent, 50);
            }
        });
        searchFrmCamera.setOnClickListener(this);
        searchInfoBtn.setOnClickListener(this);

        return rootView;
    }

    public void handleClicks(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.editConsumedFoodsBtn:
                intent = new Intent(getActivity(), AllMealsActivity.class);

                startActivityForResult(intent, 50);
                break;
            case R.id.searchFromCamera:
                checkCameraPermission();
                if (ContextCompat.checkSelfPermission(getContextOfApplication(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                }
                break;
            case R.id.searchFoodBtn:
                Log.v("check", "searchbtn");
//            if (isNetworkAvailable()) {
                if(true){
                    if (!queryInput.getText().toString().equals("")) {
                        processRequests.getNutritionalInfo(getActivity(), TAG, queryInput.getText().toString(), API_KEY);
                    }
                } else {
                    DBHandler dbHandler = new DBHandler(getActivity());

                    NutrientsModel nutrientsList = dbHandler.getFoodInfo(queryInput.getText().toString());

                    if (nutrientsList.getCalories() != null) {
                        Log.v("check", nutrientsList.getCalories());
                        intent = new Intent(getActivity(), NutritionalInformationActivity.class);
                        intent.putExtra("calories", nutrientsList.getCalories());
                        intent.putExtra("carbohydrates", nutrientsList.getCarbs());
                        intent.putExtra("fats", nutrientsList.getFats());
                        intent.putExtra("proteins", nutrientsList.getProteins());
                        intent.putExtra("query", queryInput.getText().toString());
                        startActivity(intent);
                        getActivity().getFragmentManager().popBackStack();
                    } else {
                        Log.v(TAG, "db is null");
                        showErrDialog(getContextOfApplication());
                    }

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        handleClicks(v);
    }

    private void showErrDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("no internet")
                .setMessage("There is no internet connection. Connect to the internet and try again.")

                .setPositiveButton(R.string.refresh, (dialog, which) -> {

                })
                .setIcon(android.R.drawable.stat_notify_error)
                .show();
    }

    public void checkCameraPermission() {
        int MY_PERMISSIONS_REQUEST_CAMERA = 0;
        if (ContextCompat.checkSelfPermission(getContextOfApplication(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    public byte[] imageToBinary(Bitmap photo) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
    public int[] getDayInfo() {
        int calories = 0, carbs = 0, proteins = 0, fats = 0;
        ArrayList<MealsTakenModel> mealsTakenModelArrayList = dbHandler.getEatingInfo(constants.dateToPlain);
        if (!dbHandler.isDBEmpty()) {
            for (int i = 0; i < mealsTakenModelArrayList.size(); i++) {
                NutrientsModel nutrientsModel = dbHandler.getFoodInfo(mealsTakenModelArrayList.get(i).getMeal());
                calories += Integer.parseInt(nutrientsModel.getCalories());
                carbs += Integer.parseInt(nutrientsModel.getCarbs());
                proteins += Integer.parseInt(nutrientsModel.getProteins());
                fats += Integer.parseInt(nutrientsModel.getFats());
            }
        }
        return new int[]{calories, carbs, fats, proteins};
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            updateDayInfo();
            return;
        }
        if ((requestCode == CAMERA_PIC_REQUEST || requestCode == PICK_IMAGE) && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    System.out.println("data = ");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    processRequests.getImageInfo(byteArray, getString(R.string.spoonacular_api_key), getContextOfApplication());
                    break;
                case PICK_IMAGE:
                    final Uri imageUri = data.getData();
                    System.out.println("data = ");
                    try {
                        final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);

                        byte[] image = imageToBinary(BitmapFactory.decodeStream(imageStream));
                        processRequests.getImageInfo(image, getString(R.string.spoonacular_api_key), getContextOfApplication());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.v("qwer", e.getMessage());
                    } catch (NullPointerException n) {
                        Log.v("qwer", n.getMessage());
                    }
            }
        } else {
            Toast.makeText(getContextOfApplication(), "failed to load image. please try again later", Toast.LENGTH_LONG).show();
        }
    }

    public void updateDayInfo() {
        valuesToday = getDayInfo();

        caloriesToday.setText(new StringBuilder().append(getString(R.string.calories)).append(": ").append(valuesToday[0]).append(" cal").toString());
        carbsToday.setText(new StringBuilder().append(getString(R.string.carbohydrates)).append(": ").append(valuesToday[1]).append(" g").toString());
        proteinsToday.setText(new StringBuilder().append(getString(R.string.fats)).append(": ").append(valuesToday[2]).append(" g").toString());
        fatsToday.setText(new StringBuilder().append(getString(R.string.proteins)).append(": ").append(valuesToday[3]).append(" g").toString());
    }
}