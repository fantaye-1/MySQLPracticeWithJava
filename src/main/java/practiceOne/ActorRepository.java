package practiceOne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Read-only queries against the sakila {@code actor} table. */
public class ActorRepository {

    private final Connection connection;

    public ActorRepository(Connection connection) {
        this.connection = connection;
    }

    public int countActors() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM actor")) {
            rs.next();
            return rs.getInt(1);
        }
    }

    /** Returns up to {@code limit} actors as "FIRST LAST", ordered by id. */
    public List<String> firstActors(int limit) throws SQLException {
        if (limit < 0) {
            throw new IllegalArgumentException("limit must not be negative");
        }
        String sql = "SELECT first_name, last_name FROM actor ORDER BY actor_id LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> names = new ArrayList<>();
                while (rs.next()) {
                    names.add(rs.getString("first_name") + " " + rs.getString("last_name"));
                }
                return names;
            }
        }
    }
}
