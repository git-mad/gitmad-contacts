package edu.gatech.gtorg.gitmad.contacts.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.adapters.AttributeAdapter;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

public class ViewContactFragment extends Fragment {
    private static final String ARG_CONTACT = "contact";

    private Contact contact;

    private RecyclerView rvAttributes;
    private AttributeAdapter attributeAdapter;

    public ViewContactFragment() {
        // Required empty public constructor
    }

    static ViewContactFragment newInstance(Contact contact) {
        ViewContactFragment fragment = new ViewContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = (Contact) getArguments().getSerializable(ARG_CONTACT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.tvFirstName)).setText(contact.getFirstName());
        ((TextView) view.findViewById(R.id.tvLastName)).setText(contact.getLastName());

        if (contact.getProfileUri() != null) {
            ((ImageView) view.findViewById(R.id.ivProfile)).setImageURI(Uri.parse(contact.getProfileUri()));
        }

        rvAttributes = view.findViewById(R.id.rvAttributes);
        attributeAdapter = new AttributeAdapter(contact.getAttributes());
        rvAttributes.setAdapter(attributeAdapter);
        rvAttributes.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.btnDelete).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase.getDatabase(v.getContext()).contactDao().delete(contact);
                            }
                        }).start();

                        getFragmentManager().popBackStack();
                    }
                }
        );
    }
}