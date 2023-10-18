package model;

public interface TMDBcompatible {
    /**
     * Method to retrieve the details  from a TMDB page and apply them to the current Object
     * @param tmdbID - int
     */
    public void getTMDBdetails(int tmdbID);

    /**
     * Search TMDB for query string. Type of search depends on object calling this method.
     * @param query
     */
    public Object searchTMDB(String query);
}
