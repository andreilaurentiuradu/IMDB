package production.details;

public class Rating {
    private String username;
    private Integer value;
    private String comment;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRating(Integer rating) {
        this.value = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public String toString() {
        return "your rating:" + value + ", comment:" + comment;
    }
}