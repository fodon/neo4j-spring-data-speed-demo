package movies.spring.data.neo4j.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import movies.spring.data.neo4j.MyNeo4jTestConfiguration;
import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Prof;
import movies.spring.data.neo4j.domain.Role;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
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
@SpringApplicationConfiguration(classes = MyNeo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private ProfRepository profRepo;

    public MovieRepositoryTest() {
    }

    @Before
    public void initialize() {
        System.out.println("seeding embedded database");
        Movie matrix = new Movie();
        matrix.setTitle("The Matrix");
        matrix.setReleased(1999);

        movieRepo.save(matrix);

        Prof keanu = new Prof();
        keanu.setName("Keanu Reeves");

        profRepo.save(keanu);

        Role neo = new Role();
        neo.setMovie(matrix);
        neo.setProf(keanu);
        Collection<String> roleNames = new HashSet();
        roleNames.add("Neo");
        neo.setRoles(roleNames);

        List<Role> roles = new ArrayList();
        roles.add(neo);

        matrix.setRoles(roles);

        movieRepo.save(matrix);
    }

    /**
     * Test of findByTitle method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void testFindByTitle() {

        System.out.println("findByTitle");
        String title = "The Matrix";
        Movie result = movieRepo.findByTitle(title);
        assertNotNull(result);
        assertEquals(1999, result.getReleased());
    }

    /**
     * Test of findByTitleContaining method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void testFindByTitleContaining() {
        System.out.println("findByTitleContaining");
        String title = "Matrix";
        Collection<Movie> result = movieRepo.findByTitleContaining(title);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Test of graph method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void testGraph() {
        System.out.println("graph");
        List<Map<String, Object>> graph = movieRepo.graph(5);

        assertEquals(1, graph.size());

        Map<String, Object> map = graph.get(0);

        assertEquals(2, map.size());

        String[] cast = (String[]) map.get("cast");
        String movie = (String) map.get("movie");

        assertEquals("The Matrix", movie);
        assertEquals("Keanu Reeves", cast[0]);
    }
}
