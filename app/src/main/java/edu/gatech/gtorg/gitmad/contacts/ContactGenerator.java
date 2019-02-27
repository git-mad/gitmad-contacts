package edu.gatech.gtorg.gitmad.contacts;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.gtorg.gitmad.contacts.models.Attribute;
import edu.gatech.gtorg.gitmad.contacts.models.Contact;

public class ContactGenerator {
    public static List<Contact> generateContacts(int numContacts) {
        List<Contact> toReturn = new ArrayList<>();
        for (int i = 0; i < numContacts; i++) {
            Contact contact = new Contact();

            contact.setFirstName("FirstName" + i);
            contact.setLastName("LastName" + i);
            contact.setAttributes(generateAttributes(5));

            toReturn.add(contact);
        }
        return toReturn;
    }

    public static List<Attribute> generateAttributes(int numAttributes) {
        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < numAttributes; i++) {
            Attribute attribute = new Attribute();

            attribute.setKey("Key" + i);
            attribute.setValue("Value" + i);

            attributes.add(attribute);
        }
        return attributes;
    }
}
