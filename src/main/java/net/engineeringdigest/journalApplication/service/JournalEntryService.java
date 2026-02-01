package net.engineeringdigest.journalApplication.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public boolean saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
        return true;
    }

    public List<JournalEntry> getEntries(){
        return new ArrayList<>(journalEntryRepository.findAll());
    }


    public Optional<JournalEntry> getEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    public ObjectId deleteEntry(ObjectId myId){
        if(myId == null || !journalEntryRepository.existsById(myId)){
            return null;
        }else{
            journalEntryRepository.deleteById(myId);
            return myId;
        }
    }public String editEntry(ObjectId id, JournalEntry entry){

        Optional<JournalEntry> optional = journalEntryRepository.findById(id);

        if(optional.isEmpty()){
            return "Used does not exist...";
        }else{
            JournalEntry existing = optional.get();

            if(entry.getTitle() != null){
                existing.setTitle(entry.getTitle());
            }

            if(entry.getContent() != null){
                existing.setContent(entry.getContent());
            }

            return "Update Successful";
        }
    }
}




//controller --> service --> repository