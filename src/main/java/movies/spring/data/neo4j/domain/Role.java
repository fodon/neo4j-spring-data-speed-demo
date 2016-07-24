package movies.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.*;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator = JSOGGenerator.class)
@RelationshipEntity(type = "ACTED_IN")
public class Role {

    @GraphId
    Long id;
    private Collection<String> roles;
    @StartNode
    private Prof prof;
    @EndNode
    private Movie movie;

    public Role() {
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public Prof getProf() {
        return prof;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void setProf(Prof person) {
        this.prof = person;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
