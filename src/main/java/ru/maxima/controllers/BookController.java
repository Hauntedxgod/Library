package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dao.BookDAO;
import ru.maxima.dao.PersonDAO;
import ru.maxima.model.LibraryBook;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String allBook(Model model){
        model.addAttribute("allBook" , bookDAO.allBook());
        model.addAttribute("allPerson" , personDAO.allPeople());
        return "view-with-all-book";
    }

    @GetMapping("/{id}")
    public String idOfBook(@PathVariable("id")Long id , Model model){
        model.addAttribute("idBook" , bookDAO.bookOfId(id) );
        model.addAttribute("idPerson" ,  personDAO.Id(id));
        return "view-with-book-id";
    }
    @GetMapping("/new")
    public String getNewBook(Model model){
        model.addAttribute("newBook" , new LibraryBook());
        return "view-create-new-book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("newBook") @Valid LibraryBook libraryBook , BindingResult binding){
        if(binding.hasErrors()){
            return "view-create-new-book";
        }
        bookDAO.saveBook(libraryBook);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String editedBook(@PathVariable("id" )  Long id , Model model ){
        model.addAttribute("editedBook" , bookDAO.bookOfId(id));
        return "view-to-edit-book";
    }

    @PostMapping("/{id}")
    public String updateEditedBook(@PathVariable("id") Long id , @ModelAttribute("editedBook")
    @Valid LibraryBook editedBook , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "view-to-edited-book";
        }
        bookDAO.updateBook(id , editedBook);
        return "redirect:/book";
    }
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id ) {
        bookDAO.deleteOfBook(id);
        personDAO.deleteById(id);
        return "redirect:/people";


    }
}
