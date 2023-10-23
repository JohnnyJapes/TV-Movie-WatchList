package model;

import model.content.ContentBase;

import java.util.ArrayList;

public class ContentList {

    private ArrayList<ListEntry> listEntries; // holds list entries which link to contentBase
    private String listName;
    private int id;

    public ContentList(){
        listName = "Watching";
        listEntries = new ArrayList<ListEntry>();
    }
    public ContentList(ArrayList<ListEntry> listEntries, String listName) {
        this.listEntries = listEntries;
        this.listName = listName;
    }

    /**
     * Gets listEntries.
     *
     * @return java.util.ArrayList<model.ListEntry>, value of listEntries
     */
    public ArrayList<ListEntry> getListEntries() {
        return listEntries;
    }

    /**
     * Method to set listEntries.
     *
     * @param listEntries java.util.ArrayList<model.ListEntry> - listEntries
     */
    public void setListEntries(ArrayList<ListEntry> listEntries) {
        this.listEntries = listEntries;
    }

    /**
     * Gets listName.
     *
     * @return java.lang.String, value of listName
     */
    public String getListName() {
        return listName;
    }

    /**
     * Method to set listName.
     *
     * @param listName java.lang.String - listName
     */
    public void setListName(String listName) {
        this.listName = listName;
    }
}
