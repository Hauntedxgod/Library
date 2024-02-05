package ru.maxima.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.maxima.model.LibraryBook;
import ru.maxima.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        LibraryBook libraryBook= new LibraryBook();

        person.setId(rs.getLong("id"));
        person.setName(rs.getString("name"));
        person.setAge(rs.getInt("age"));
        person.setOwnerId(Long.valueOf(rs.getInt("owner")));
        return person;
    }
}
