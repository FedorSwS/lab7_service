package by.bsac.profile;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProfileTools {
    public static final String SESSION_LOGGEDIN_ATTRIBUTE_NAME = "loggedInUser";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_AUTH_TYPE = "authType";
    public static final String SESSION_LOGIN_DATE = "loginDate";
    public static final String SESSION_LOGIN_INSTANT = "loginInstant";
    public static final String PERSON_IS_ADMIN = "isAdmin";
    public static final String ALL_PERSONS_ATTRIBUTE_NAME = "allPersons";

    public static boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute(SESSION_LOGGEDIN_ATTRIBUTE_NAME) != null;
    }

    public static boolean isAdmin(Person person) {
        return person != null && "ADMIN".equalsIgnoreCase(person.getRole());
    }

    public static String generateLoginDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public static String getSessionWorkTime(HttpSession session) {
        if (session == null) return "0 сек.";
        Object obj = session.getAttribute(SESSION_LOGIN_INSTANT);
        if (!(obj instanceof Instant)) return "0 сек.";
        Duration d = Duration.between((Instant) obj, Instant.now());
        long mins = d.toMinutes();
        long secs = d.getSeconds() % 60;
        return String.format("%d мин. %d сек.", mins, secs);
    }
}
