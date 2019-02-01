package edu.gatech.gtorg.gitmad.contacts.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import edu.gatech.gtorg.gitmad.contacts.R;
import edu.gatech.gtorg.gitmad.contacts.fragments.ContactListFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment.newInstance())
                .commit();
    }
}
