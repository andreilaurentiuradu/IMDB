package service;

import production.details.Actor;

import java.util.Comparator;
import java.util.List;

public class ActorService {
    private final List<Actor> actors;

    public ActorService(List<Actor> actors) {
       this.actors = actors;
    }

    public void printActorDetails(String sorted) {
        if (sorted.equals("Yes")) {
            actors.sort(new Comparator<Actor>() {
                @Override
                public int compare(Actor actor1, Actor actor2) {

                    return actor1.getName().compareTo(actor2.getName());
                }
            });
        }
        actors.forEach(System.out::println);
    }

    public void searchByName(String name) {
        for (Actor a : actors) {
            if (a.getName().contains(name)) {
                System.out.println(a);
            }
        }
    }
}
