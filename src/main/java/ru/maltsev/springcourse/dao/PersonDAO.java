package ru.maltsev.springcourse.dao;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.maltsev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/sc_people";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "iamroot";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        Statement statement = connection.createStatement();
        String SQL = "SELECT * FROM person";
        ResultSet resultSet = statement.executeQuery(SQL);

        while (resultSet.next()) {
            Person person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

            people.add(person);
        }

        return people;
    }

    @SneakyThrows
    public Person show(int id) {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Person(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("email"));
    }

    @SneakyThrows
    public void save(Person person) {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person VALUES(1, ?, ?, ?)");

        preparedStatement.setString(1, person.getName());
        preparedStatement.setInt(2, person.getAge());
        preparedStatement.setString(3, person.getEmail());

        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public void update(int id, Person updatedPerson) {
        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id=?");
        preparedStatement.setString(1, updatedPerson.getName());
        preparedStatement.setInt(2, updatedPerson.getAge());
        preparedStatement.setString(3, updatedPerson.getEmail());
        preparedStatement.setInt(4, id);

        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public void delete(int id) {
        PreparedStatement preparedStatement =
                connection.prepareStatement("DELETE FROM person WHERE id=?");
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }
}
