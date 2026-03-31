package edu.illinois.cs.cs124.ay2026.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder> {

    private final List<Organization> organizations;

    public OrganizationAdapter(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_organization, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Organization org = organizations.get(position);

        holder.name.setText(org.name);
        holder.type.setText(org.type);
        holder.description.setText(org.description);
        holder.phone.setText("Phone: " + org.phone);
        holder.hours.setText("Hours: " + org.hours);

        if (org.emergencyContact) {
            holder.emergencyBadge.setVisibility(View.VISIBLE);
        } else {
            holder.emergencyBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, description, phone, hours, emergencyBadge;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.org_name);
            type = view.findViewById(R.id.org_type);
            description = view.findViewById(R.id.org_description);
            phone = view.findViewById(R.id.org_phone);
            hours = view.findViewById(R.id.org_hours);
            emergencyBadge = view.findViewById(R.id.org_emergency_badge);
        }
    }
}
