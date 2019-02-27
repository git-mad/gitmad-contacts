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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.gtorg.gitmad.contacts.ContactGenerator;
import edu.gatech.gtorg.gitmad.contacts.CustomOnClick;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.adapters.ContactAdapter;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;


public class ContactListFragment extends Fragment {

    private List<Contact> contacts = new ArrayList<>();
    private ContactAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ContactAdapter(contacts, new CustomOnClick() {
            @Override
            public void onItemClick(Object o) {
                Toast.makeText(getContext(), ((Contact) o).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        loadContactsFromDatabase();

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, AddContactFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void loadContactsFromDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts.addAll(AppDatabase.getDatabase(getContext()).contactDao().getAll());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }
}
