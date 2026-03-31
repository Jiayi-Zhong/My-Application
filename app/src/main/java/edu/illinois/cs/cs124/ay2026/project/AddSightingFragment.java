package edu.illinois.cs.cs124.ay2026.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSightingFragment extends Fragment {

    private static final String TAG = "AddSightingFragment";

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

        Button submitButton = view.findViewById(R.id.button_submit);
        submitButton.setOnClickListener(v -> submitSighting(view));
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
    }
}
