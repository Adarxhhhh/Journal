package net.engineeringdigest.journalApplication.controller;

import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV4 {

    @Autowired
    JournalEntryService journalEntryService;


    @GetMapping("/get-entry")
    public ResponseEntity<?> getAllEntries(){
        List<JournalEntry> entries = journalEntryService.getEntries();

        if(entries.isEmpty()){
            return new ResponseEntity<>("Empty List", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId myId){
        Optional<JournalEntry> entry = journalEntryService.getEntryById(myId);

        if(entry.isEmpty()) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
    }

    @PostMapping("/add-entry")
    public ResponseEntity<?> saveEntry(@RequestBody JournalEntry entry){

        entry.setDate(LocalDateTime.now());
        boolean saved = journalEntryService.saveEntry(entry);

        if(!saved){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
    }

    @PutMapping("/edit-entry/{myId}")
    public ResponseEntity<?> editEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry entry){
        boolean result = journalEntryService.editEntry(myId, entry);

        if(result){
            return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/delete-entry/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        ObjectId objectId= journalEntryService.deleteEntry(myId);

        if(objectId == null){
            return new ResponseEntity<>("Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
