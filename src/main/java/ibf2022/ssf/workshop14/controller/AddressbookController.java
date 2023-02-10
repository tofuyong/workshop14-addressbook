package ibf2022.ssf.workshop14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2022.ssf.workshop14.model.Contact;
import ibf2022.ssf.workshop14.services.AddressbookService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class AddressbookController {

    @Autowired
    private AddressbookService addrbkSvc;

    @GetMapping(value="/")
    public String showContactForm(Model model){
        model.addAttribute("contact", new Contact()); //binding process i.e. interpolation
        return "contactform";
    }

    @PostMapping(path="/contact")
    public String saveContact(@Valid Contact contact, BindingResult result,
                        Model model, HttpServletResponse response){

        if(result.hasErrors()) {
            return "contactform";
        }

        addrbkSvc.saveContact(contact);
        model.addAttribute("contact", contact);
        response.setStatus(HttpServletResponse.SC_CREATED); //status code 201, might be tested
        return "contact";
    }

    @GetMapping("/contact")
    public String getAllContact(Model model, @RequestParam(name="startIndex") Integer startIndex) {
        
        List<Contact> result = addrbkSvc.getAllContact(startIndex);
        model.addAttribute("contacts", result);
        return "list";
    }

    @GetMapping(path="/contact/{contactId}")
    
    public String getContactDetails(Model model, @PathVariable(value="contactId") String contactId){
        //above code: value="contactid" tells other developers that the key name has been used

        Contact ctc = addrbkSvc.findContactById(contactId);
        System.out.println(ctc.getId());

        model.addAttribute("contact", ctc);
        return "contact"; //return record and redirect to contact page
    }
    
}
