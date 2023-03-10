package ibf2022.ssf.workshop14.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.ssf.workshop14.model.Contact;

@Repository
public class AddressbookRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void save(final Contact ctc) {
        redisTemplate.opsForList().leftPush("contactlist", ctc.getId());
        redisTemplate.opsForHash().put("addressbookmap", ctc.getId(), ctc); //name of map, key, value
    }

    public Contact findById(String contactId){
        //get back record inserted
        return (Contact) redisTemplate.opsForHash().get("addressbookmap", contactId);
    }

    public List<Contact> findAll(int startIndex) {
        List<Object> contactList = redisTemplate.opsForList().range("contactlist", startIndex, 10);
        List<Contact> ctcs = redisTemplate.opsForHash()
                    .multiGet("addressbookmap", contactList)
                    .stream()
                    .filter(Contact.class::isInstance) //longhand = if(c instanceof(Contact.class))
                    .map(Contact.class::cast)
                    .toList();

        return ctcs;
    }
    
}
