package practiceOne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to the MySQL sakila sample database and prints a few actors.
 * Connection settings come from environment variables, see {@link DbConfig}.
 */
public class MySQLPracticeOne {

    public static void main(String[] args) {
        DbConfig config;
        try {
            config = DbConfig.fromEnvironment(System.getenv());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            System.exit(2);
            return;
        }

        // try-with-resources closes the connection even if a query fails.
        try (Connection connection =
                     DriverManager.getConnection(config.url(), config.user(), config.password())) {
            System.out.println("Connected to " + config.url());

            ActorRepository actors = new ActorRepository(connection);
            System.out.println("Actors in database: " + actors.countActors());
            System.out.println("First 5 actors:");
            for (String name : actors.firstActors(5)) {
                System.out.println("  " + name);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            System.exit(1);
        }
    }
}
