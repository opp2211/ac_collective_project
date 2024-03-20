package ru.maltsev.springcourse.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maltsev.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonJdbcDao implements PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @SneakyThrows
    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
    }

    @SneakyThrows
    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?",
                        new BeanPropertyRowMapper<>(Person.class), email).stream().findAny();
    }

    @SneakyThrows
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    @SneakyThrows
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(),
                id);
    }

    @SneakyThrows
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
