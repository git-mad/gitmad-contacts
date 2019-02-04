package edu.gatech.gtorg.gitmad.contacts.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String firstName;

    private String lastName;

    private List<Attribute> attributes;

    public Contact() {
        this.id = UUID.randomUUID().toString();
        attributes = new ArrayList<>();
    }

    public Contact(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Contact(String firstName, String lastName, List<Attribute> attributes) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
