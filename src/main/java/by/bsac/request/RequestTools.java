package by.bsac.request;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestTools {

    public static final int MAX_FAILED_ATTEMPTS = 3;

    public static int getFailedAttempts(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Object val = session.getAttribute("failedLoginAttemptsCount");
        if (val == null) {
            session.setAttribute("failedLoginAttemptsCount", 0);
            return 0;
        }
        return Integer.parseInt(val.toString());
    }

    public static void addLoginAttemptToSession(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession(true);
        int attempts = getFailedAttempts(req) + 1;
        session.setAttribute("failedLoginAttemptsCount", attempts);

        // Фиксируем дату и время блокировки ровно в момент достижения 3-й неудачной попытки
        if (attempts >= MAX_FAILED_ATTEMPTS
                && (session.getAttribute("blockedDate") == null || session.getAttribute("blockedTime") == null)) {
            session.setAttribute("blockedDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            session.setAttribute("blockedTime", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }
    }

    public static boolean isBlocked(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        int attempts = getFailedAttempts(req);
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            HttpSession session = req.getSession(true);
            if (session.getAttribute("blockedDate") == null || session.getAttribute("blockedTime") == null) {
                session.setAttribute("blockedDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                session.setAttribute("blockedTime", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
            return true;
        }
        return false;
    }

    public static void redirectToBlockedPage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.getRequestDispatcher("/blocked.jsp").forward(req, resp);
    }
}
