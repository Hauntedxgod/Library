package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dao.BookDAO;
import ru.maxima.dao.PersonDAO;
import ru.maxima.model.OwnerDTO;
import ru.maxima.model.Person;

@Controller
@RequestMapping("/people")
public class PersonController {
        private final PersonDAO personDAO;
        private final BookDAO bookDAO;

        @Autowired
        public PersonController(PersonDAO personDAO, BookDAO bookDAO) {
            this.personDAO = personDAO;
            this.bookDAO = bookDAO;
        }
        @GetMapping()
        public String showAllPeople(Model model){
            model.addAttribute("allPeople" , personDAO.getAllPeople());
            return "view-with-alll-people";
        }
        @GetMapping("/{id}")
        public String showPersonById(@PathVariable("id") Long id , Model model){
            Person person = personDAO.getIdPerson(id);
            person.setBooks(bookDAO.getOwnerId(id));
            model.addAttribute("allBook" , bookDAO.getAllBook());
            model.addAttribute("personById", person);
            model.addAttribute("getBookId" , bookDAO.getBookOfId(id));
            model.addAttribute("ownerDto", new OwnerDTO());
            return "view-with-person-by-id";
        }
        @GetMapping("/new")
        public String giveToUserPageToCreateNewPerson(Model model) {
            model.addAttribute("newPerson", new Person());
            return "view-to-create-new-person";
        }

        @PostMapping()
        public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person , BindingResult binding){
            if (binding.hasErrors()){
                return "view-to-create-new-person";
            }
            personDAO.savePerson(person);
            return "redirect:/people";
        }
         @PostMapping("/addowner/{id}")
          public String orderPerson(@PathVariable("id") Long id , @ModelAttribute(name = "ownerDto") OwnerDTO ownerDTO
            , BindingResult binding ){
             if (ownerDTO.getOwnerId() != null){
                 personDAO.addOwner(id , Long.valueOf(ownerDTO.getOwnerId()));
             }
             return "redirect:/people";
        }

        @PostMapping("/deletePerson/addowner/{id}")
         public String deletePersonBook(@PathVariable("id") Long id) {
            bookDAO.deleteOfPersonBook(id);
        return "redirect:/book";
        }

        @GetMapping("/{id}/edit")
        public String giveToUserPageToEditPerson(@PathVariable("id") Long id, Model model){
            model.addAttribute("editedPerson" , personDAO.getIdPerson(id));
            return "view-to-edit-person";
        }

        @PostMapping("/{id}")
        public String updateEditedPerson(@PathVariable("id") Long id ,
                                         @ModelAttribute("editedPerson") @Valid Person editedPerson , BindingResult binding){
            if (binding.hasErrors()){
                return "view-to-edit-person";
            }
            personDAO.getUpdatePerson(id , editedPerson);
            return "redirect:/people";
        }
        @PostMapping("/delete/{id}")
        public String deletePerson(@PathVariable("id") Long id ) {
            personDAO.deletePerson(id);
            return "redirect:/people";


    }
}
