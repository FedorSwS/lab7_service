package by.bsac.profile;

public class SimpleAuthenticatorImpl implements Authenticator {
    private final PersonDAO personDAO = new PersonDAO();

    @Override
    public Person authenticateByUserName(String userName, String password) {
        if (userName == null || password == null) return null;
        Person person = personDAO.findByName(userName);
        if (person != null && person.getPassword().equals(password)) {
            return person;
        }
        return null;
    }

    @Override
    public Person authenticateByUserEmail(String email, String password) {
        if (email == null || password == null) return null;
        Person person = personDAO.findByEmail(email);
        if (person != null && person.getPassword().equals(password)) {
            return person;
        }
        return null;
    }
}
