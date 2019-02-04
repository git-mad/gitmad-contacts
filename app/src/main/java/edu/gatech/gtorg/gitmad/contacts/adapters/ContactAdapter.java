package edu.gatech.gtorg.gitmad.contacts.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.gatech.gtorg.gitmad.contacts.OnClick;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;
    private OnClick onClick;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View container;
        private ImageView imageView;
        private TextView textView;

        ViewHolder(View container, ImageView imageView, TextView textView) {
            super(container);
            this.container = container;
            this.imageView = imageView;
            this.textView = textView;
        }
    }

    public ContactAdapter(List<Contact> contacts, OnClick onClick) {
        this.contacts = contacts;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(v,
                (ImageView) v.findViewById(R.id.ivContact),
                (TextView) v.findViewById(R.id.tvName));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textView.setText(contacts.get(position).getName());

        Bitmap bmp = BitmapFactory.decodeByteArray(contacts.get(position).getPicture(), 0, contacts.get(position).getPicture().length);
        holder.imageView.setImageBitmap(bmp);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(contacts.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}