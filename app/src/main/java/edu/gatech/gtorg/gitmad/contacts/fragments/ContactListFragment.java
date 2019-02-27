package edu.gatech.gtorg.gitmad.contacts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.gtorg.gitmad.contacts.CustomOnClick;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.adapters.ContactAdapter;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;


public class ContactListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
    }

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }
}
