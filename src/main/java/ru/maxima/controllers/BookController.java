package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dao.BookDAO;
import ru.maxima.model.LibraryBook;

import java.lang.management.MemoryNotificationInfo;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String allBook(Model model){
        model.addAttribute("allBook" , bookDAO.allBook());
        return "view-with-all-book";
    }

    @GetMapping("/{id}")
    public String idOfBook(@PathVariable("id")Long id , Model model){
        model.addAttribute("idBook" , bookDAO.bookOfId(id) );
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
        return "redirect:/people";


    }
}
