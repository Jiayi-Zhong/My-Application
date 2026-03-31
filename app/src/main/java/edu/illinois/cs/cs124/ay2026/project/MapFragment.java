package edu.illinois.cs.cs124.ay2026.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchSightings(view);
    }

    private void fetchSightings(View view) {
        SightingApi api = SupabaseClient.getInstance().create(SightingApi.class);

        // "created_at.desc" tells Supabase to sort newest sightings first.
        api.getAllSightings("*", "created_at.desc").enqueue(new Callback<List<Sighting>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sighting>> call,
                                   @NonNull Response<List<Sighting>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e(TAG, "Failed to load sightings: " + response.code());
                    showStatus(view, "Failed to load sightings.");
                    return;
                }

                List<Sighting> sightings = response.body();
                if (sightings.isEmpty()) {
                    showStatus(view, "No sightings yet. Be the first to add one!");
                    return;
                }

                // Build a simple list of species names to display.
                List<String> labels = new ArrayList<>();
                for (Sighting s : sightings) {
                    labels.add(s.species + " (" + s.animalType + ") — " + s.dateSpotted);
                }

                ListView listView = view.findViewById(R.id.sightings_list);
                TextView statusText = view.findViewById(R.id.text_map_placeholder);
                statusText.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    labels
                ));
            }

            @Override
            public void onFailure(@NonNull Call<List<Sighting>> call, @NonNull Throwable t) {
                Log.e(TAG, "Network error loading sightings", t);
                showStatus(view, "Network error. Check your connection.");
            }
        });
    }

    private void showStatus(View view, String message) {
        TextView statusText = view.findViewById(R.id.text_map_placeholder);
        statusText.setText(message);
        statusText.setVisibility(View.VISIBLE);
    }
}
