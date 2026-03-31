package edu.illinois.cs.cs124.ay2026.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SightingDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_SPECIES = "species";
    private static final String ARG_ANIMAL_TYPE = "animal_type";
    private static final String ARG_DATE = "date";
    private static final String ARG_BACKGROUND = "background";
    private static final String ARG_TIPS = "tips";

    public static SightingDetailFragment newInstance(Sighting sighting) {
        SightingDetailFragment fragment = new SightingDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPECIES, sighting.species);
        args.putString(ARG_ANIMAL_TYPE, sighting.animalType);
        args.putString(ARG_DATE, sighting.dateSpotted);
        args.putString(ARG_BACKGROUND, sighting.backgroundInfo);
        args.putString(ARG_TIPS, sighting.protectionTips);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sighting_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = requireArguments();

        setText(view, R.id.detail_species, args.getString(ARG_SPECIES));
        setText(view, R.id.detail_animal_type, args.getString(ARG_ANIMAL_TYPE));
        setText(view, R.id.detail_date, "Spotted: " + args.getString(ARG_DATE));
        setOptionalText(view, R.id.detail_background, args.getString(ARG_BACKGROUND), "No background info provided.");
        setOptionalText(view, R.id.detail_tips, args.getString(ARG_TIPS), "No protection tips provided.");
    }

    private void setText(View view, int id, String text) {
        ((TextView) view.findViewById(id)).setText(text);
    }

    private void setOptionalText(View view, int id, String text, String fallback) {
        String display = (text != null && !text.isEmpty()) ? text : fallback;
        ((TextView) view.findViewById(id)).setText(display);
    }
}
