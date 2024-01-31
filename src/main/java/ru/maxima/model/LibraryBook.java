package ru.maxima.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.maxima.dao.PersonDAO;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LibraryBook {

    private Long id;

    @NotEmpty(message = "The line with the name of the book must be filled in")
    @Size(min = 1 , max = 30,  message = "Name should be between 1 and 30 characters")
    private String nameOfBook;

    @NotEmpty(message = "Name should not to be empty")
    @Size(min = 1 , max = 30,  message = "Name should be between 1 and 30 characters")
    private String authorName;

    @Min(value = 0 , message = "Age should be more than 0")
    private int yearOfCreation;

    private Person owner;
}
