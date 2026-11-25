package by.bsac.profile;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private static final List<Person> PERSONS = new ArrayList<>();

    static {
        PERSONS.add(new Person("admin", "admin@bsac.by", "admin123", "ADMIN"));
        PERSONS.add(new Person("student", "student@bsac.by", "student123", "USER"));
        PERSONS.add(new Person("moderator", "mod@bsac.by", "mod123", "MODERATOR"));
    }

    public List<Person> getAll() {
        return new ArrayList<>(PERSONS);
    }

    public Person findByName(String name) {
        if (name == null) return null;
        for (Person p : PERSONS) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                return p;
            }
        }
        return null;
    }

    public Person findByEmail(String email) {
        if (email == null) return null;
        for (Person p : PERSONS) {
            if (p.getEmail().equalsIgnoreCase(email.trim())) {
                return p;
            }
        }
        return null;
    }

    public void save(Person person) {
        // Объект уже хранится в списке PERSONS по ссылке
    }
}
