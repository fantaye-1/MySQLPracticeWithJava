package practiceOne;

import java.util.Map;

/**
 * Database connection settings, read from environment variables so that no
 * credentials live in the source code.
 *
 * <ul>
 *   <li>{@code MYSQL_URL} - JDBC URL (default {@value #DEFAULT_URL})</li>
 *   <li>{@code MYSQL_USER} - user name (default {@value #DEFAULT_USER})</li>
 *   <li>{@code MYSQL_PASSWORD} - password (required)</li>
 * </ul>
 */
public record DbConfig(String url, String user, String password) {

    static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/sakila";
    static final String DEFAULT_USER = "root";

    public static DbConfig fromEnvironment(Map<String, String> env) {
        String url = valueOrDefault(env.get("MYSQL_URL"), DEFAULT_URL);
        String user = valueOrDefault(env.get("MYSQL_USER"), DEFAULT_USER);
        String password = env.get("MYSQL_PASSWORD");
        if (password == null) {
            throw new IllegalStateException(
                    "MYSQL_PASSWORD is not set. Export it (see .env.example) before running.");
        }
        return new DbConfig(url, user, password);
    }

    private static String valueOrDefault(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value.trim();
    }

    /** Never print the password. */
    @Override
    public String toString() {
        return "DbConfig[url=" + url + ", user=" + user + ", password=****]";
    }
}
