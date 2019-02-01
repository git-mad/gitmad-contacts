package edu.gatech.gtorg.gitmad.contacts.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

public class ViewContactFragment extends Fragment {
    private static final String ARG_CONTACT = "contact";

    private Contact contact;

    public ViewContactFragment() {
        // Required empty public constructor
    }

    public static ViewContactFragment newInstance(Contact contact) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.tvFirstName)).setText(contact.getFirstName());
        ((TextView) view.findViewById(R.id.tvLastName)).setText(contact.getLastName());
    }
}
