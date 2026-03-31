package edu.illinois.cs.cs124.ay2026.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resources, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.organizations_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new OrganizationAdapter(getSampleOrganizations()));
    }

    private List<Organization> getSampleOrganizations() {
        List<Organization> list = new ArrayList<>();

        list.add(new Organization(
            "Illinois Wildlife Center",
            "Wildlife Rehabilitation",
            "(217) 555-0192",
            "info@ilwildlifecenter.org",
            "ilwildlifecenter.org",
            "2301 S. Oak St, Champaign, IL",
            "Rehabilitates injured and orphaned native Illinois wildlife and returns them to the wild.",
            "Mon–Sat 8am–6pm",
            false
        ));

        list.add(new Organization(
            "Champaign County Animal Control",
            "Animal Control & Rescue",
            "(217) 555-0148",
            "animalcontrol@champaigncounty.gov",
            "champaigncounty.gov/animal-control",
            "1905 N. Crystal Lake Dr, Urbana, IL",
            "Handles stray, injured, and dangerous animals in Champaign County. Accepts surrendered pets.",
            "Mon–Fri 8am–5pm",
            true
        ));

        list.add(new Organization(
            "Prairie Rivers Network",
            "Wildlife Advocacy",
            "(217) 555-0173",
            "contact@prairierivers.org",
            "prairierivers.org",
            "110 E. Main St, Champaign, IL",
            "Advocates for Illinois waterways and the wildlife that depend on healthy river ecosystems.",
            "Mon–Fri 9am–5pm",
            false
        ));

        list.add(new Organization(
            "Orphaned Wildlife Care",
            "Wildlife Rehabilitation",
            "(217) 555-0261",
            "help@orphanedwildlifecare.org",
            "orphanedwildlifecare.org",
            "4420 W. Springfield Ave, Champaign, IL",
            "Specializes in caring for orphaned baby animals including raccoons, opossums, and songbirds.",
            "Daily 7am–9pm",
            false
        ));

        list.add(new Organization(
            "Central Illinois Cat Rescue",
            "Animal Rescue",
            "(217) 555-0334",
            "rescue@cicatrescue.org",
            "cicatrescue.org",
            "308 W. Kirby Ave, Champaign, IL",
            "Rescues and rehomes stray and feral cats. Runs TNR (Trap-Neuter-Return) programs.",
            "Tue–Sun 10am–4pm",
            false
        ));

        list.add(new Organization(
            "Illinois Raptor Center",
            "Wildlife Rehabilitation",
            "(217) 555-0417",
            "raptors@illinoisraptorcenter.org",
            "illinoisraptorcenter.org",
            "7419 N. Prospect Rd, Chillicothe, IL",
            "Rescues and rehabilitates birds of prey including hawks, owls, eagles, and falcons.",
            "Daily 9am–5pm",
            true
        ));

        return list;
    }
}
