package edu.gatech.gtorg.gitmad.contacts.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Delete
    void delete(Contact contact);
}
