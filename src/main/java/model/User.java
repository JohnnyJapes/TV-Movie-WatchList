package model;

public class User {
    String userName;
    //other variables to come once view is created
    //last window size, etc.

    public User(String userName) {
        this.userName = userName;
    }

    /**
     * Gets userName.
     *
     * @return java.lang.String, value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method to set userName.
     *
     * @param userName java.lang.String - userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
