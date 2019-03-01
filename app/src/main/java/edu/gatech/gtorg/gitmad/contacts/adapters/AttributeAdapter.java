package edu.gatech.gtorg.gitmad.contacts.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
                Intent intent;
                switch (key) {
                    case "Email":
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.putExtra(Intent.EXTRA_EMAIL, value);
                        break;
                    case "Phone":
                        intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + value));
                        break;
                    case "Address":
                        intent = new Intent(Intent.ACTION_VIEW);
                        try {
                            intent.setData(Uri.parse("geo:0,0?q=" + URLEncoder.encode(value, "UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            Log.e("AttributeAdapter", "Address Encoding", e);
                            return;
                        }
                        break;
                    case "Facebook":
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.facebook.com/" + value));
                        break;
                    case "Instagram":
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.instagram.com/" + value));
                        break;
                    case "Twitter":
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.twitter.com/" + value));
                        break;
                    case "Snapchat":
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.snapchat.com/" + value));
                        break;
                    default:
                        // Unrecognized key, don't do anything
                        return;
                }

                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}