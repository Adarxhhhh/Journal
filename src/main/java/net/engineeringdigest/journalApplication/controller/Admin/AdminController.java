package net.engineeringdigest.journalApplication.controller.Admin;

import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.service.JournalEntryService;
import net.engineeringdigest.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> usersList = userService.getEntries();

        if(!usersList.isEmpty() && usersList != null){
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }

        return new ResponseEntity<>("Not Found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public void createNewAdmin(@RequestBody User user){
        userService.saveNewAdmin(user);
    }

    //Dummy Procedure to fetch all entries
    //Only for admins to see the journal entries.
    @GetMapping("/get-all-entries")
    public ResponseEntity<?> getAllJournalEntries(){

        List<JournalEntry> entries = journalEntryService.getEntries();

        if(entries.isEmpty()){
            return ResponseEntity.badRequest().body("Empty List");
        }else{
            return ResponseEntity.ok().body(entries);
        }
    }
}
