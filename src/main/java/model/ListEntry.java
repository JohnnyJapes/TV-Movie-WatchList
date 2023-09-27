package model;

import model.content.ContentBase;

public class ListEntry {

    private ContentBase entry; //actual piece of content for that entry
    private int listRank; //rank/order in the default list sorting

    public ListEntry(ContentBase entry, int listRank) {
        this.entry = entry;
        this.listRank = listRank;
    }

    /**
     * Gets entry.
     *
     * @return model.content.ContentBase, value of entry
     */
    public ContentBase getEntry() {
        return entry;
    }

    /**
     * Method to set entry.
     *
     * @param entry model.content.ContentBase - entry
     */
    public void setEntry(ContentBase entry) {
        this.entry = entry;
    }

    /**
     * Gets listRank.
     *
     * @return int, value of listRank
     */
    public int getListRank() {
        return listRank;
    }

    /**
     * Method to set listRank.
     *
     * @param listRank int - listRank
     */
    public void setListRank(int listRank) {
        this.listRank = listRank;
    }
}
