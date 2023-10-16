package model;

public interface TMDBcompatable {
    /**
     * Method to retrieve the details  from a TMDB page and apply them to the current Object
     * @param tmdbID - int
     */
    public void getTMDBdetails(int tmdbID);

    public void searchTMDB(String query);
}
