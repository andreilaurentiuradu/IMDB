package production.details;

import user.notifications.Observer;
import user.notifications.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Rating implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private String username;
    private Integer value;
    private String comment;

    public Integer getValue() {
        return value;
    }

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
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String notification) {
        for (Observer observer : observers) {
            observer.update(notification);
        }
    }
    @Override
    public String toString() {
        return "rating:" + value + ", comment:" + comment + " username:" + username;
    }


}