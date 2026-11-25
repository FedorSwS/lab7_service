package by.bsac.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListenerImpl implements HttpSessionAttributeListener, HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("[SessionListener] Сессия создана: ID=" + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("[SessionListener] Сессия уничтожена: ID=" + se.getSession().getId());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent ev) {
        System.out.println("[SessionListener] add: " + ev.getName() + " = " + ev.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent ev) {
        System.out.println("[SessionListener] remove: " + ev.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent ev) {
        System.out.println("[SessionListener] replace: " + ev.getName() + " = " + ev.getValue());
    }
}
