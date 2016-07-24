package hello.neo.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.neo4j.ogm.annotation.*;

@NodeEntity
public class Person {

    @GraphId
    private Long id;

    private String name;
    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means
     * when querying to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "TEAMMATE", direction = Relationship.UNDIRECTED)
    public Set<Person> teammates;

    @Relationship(type = "HAS", direction = Relationship.OUTGOING)
    public Set<Car> cars;

    /**
     * Neo4j requires a no-arg constructor much like JPA
     */
    private Person() {
    }

    public Person(Random rand) {
        this.name = Long.toHexString(rand.nextLong());
        this.cars = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            this.cars.add(new Car(rand));
        }
    }

    public Person(String name) {
        this.name = name;
    }

    public void hasCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
    }

    public void worksWith(Person person) {
        if (teammates == null) {
            teammates = new HashSet<>();
        }
        teammates.add(person);
    }

    @Override
    public String toString() {
        return this.name + "'s teammates => "
                + Optional.ofNullable(this.teammates)
                .orElse(Collections.emptySet())
                .stream()
                .map(person -> person.getName())
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Person> getTeammates() {
        return teammates;
    }

    public void setTeammates(Set<Person> teammates) {
        this.teammates = teammates;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
