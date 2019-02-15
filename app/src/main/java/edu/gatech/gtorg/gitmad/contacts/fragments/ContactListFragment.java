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

    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    private SearchView searchView;

    private List<Contact> contacts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvContacts);
        searchView = view.findViewById(R.id.searchView);

        contacts = new ArrayList<>();
        loadData();

        adapter = new ContactAdapter(contacts, new CustomOnClick() {
            @Override
            public void onItemClick(Object o) {
                // Whenever an item is clicked, show the ViewContactFragment, and pass in the clicked Contact
                getFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, ViewContactFragment.newInstance((Contact) o))
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: Implement SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // We don't care about submit since we're filtering in onChange.
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // TODO: Filter
                return false;
            }
        });

        // fab stands for Floating Action Button
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On click of the FAB, show AddContactFragment
                getFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, AddContactFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts.addAll(AppDatabase.getDatabase(getContext()).contactDao().getAll());

                // Since we fetched the contacts on a background thread (to prevent the UI from hanging)
                // We have to switch back to the UI thread to make any UI changes
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
