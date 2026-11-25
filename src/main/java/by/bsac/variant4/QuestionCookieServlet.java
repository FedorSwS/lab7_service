package by.bsac.variant4;

import by.bsac.profile.ProfileTools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Индивидуальное задание (Вариант 4):
 * Записать в файл cookie все вопросительные предложения текста, которые хранятся в текстовом файле.
 */
@WebServlet("/extract-questions")
public class QuestionCookieServlet extends HttpServlet {

    public static final String COOKIE_NAME = "question_sentences";
    private static final String DATA_FILE_PATH = "/WEB-INF/data/sample_text.txt";

    private String readSourceText() throws IOException {
        InputStream is = getServletContext().getResourceAsStream(DATA_FILE_PATH);
        if (is == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("
");
            }
        }
        return sb.toString().trim();
    }

    private List<String> extractQuestions(String text) {
        List<String> questions = new ArrayList<>();
        // Выделяем предложения, оканчивающиеся на вопросительный знак '?'
        Pattern pattern = Pattern.compile("[^.!?\n]+\?");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (!sentence.isEmpty()) {
                questions.add(sentence);
            }
        }
        return questions;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sourceText = readSourceText();
        List<String> questions = extractQuestions(sourceText);

        // Объединяем найденные вопросительные предложения и кодируем для безопасной записи в Cookie (UTF-8)
        String joined = String.join(" | ", questions);
        String encoded = URLEncoder.encode(joined, StandardCharsets.UTF_8.name());

        Cookie cookie = new Cookie(COOKIE_NAME, encoded);
        cookie.setMaxAge(60 * 60); // 1 час
        cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
        resp.addCookie(cookie);

        req.setAttribute("sourceText", sourceText);
        req.setAttribute("questions", questions);
        req.setAttribute("savedCookieDecoded", joined);
        req.setAttribute("workTime", ProfileTools.getSessionWorkTime(req.getSession(false)));

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sourceText = readSourceText();
        req.setAttribute("sourceText", sourceText);

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    String decoded = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8.name());
                    req.setAttribute("savedCookieDecoded", decoded);
                }
            }
        }
        req.setAttribute("workTime", ProfileTools.getSessionWorkTime(req.getSession(false)));
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
