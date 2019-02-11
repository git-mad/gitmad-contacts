package edu.gatech.gtorg.gitmad.contacts;

/**
 * This is so we can separate the onClick logic away from the adapter
 */
public interface CustomOnClick {
    void onItemClick(Object o);
}
