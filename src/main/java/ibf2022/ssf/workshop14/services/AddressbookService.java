package ibf2022.ssf.workshop14.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.ssf.workshop14.model.Contact;
import ibf2022.ssf.workshop14.repository.AddressbookRepository;

@Service
public class AddressbookService {
    @Autowired
    private AddressbookRepository addrbookRepo;

    public void saveContact(final Contact ctc){
        addrbookRepo.save(ctc);
        
    }

    public Contact findContactById(String contactId) {
        return addrbookRepo.findById(contactId);
    }

    public List<Contact> getAllContact(int startIndex) {
        return addrbookRepo.findAll(startIndex); //pass in start index to fetch records of specific location 
    }
    
}
