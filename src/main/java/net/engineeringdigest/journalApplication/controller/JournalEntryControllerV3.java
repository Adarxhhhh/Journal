//package net.engineeringdigest.journalApplication.controller;
//
///*
//  In V2, you integrated a persistent mongodb database where you
//  use Spring data mongodb to directly make your SpringBoot application
//  interact with the database without having to use any mongodb commands.
//  Spring Data MongoDB handles these queries for you in the backend.
//
//  in V3 you perfected how to implement controller --> service --> repository
//*/
//
//import net.engineeringdigest.journalApplication.entity.JournalEntry;
//import net.engineeringdigest.journalApplication.service.JournalEntryService;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
////Status Codes indicate that there was an issue error and the expected response was either returned/not returned.
//
//@RestController
//@RequestMapping("/journal")
//
//public class JournalEntryControllerV3 {
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//
//    @GetMapping("/get-entry")
//    public ResponseEntity<?> getAllEntries(){
//        List<JournalEntry> list = journalEntryService.getEntries();
//
//        if(list != null && !list.isEmpty()){
//            return new ResponseEntity<>(list, HttpStatus.FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/id/{myID}")
//    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myID){
//            Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myID);
//
//            if(journalEntry.isPresent()){
//                return new ResponseEntity<>(journalEntry.get(), HttpStatus.FOUND);
//            }
//            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/add-entry")
//    public ResponseEntity<?> addEntry(@RequestBody JournalEntry myEntry){
//        myEntry.setDate(LocalDateTime.now());
//        boolean res = journalEntryService.saveEntry(myEntry);
//
//        if(res){
//            return new ResponseEntity<>("Successful", HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>("Already Exists", HttpStatus.BAD_REQUEST);
//    }
//
//    @PutMapping("/edit-entry/{myId}")
//    public String editEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
//        return journalEntryService.editEntry(myId, myEntry);
//    }
//
//    @DeleteMapping("/remove-entry/{myId}")
//    public ResponseEntity<?> removeEntry(@PathVariable ObjectId myId){
//        ObjectId id = journalEntryService.deleteEntry(myId);
//        if(id == null){
//            return new ResponseEntity<>("Not Found", HttpStatus.NO_CONTENT);
//        }else{
//            return new ResponseEntity<>(id, HttpStatus.OK);
//        }
//    }
//
//}
