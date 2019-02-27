package edu.gatech.gtorg.gitmad.contacts.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.gatech.gtorg.gitmad.contacts.ContactGenerator;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

import static android.app.Activity.RESULT_OK;

public class AddContactFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Contact contact;

    private ImageView ivProfile;
    private EditText etFirstName;
    private EditText etLastName;

    private Uri tempUri;

    public AddContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        contact = new Contact();
        ivProfile = view.findViewById(R.id.ivProfile);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        view.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();

                contact.setFirstName(firstName);
                contact.setLastName(lastName);
                contact.setAttributes(ContactGenerator.generateAttributes(5));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getDatabase(getContext()).contactDao().insert(contact);
                        getFragmentManager().popBackStack();
                    }
                }).start();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        // Create an intent to take an image
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Make sure this intent is available
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.e("AddContactFragment", "Camera", e);
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(), "edu.gatech.gtorg.gitmad.contacts", photoFile);
                tempUri = photoUri;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        return File.createTempFile(
                contact.getId(),
                ".jpg",
                getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ivProfile.setImageURI(tempUri);
            contact.setProfileUri(tempUri.toString());
        }
    }

    public static AddContactFragment newInstance() {
        return new AddContactFragment();
    }
}
