package edu.gatech.gtorg.gitmad.contacts.database;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import androidx.room.TypeConverter;
import edu.gatech.gtorg.gitmad.contacts.models.Attribute;

class DataConverter {
    @TypeConverter
    public String fromAttributes(List<Attribute> attributes) {
        return (new Gson()).toJson(attributes);
    }

    @TypeConverter
    public List<Attribute> toAttributes(String json) {
        return (new Gson()).fromJson(json,
                new TypeToken<List<Attribute>>() {
                }.getType()
        );
    }

    @TypeConverter
    public String fromPicture(Uri picture) {
        return (new Gson()).toJson(picture);
    }

    @TypeConverter
    public Uri toPicture(String json) {
        return (new Gson()).fromJson(json, Uri.class);
    }
}
