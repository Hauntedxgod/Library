package ru.maxima.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.maxima.model.LibraryBook;
import ru.maxima.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<LibraryBook> {


    @Override
    public LibraryBook mapRow(ResultSet rs, int rowNum) throws SQLException {
        LibraryBook libraryBook = new LibraryBook();

        libraryBook.setId(rs.getLong("id"));
        libraryBook.setNameOfBook(rs.getString("NameOfBook"));
        libraryBook.setAuthorName(rs.getString("AuthorName"));
        libraryBook.setYearOfCreation(rs.getInt("YearOfCreation"));
        libraryBook.setOwnerId(Long.valueOf(rs.getInt("owner")));
        return libraryBook;
    }
}
