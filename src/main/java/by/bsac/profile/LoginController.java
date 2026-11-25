package by.bsac.profile;

import by.bsac.request.RequestTools;
import java.io.IOException;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final PersonDAO personDAO = new PersonDAO();

    private PersonDAO getPersonDAO() {
        return personDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        login(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String authTypeParam = request.getParameter("authType");
        if (authTypeParam == null) {
            authTypeParam = "name";
        }

        Authenticator authenticator = new SimpleAuthenticatorImpl();
        String password = request.getParameter("psw");
        String authValue = request.getParameter("loginValue");

        Person person;
        if ("email".equals(authTypeParam)) {
            person = authenticator.authenticateByUserEmail(authValue, password);
        } else {
            person = authenticator.authenticateByUserName(authValue, password);
        }

        if (person != null) {
            HttpSession session = request.getSession(true);
            String loginDate = ProfileTools.generateLoginDate();
            person.setLoginDate(loginDate);
            getPersonDAO().save(person);

            // Сохраняем параметры авторизации, дату входа и момент старта работы в сессии
            session.setAttribute(ProfileTools.SESSION_LOGGEDIN_ATTRIBUTE_NAME, person.getName());
            session.setAttribute(ProfileTools.SESSION_USER_ROLE, person.getRole());
            session.setAttribute(ProfileTools.SESSION_AUTH_TYPE, authTypeParam);
            session.setAttribute(ProfileTools.SESSION_LOGIN_DATE, loginDate);
            session.setAttribute(ProfileTools.SESSION_LOGIN_INSTANT, Instant.now());

            if (ProfileTools.isAdmin(person)) {
                session.setAttribute(ProfileTools.PERSON_IS_ADMIN, true);
                session.setAttribute(ProfileTools.ALL_PERSONS_ATTRIBUTE_NAME, getPersonDAO().getAll());
            } else {
                session.setAttribute(ProfileTools.PERSON_IS_ADMIN, false);
            }

            session.setAttribute("failedLoginAttemptsCount", 0);
            session.removeAttribute("blockedDate");
            session.removeAttribute("blockedTime");

            response.sendRedirect(request.getContextPath() + "/home.jsp");
        } else {
            RequestTools.addLoginAttemptToSession(request, response);
            if (RequestTools.isBlocked(request, response)) {
                RequestTools.redirectToBlockedPage(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/error-login.jsp");
            }
        }
    }
}
