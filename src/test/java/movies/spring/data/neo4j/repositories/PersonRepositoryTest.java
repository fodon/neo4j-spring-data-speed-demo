package movies.spring.data.neo4j.repositories;

import hello.neo.domain.Person;
import hello.neo.repo.PersonRepository;
import java.util.ArrayList;
import java.util.Random;
import movies.spring.data.neo4j.MyNeo4jSpeedTestConfiguration;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
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

    static final int BATCH_SIZE = 10000;
    static final int BATCHES = 10;

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
        SessionFactory sessionFactory = new SessionFactory("hello.neo.domain");
        final Session session = sessionFactory.openSession();
        System.out.println("Before linking up with Neo4j...");
        long start = System.currentTimeMillis();
        long mark = start;
        for (int j = 0; j < BATCHES; j++) {
            ArrayList<Person> list = new ArrayList<>();
            for (int i = 0; i < BATCH_SIZE; i++) {
                Person greg = new Person(rand);
                list.add(greg);
            }
            session.save(list);
            long now = System.currentTimeMillis();
            System.out.format("%d : Time:%d\n", j, now - mark);
            mark = now;
        }

    }
}
