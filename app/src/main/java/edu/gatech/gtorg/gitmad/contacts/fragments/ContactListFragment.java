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

import edu.gatech.gtorg.gitmad.contacts.OnClick;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.adapters.ContactsAdapter;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;


public class ContactListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContactsAdapter adapter;

    private SearchView searchView;
    private FloatingActionButton fab;

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
        fab = view.findViewById(R.id.fab);

        contacts = new ArrayList<>();
        loadData();

        adapter = new ContactsAdapter(contacts, new OnClick() {
            @Override
            public void onClick(Object o) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", (Contact) o);

                getFragmentManager().beginTransaction()
                        .replace(R.id.mainFrameLayout, ViewContactFragment.newInstance((Contact) o))
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
