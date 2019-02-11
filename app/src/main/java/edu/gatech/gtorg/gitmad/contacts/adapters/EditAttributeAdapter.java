package edu.gatech.gtorg.gitmad.contacts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.models.Attribute;

public class EditAttributeAdapter extends RecyclerView.Adapter<EditAttributeAdapter.ViewHolder> {

    private static final String[] keys = new String[]{"Email", "Phone", "Address", "Facebook", "Instagram", "Twitter", "Snapchat"};
    private List<Attribute> attributes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View container;
        public EditText etKey;
        public EditText etValue;
        private ImageView ivDelete;

        ViewHolder(View container, EditText etKey, EditText etValue, ImageView ivDelete) {
            super(container);
            this.container = container;
            this.etKey = etKey;
            this.etValue = etValue;
            this.ivDelete = ivDelete;
        }
    }

    public EditAttributeAdapter(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @NonNull
    @Override
    public EditAttributeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit_attribute, parent, false);

        // Sets etKey to autocomplete with strings in keys array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_dropdown_item_1line, keys);
        AutoCompleteTextView textView = v.findViewById(R.id.etKey);
        textView.setAdapter(adapter);

        return new ViewHolder(v,
                (EditText) v.findViewById(R.id.etKey),
                (EditText) v.findViewById(R.id.etValue),
                (ImageView) v.findViewById(R.id.ivDelete));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.container.setTag(attributes.get(position));
        holder.etKey.setText(attributes.get(position).getKey());
        holder.etValue.setText(attributes.get(position).getValue());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attributes.remove(holder.container.getTag());
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
