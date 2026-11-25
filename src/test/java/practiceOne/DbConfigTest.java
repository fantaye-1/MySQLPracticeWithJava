package practiceOne;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DbConfigTest {

    @Test
    void usesDefaultsWhenOnlyPasswordIsSet() {
        DbConfig config = DbConfig.fromEnvironment(Map.of("MYSQL_PASSWORD", "secret"));

        assertEquals(DbConfig.DEFAULT_URL, config.url());
        assertEquals(DbConfig.DEFAULT_USER, config.user());
        assertEquals("secret", config.password());
    }

    @Test
    void readsAllValuesFromEnvironment() {
        DbConfig config = DbConfig.fromEnvironment(Map.of(
                "MYSQL_URL", "jdbc:mysql://db:3306/test",
                "MYSQL_USER", "app",
                "MYSQL_PASSWORD", "pw"));

        assertEquals("jdbc:mysql://db:3306/test", config.url());
        assertEquals("app", config.user());
        assertEquals("pw", config.password());
    }

    @Test
    void blankUrlAndUserFallBackToDefaults() {
        DbConfig config = DbConfig.fromEnvironment(Map.of(
                "MYSQL_URL", "  ", "MYSQL_USER", "", "MYSQL_PASSWORD", "pw"));

        assertEquals(DbConfig.DEFAULT_URL, config.url());
        assertEquals(DbConfig.DEFAULT_USER, config.user());
    }

    @Test
    void missingPasswordFailsWithHelpfulMessage() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> DbConfig.fromEnvironment(Map.of()));

        assertEquals(true, e.getMessage().contains("MYSQL_PASSWORD"));
    }

    @Test
    void toStringDoesNotLeakPassword() {
        DbConfig config = new DbConfig("jdbc:x", "u", "topsecret");

        assertFalse(config.toString().contains("topsecret"));
    }
}
