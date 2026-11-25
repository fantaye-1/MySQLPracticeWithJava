package practiceOne;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Runs the queries against an in-memory H2 database in MySQL mode. */
class ActorRepositoryTest {

    private Connection connection;
    private ActorRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:sakila;MODE=MySQL", "sa", "");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE actor (actor_id INT PRIMARY KEY, "
                    + "first_name VARCHAR(45), last_name VARCHAR(45))");
            stmt.execute("INSERT INTO actor VALUES "
                    + "(3, 'ED', 'CHASE'), (1, 'PENELOPE', 'GUINESS'), (2, 'NICK', 'WAHLBERG')");
        }
        repository = new ActorRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE actor");
        }
        connection.close();
    }

    @Test
    void countsActors() throws SQLException {
        assertEquals(3, repository.countActors());
    }

    @Test
    void returnsActorsOrderedByIdUpToLimit() throws SQLException {
        assertEquals(List.of("PENELOPE GUINESS", "NICK WAHLBERG"), repository.firstActors(2));
    }

    @Test
    void limitLargerThanTableReturnsAll() throws SQLException {
        assertEquals(3, repository.firstActors(10).size());
    }

    @Test
    void negativeLimitIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> repository.firstActors(-1));
    }
}
