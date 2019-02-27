package edu.gatech.gtorg.gitmad.contacts.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.models.Attribute;

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.ViewHolder> {

    private List<Attribute> attributes;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View container;
        private TextView tvKey;
        private TextView tvValue;

        ViewHolder(View container, TextView tvKey, TextView tvValue) {
            super(container);
            this.container = container;
            this.tvKey = tvKey;
            this.tvValue = tvValue;
        }
    }

    public AttributeAdapter(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @NonNull
    @Override
    public AttributeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attribute, parent, false);
        return new ViewHolder(v, (TextView) v.findViewById(R.id.tvKey), (TextView) v.findViewById(R.id.tvValue));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvKey.setText(attributes.get(position).getKey() + ": ");
        holder.tvValue.setText(attributes.get(position).getValue());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = attributes.get(position).getKey();
                String value = attributes.get(position).getValue();
                Toast.makeText(v.getContext(), key + ": " + value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}