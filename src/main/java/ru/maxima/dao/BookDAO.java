package ru.maxima.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maxima.model.LibraryBook;
import ru.maxima.model.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LibraryBook> allBook(){
        List<LibraryBook> book = jdbcTemplate.query("select * from book" , new BookMapper());
        return book;
    }

    public LibraryBook bookOfId(Long id){
        return jdbcTemplate.query("select * from Book where id = ?" , new Object[]{id} ,
                new BookMapper()).stream().findAny().orElse(null);
    }

    public List<LibraryBook> getOwnerId(Long ownerId){
        return jdbcTemplate.query("select * from Book where owner = ?" , new Object[]{ownerId} ,
                new BookMapper());
    }

    public void saveBook(LibraryBook libraryBook){
        jdbcTemplate.update("insert into Book (NameOfbook , AuthorName , YearOfCreation) values (? , ? , ?)" ,
                libraryBook.getNameOfBook() , libraryBook.getAuthorName() , libraryBook.getYearOfCreation());
    }
    public void updateBook(Long id , LibraryBook editedBook){
        jdbcTemplate.update("update book set NameOfbook = ? , AuthorName = ? , YearOfCreation = ?  where id = ?" ,
                editedBook.getNameOfBook() , editedBook.getAuthorName(), editedBook.getYearOfCreation(), id);
    }
    public void addOwner(Long id , Long ownerId){
        jdbcTemplate.update("update book set owner = ? where id = ? ", ownerId , id);
    }
    public void deleteOfPersonBook(Long id){
        jdbcTemplate.update("update book set owner = null where id = ? ", id);
    }
    public void deleteOfBook(Long id){
        jdbcTemplate.update("delete from Book where id = ? " , id);
    }


}
