package repository;

import production.details.Actor;

import java.util.Comparator;
import java.util.List;

public class ActorRepository {
    private final List<Actor> actors;

    public ActorRepository(List<Actor> actors) {
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

        for (Actor actor : actors) {
            actor.displayActorInfo();
        }
    }

    public Actor searchByName(String name) {
        for (Actor a : actors) {
            if (a.getName().contains(name)) {
                return a;
            }
        }

        return null;
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void removeActor(String name) {
        actors.remove(searchByName(name));
    }

    public boolean isActor(String value) {
        for (Actor actor : actors) {
           if (actor.getName().equals(value))
               return true;
        }
        return false;
    }
}
