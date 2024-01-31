package ru.maxima.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maxima.model.LibraryBook;

import java.awt.print.Book;
import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LibraryBook> allBook(){
        List<LibraryBook> book = jdbcTemplate.query("select * from Book " ,
                new BookMapper());
        return book;
    }

    public LibraryBook bookOfId(Long id){
        return jdbcTemplate.query("select * from Book where id = ?" , new Object[]{id} ,
                new BookMapper()).stream().findAny().orElse(null);
    }

    public void saveBook(LibraryBook libraryBook){
        jdbcTemplate.update("insert into Book (NameOfbook , AuthorName , YearOfCreation) values (? , ? , ?)" ,
                libraryBook.getNameOfBook() , libraryBook.getAuthorName() , libraryBook.getYearOfCreation());
    }
    public void updateBook(Long id , LibraryBook editedBook){
        jdbcTemplate.update("update book set NameOfbook = ? , AuthorName = ? , YearOfCreation = ?  where id = ?" ,
                editedBook.getNameOfBook() , editedBook.getAuthorName(), editedBook.getYearOfCreation(), id);
    }
    public void deleteOfBook(Long id){
        jdbcTemplate.update("delete from Book where id = ? " , id);
    }
}
