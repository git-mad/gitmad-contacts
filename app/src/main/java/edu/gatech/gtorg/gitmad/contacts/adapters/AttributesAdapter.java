package edu.gatech.gtorg.gitmad.contacts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.jar.Attributes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.models.Attribute;

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private List<Attribute> attributes;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKey;
        private TextView tvValue;

        ViewHolder(View container, TextView tvKey, TextView tvValue) {
            super(container);
            this.tvKey = tvKey;
            this.tvValue = tvValue;
        }
    }

    public AttributesAdapter(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @NonNull
    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attribute, parent, false);
        return new ViewHolder(v, (TextView) v.findViewById(R.id.tvKey), (TextView) v.findViewById(R.id.tvValue));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvKey.setText(attributes.get(position).getKey() + ": ");
        holder.tvValue.setText(attributes.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
