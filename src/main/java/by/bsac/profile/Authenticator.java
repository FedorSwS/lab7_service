package by.bsac.profile;

public interface Authenticator {
    Person authenticateByUserName(String userName, String password);
    Person authenticateByUserEmail(String email, String password);
}
