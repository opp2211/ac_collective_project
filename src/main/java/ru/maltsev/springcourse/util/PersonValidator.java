package ru.maltsev.springcourse.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maltsev.springcourse.dao.PersonDAO;
import ru.maltsev.springcourse.models.Person;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        Optional<Person> personFromDbByEmail = personDAO.show(person.getEmail());
        if (personFromDbByEmail.isPresent() && personFromDbByEmail.get().getId() != person.getId()) {
            errors.rejectValue("email", "", "This email is already taken");
        }
    }
}
