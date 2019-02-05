package edu.gatech.gtorg.gitmad.contacts.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.adapters.EditAttributeAdapter;
import edu.gatech.gtorg.gitmad.contacts.database.AppDatabase;
import edu.gatech.gtorg.gitmad.contacts.models.Attribute;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

import static android.app.Activity.RESULT_OK;

public class AddContactFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText etFirstName;
    private EditText etLastName;
    private ImageView ivProfile;

    private RecyclerView rvEditAttributes;
    private EditAttributeAdapter editAttributesAdapter;
    private List<Attribute> attributes;

    private Uri tempUri;

    Contact contact;

    public AddContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        contact = new Contact();

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        ivProfile = view.findViewById(R.id.ivProfile);

        attributes = new ArrayList<>();
        editAttributesAdapter = new EditAttributeAdapter(attributes);
        rvEditAttributes = view.findViewById(R.id.rvAttributes);
        rvEditAttributes.setAdapter(editAttributesAdapter);
        rvEditAttributes.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.btnAddAttribute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attributes.add(new Attribute());
                editAttributesAdapter.notifyItemInserted(attributes.size() - 1);
            }
        });

        view.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String firstName = etFirstName.getText().toString();
                        String lastName = etLastName.getText().toString();

                        if (firstName.length() == 0) {
                            Toast.makeText(getContext(), "Missing first name!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (lastName.length() == 0) {
                            Toast.makeText(getContext(), "Missing last name!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<Attribute> completedAttributes = new ArrayList<>();
                        for (int i = 0; i < rvEditAttributes.getChildCount(); i++) {
                            EditAttributeAdapter.ViewHolder holder = (EditAttributeAdapter.ViewHolder) rvEditAttributes.findViewHolderForAdapterPosition(i);
                            Attribute attribute = new Attribute(holder.etKey.getText().toString(), holder.etValue.getText().toString());
                            completedAttributes.add(attribute);
                        }

                        contact.setFirstName(firstName);
                        contact.setLastName(lastName);
                        contact.setAttributes(completedAttributes);

                        AppDatabase.getDatabase(v.getContext()).contactDao().insert(contact);
                    }
                }).start();

                getFragmentManager().popBackStack();
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

    static AddContactFragment newInstance() {
        return new AddContactFragment();
    }
}
