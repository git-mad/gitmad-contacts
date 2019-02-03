package edu.gatech.gtorg.gitmad.contacts.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.navigation.Navigation;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

public class AddContactFragment extends Fragment {

    private EditText etFirstName;
    private EditText etLastName;

    public AddContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        view.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String firstName = etFirstName.getText().toString();
                        String lastName = etLastName.getText().toString();

                        Contact toAdd = new Contact(firstName, lastName);

                        AppDatabase.getDatabase(v.getContext()).contactDao().insert(toAdd);
                    }
                }).start();

                Navigation.findNavController(v).navigate(R.id.action_addContactFragment_to_contactListFragment);
            }
        });
    }
}
