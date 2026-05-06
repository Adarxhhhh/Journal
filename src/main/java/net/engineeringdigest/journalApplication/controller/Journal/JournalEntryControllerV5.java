package net.engineeringdigest.journalApplication.controller.Journal;

import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.service.JournalEntryService;
import net.engineeringdigest.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//In V5 you start mapping the journal entries to the respective users

//Controller -> Service -> Repository

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV5 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    //Dummy Procedure to fetch all entries
//    @GetMapping("/get-entry")
//    public ResponseEntity<?> getAllJournalEntries(){
//        List<JournalEntry> entries = journalEntryService.getEntries();
//
//        if(entries.isEmpty()){
//            return ResponseEntity.badRequest().body("Empty List");
//        }else{
//            return ResponseEntity.ok().body(entries);
//        }
//    }


    @GetMapping("/get-entry")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntry> entries = user.getJournalEntries();

        if(entries.isEmpty()){
            return ResponseEntity.ok().body("Empty");
        }else{
            return ResponseEntity.ok().body(entries);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if(!collect.isEmpty()){
            Optional<JournalEntry> entry = journalEntryService.findById(myId);
            if(entry.isPresent()) return ResponseEntity.ok().body(entry);
        }

        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-entry")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try{
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-entry/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry entry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        //Match and see if the current User owns Entry
        boolean ownsEntry = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(myId));

        if(ownsEntry) {
            JournalEntry old = journalEntryService.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(!entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle());
                old.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return ResponseEntity.ok().body(old);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("Not Authorized to access", HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping("/delete-entry/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        boolean deleted = journalEntryService.deleteEntry(myId, userName);

        if(deleted){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unauthorized to delete", HttpStatus.UNAUTHORIZED);
    }
}
