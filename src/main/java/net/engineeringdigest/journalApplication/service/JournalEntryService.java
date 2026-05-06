package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.repository.JournalEntryRepository;
import net.engineeringdigest.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Controller -> Service -> Repository

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListableBeanFactory listableBeanFactory;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            //This line to set a username to null is to test why the @Transactional annotation is required;
            //Without the @Transactional the journal entry is saved to the journal entry db but the entry reference is not saved to users db
            //This is because we changed the username to null after the entry was saved but before the reference could be saved to the user.
            //@Transactional makes sure that the complete method is executed without any errors and if at any point there is an error all the previous changes in the method are rolled back.
            //user.setUserName(null);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry...");
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());

        JournalEntry saved = journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getEntries(){
        return new ArrayList<>(journalEntryRepository.findAll());
    }


    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }


    public boolean editEntry(ObjectId id, JournalEntry entry){

        Optional<JournalEntry> optional = journalEntryRepository.findById(id);

        if(optional.isEmpty()){
            return false;
        }else{
            JournalEntry existing = optional.get();

            if(entry.getTitle() != null){
                existing.setTitle(entry.getTitle());
            }

            if(entry.getContent() != null){
                existing.setContent(entry.getContent());
            }

            return true;
        }
    }

    @Transactional
    public boolean deleteEntry(ObjectId myId,  String userName){
        try{
            User user = userRepository.findByUserName(userName);

            if(user != null) {
                boolean removed = user.getJournalEntries().removeIf(x -> x.getId().toString().equals(myId.toString()));
                if (removed) {
                    userRepository.save(user);
                    journalEntryRepository.deleteById(myId);
                    return true;
                }
            }
        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage() + ", " + e.getClass());
        }
        return false;
    }
}
