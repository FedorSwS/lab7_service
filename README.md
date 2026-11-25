# Лабораторная работа №7 (Вариант 4): Сессии, события, фильтры и блокировка после 3 неудачных попыток

## Что реализовано
- Авторизация по имени пользователя или Email (`LoginController`, `SimpleAuthenticatorImpl`).
- Сохранение параметров авторизации, даты входа и времени работы в `HttpSession`.
- Фильтр `SessionFilter`, защищающий закрытые страницы и отслеживающий статус блокировки.
- Блокировка пользователя после 3 неудачных попыток входа с сохранением даты и времени блокировки (`RequestTools`, `blocked.jsp`).
- Слушатель событий сессии `SessionListenerImpl` (`HttpSessionListener`, `HttpSessionAttributeListener`).
- Вариант №4 (`QuestionCookieServlet`): чтение текстового файла `WEB-INF/data/sample_text.txt`, извлечение всех вопросительных предложений и запись их в Cookie `question_sentences`.

## Развёртывание (Apache Tomcat 9 + Maven / IntelliJ IDEA / Eclipse)
1. Установите **JDK 11 или 17**, **Apache Maven 3.8+** и **Apache Tomcat 9.0** (Tomcat 9 использует пакет `javax.servlet.*`, как в методических указаниях).
2. Соберите WAR-архив командой в корне проекта:
   `mvn clean package`
3. Скопируйте файл `target/BSAC.war` в папку `<TOMCAT_HOME>/webapps/`.
4. Запустите Tomcat (`bin/startup.bat` на Windows или `bin/startup.sh` на Linux/macOS).
5. Откройте в браузере: `http://localhost:8080/BSAC/login.jsp`.
