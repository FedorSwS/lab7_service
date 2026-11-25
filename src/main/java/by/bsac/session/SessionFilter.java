package by.bsac.session;

import by.bsac.profile.ProfileTools;
import by.bsac.request.RequestTools;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class SessionFilter implements Filter {

    private final List<String> ignoredUrls = Arrays.asList(
            "/login",
            "/login.jsp",
            "/error-login.jsp",
            "/blocked.jsp"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private boolean isIgnoredUrl(String requestUri, String contextPath) {
        String path = requestUri.substring(contextPath.length());
        for (String ignored : ignoredUrls) {
            if (path.equals(ignored) || path.startsWith("/css/")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Устанавливаем кодировку UTF-8 для всех запросов и ответов
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        if (req.getSession().getAttribute("failedLoginAttemptsCount") == null) {
            req.getSession().setAttribute("failedLoginAttemptsCount", 0);
        }

        String requestUri = req.getRequestURI();
        boolean shouldBeIgnored = isIgnoredUrl(requestUri, req.getContextPath());

        // Если пользователь заблокирован (3+ неудачных попыток) — всегда перенаправляем на blocked.jsp
        if (RequestTools.isBlocked(req, res)) {
            RequestTools.redirectToBlockedPage(req, res);
        } else if (!shouldBeIgnored && !ProfileTools.isLoggedIn(req)) {
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        } else {
            // Обновляем отображаемое время работы в сессии для авторизованного пользователя
            if (ProfileTools.isLoggedIn(req)) {
                req.setAttribute("workTime", ProfileTools.getSessionWorkTime(req.getSession(false)));
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
