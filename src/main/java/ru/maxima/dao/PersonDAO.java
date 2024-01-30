package ru.maxima.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maxima.model.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> allPeople(){
        List<Person> people = jdbcTemplate.query("select * from Human" , new PersonMapper());
        return people;
    }

    public Person Id(Long id ){
        return jdbcTemplate.query("select * from Human where id = ? " , new Object[]{id} ,
                new PersonMapper()).stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("insert into Human (name , age ) values (? , ? )",
                person.getName() , person.getAge());
    }
    public void update(Long id , Person editedPerson){
        jdbcTemplate.update("update Human set name = ? , age = ? where id = ?"
                , editedPerson.getName() , editedPerson.getAge() , id);
    }
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from Human where id = ? " , id );
    }

}