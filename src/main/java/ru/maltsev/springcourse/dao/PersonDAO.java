package ru.maltsev.springcourse.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maltsev.springcourse.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PersonDAO {

    List<Person> index();

    Person show(int id);

    Optional<Person> show(String email);

    void save(Person person);

    void update(int id, Person updatedPerson);

    void delete(int id);

}
