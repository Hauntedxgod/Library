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
        List<Person> people = jdbcTemplate.query("select * from human" , new PersonMapper());
        return people;
    }

    public Person ById (Long id){
        return jdbcTemplate.query("select * from human where id = ? " , new Object[]{id} ,
                new PersonMapper()).stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("insert into human (name , age ) values (? , ? )",
                person.getName() , person.getAge());
    }
    public void update(Long id , Person editedPerson){
        jdbcTemplate.update("update human set name = ? , age = ? where id = ?"
                , editedPerson.getName() , editedPerson.getAge() , id);
    }
    public void addOwner(Long id , Long ownerId){
        jdbcTemplate.update("update human set owner = ? where id = ? ",  ownerId , id);
    }
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from human where id = ? " , id );
    }

}
