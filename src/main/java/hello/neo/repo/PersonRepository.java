package hello.neo.repo;

import hello.neo.domain.Person;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

    Person findByName(String name);

    List<Person> findByTeammatesName(String name);

}
