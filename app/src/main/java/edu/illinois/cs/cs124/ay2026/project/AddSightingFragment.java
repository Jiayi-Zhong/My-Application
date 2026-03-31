package edu.illinois.cs.cs124.ay2026.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSightingFragment extends Fragment {

    private static final String TAG = "AddSightingFragment";

    private double selectedLat = 0;
    private double selectedLng = 0;

    private final ActivityResultLauncher<String> locationPermissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (granted) {
                getLocation();
            } else {
                Toast.makeText(requireContext(),
                    "Location permission denied. You can still submit without a location.",
                    Toast.LENGTH_LONG).show();
            }
        });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_sighting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = view.findViewById(R.id.spinner_animal_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            new String[]{"Wild Animal", "Stray Animal"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        view.findViewById(R.id.button_location).setOnClickListener(v -> requestLocation(view));
        view.findViewById(R.id.button_submit).setOnClickListener(v -> submitSighting(view));
    }

    private void requestLocation(View view) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void getLocation() {
        LocationManager lm = (LocationManager) requireContext().getSystemService(LocationManager.class);

        // Try last known location first (instant, no wait).
        Location last = null;
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            last = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (last == null) {
                last = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if (last != null) {
            setLocation(last);
            return;
        }

        // No cached location — request a fresh one.
        View view = getView();
        if (view != null) {
            ((TextView) view.findViewById(R.id.text_location_status)).setText("Getting location…");
        }

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                setLocation(location);
                lm.removeUpdates(this);
            }
        };

        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        } catch (SecurityException e) {
            Log.e(TAG, "Location permission missing", e);
        }
    }

    private void setLocation(Location location) {
        selectedLat = location.getLatitude();
        selectedLng = location.getLongitude();

        View view = getView();
        if (view != null) {
            String coords = String.format("%.5f, %.5f", selectedLat, selectedLng);
            ((TextView) view.findViewById(R.id.text_location_status)).setText(coords);
        }
    }

    private void submitSighting(View view) {
        EditText speciesField = view.findViewById(R.id.edit_species);
        EditText dateField = view.findViewById(R.id.edit_date);
        EditText backgroundField = view.findViewById(R.id.edit_background);
        EditText tipsField = view.findViewById(R.id.edit_tips);
        Spinner spinner = view.findViewById(R.id.spinner_animal_type);

        String species = speciesField.getText().toString().trim();
        String date = dateField.getText().toString().trim();

        if (species.isEmpty()) {
            speciesField.setError("Species is required");
            return;
        }
        if (date.isEmpty()) {
            dateField.setError("Date is required");
            return;
        }

        Sighting sighting = new Sighting();
        sighting.species = species;
        sighting.animalType = spinner.getSelectedItem().toString();
        sighting.dateSpotted = date;
        sighting.backgroundInfo = backgroundField.getText().toString().trim();
        sighting.protectionTips = tipsField.getText().toString().trim();
        sighting.latitude = selectedLat;
        sighting.longitude = selectedLng;

        Button submitButton = view.findViewById(R.id.button_submit);
        submitButton.setEnabled(false);

        SightingApi api = SupabaseClient.getInstance().create(SightingApi.class);
        api.addSighting(sighting).enqueue(new Callback<List<Sighting>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sighting>> call,
                                   @NonNull Response<List<Sighting>> response) {
                submitButton.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Sighting submitted!", Toast.LENGTH_SHORT).show();
                    clearForm(view);
                } else {
                    Log.e(TAG, "Submit failed: " + response.code());
                    Toast.makeText(requireContext(), "Failed to submit. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Sighting>> call, @NonNull Throwable t) {
                submitButton.setEnabled(true);
                Log.e(TAG, "Network error submitting sighting", t);
                Toast.makeText(requireContext(), "Network error. Check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm(View view) {
        ((EditText) view.findViewById(R.id.edit_species)).setText("");
        ((EditText) view.findViewById(R.id.edit_date)).setText("");
        ((EditText) view.findViewById(R.id.edit_background)).setText("");
        ((EditText) view.findViewById(R.id.edit_tips)).setText("");
        ((Spinner) view.findViewById(R.id.spinner_animal_type)).setSelection(0);
        ((TextView) view.findViewById(R.id.text_location_status)).setText("No location set");
        selectedLat = 0;
        selectedLng = 0;
    }
}
