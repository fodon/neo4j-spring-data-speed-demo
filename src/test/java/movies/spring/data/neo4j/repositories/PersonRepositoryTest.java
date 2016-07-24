package movies.spring.data.neo4j.repositories;

import hello.neo.domain.Person;
import hello.neo.repo.PersonRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import movies.spring.data.neo4j.MyNeo4jSpeedTestConfiguration;
import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Prof;
import movies.spring.data.neo4j.domain.Role;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author pdtyreus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyNeo4jSpeedTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    public PersonRepositoryTest() {
    }

    @Before
    public void initialize() {
        System.out.println("Not initialzing DB.");
    }

    /**
     * Test of findByTitle method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void speedTest() {
        Random rand = new Random(10);
        System.out.println("Before linking up with Neo4j...");
        long start = System.currentTimeMillis();
        long mark = start;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 100; i++) {
                Person greg = new Person(rand);
                personRepo.save(greg);
            }
            long now = System.currentTimeMillis();
            System.out.format("%d : Time:%d\n", j, now - mark);
            mark = now;
        }

    }
}
