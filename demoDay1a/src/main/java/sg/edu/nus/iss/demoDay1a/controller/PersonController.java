package sg.edu.nus.iss.demoDay1a.controller;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.nus.iss.demoDay1a.model.Person;
import sg.edu.nus.iss.demoDay1a.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private List<Person> personList= new ArrayList<Person>();

    //same with autowired//instantiate a personservice object
    //public PersonController(){
        //personService = new PersonService();
    //}
    @Autowired 
    PersonService personService;

    @Value("${welcome.message}")
    private String welcomeMessage;

    @Value("${person.list.header}")
    private String headerMessage;


    @GetMapping(value={"/index","/home"})
    public String index(Model model){
        model.addAttribute("message", welcomeMessage);
        return "home";
    }

    @GetMapping(value = "/testRetrieveAllPerson",produces = "application/json")
    public @ResponseBody List<Person> getAllPersons(){
        personList = personService.getPersons();
        return personList;
    }

    @GetMapping(value="/list")
    public String personList(Model model){
        personList = personService.getPersons();
        model.addAttribute("persons", personList);
        model.addAttribute("listofPersons", headerMessage);
        return "personList";
    }

    @PostMapping(value="/update")
    public String updatePerson(@ModelAttribute(value = "per")Person p, Model model){
        model.addAttribute("per", p);
        return "personEdit";
    }

    @PostMapping(value = "/updatePerson")
    public String updatePersonRecord(@ModelAttribute(value="person")Person p){
        personService.updatePerson(p);
        return "redirect:/persons/list";
    }
}
