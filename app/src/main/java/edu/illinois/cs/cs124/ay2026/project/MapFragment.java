package edu.illinois.cs.cs124.ay2026.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";

    // Default center: Urbana-Champaign, IL
    private static final double DEFAULT_LAT = 40.1020;
    private static final double DEFAULT_LNG = -88.2272;
    private static final double DEFAULT_ZOOM = 13.0;

    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(DEFAULT_ZOOM);
        mapView.getController().setCenter(new GeoPoint(DEFAULT_LAT, DEFAULT_LNG));

        fetchSightings(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    private void fetchSightings(View view) {
        SightingApi api = SupabaseClient.getInstance().create(SightingApi.class);

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
                hideStatus(view);

                if (sightings.isEmpty()) {
                    showStatus(view, "No sightings yet. Be the first to add one!");
                    return;
                }

                boolean anyPinned = false;
                for (Sighting s : sightings) {
                    if (s.latitude == 0 && s.longitude == 0) continue;

                    Marker marker = new Marker(mapView);
                    marker.setPosition(new GeoPoint(s.latitude, s.longitude));
                    marker.setTitle(s.species);
                    marker.setSnippet(s.animalType + " — " + s.dateSpotted);
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setOnMarkerClickListener((m, map) -> {
                        SightingDetailFragment detail = SightingDetailFragment.newInstance(s);
                        detail.show(getParentFragmentManager(), "sighting_detail");
                        return true;
                    });
                    mapView.getOverlays().add(marker);
                    anyPinned = true;
                }

                if (!anyPinned) {
                    showStatus(view, "Sightings loaded — no locations set yet.");
                }

                mapView.invalidate();
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

    private void hideStatus(View view) {
        view.findViewById(R.id.text_map_placeholder).setVisibility(View.GONE);
    }
}
