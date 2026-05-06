//package net.engineeringdigest.journalApplication.controller;
//
///*
//  This is where you integrate a persistent mongodb database where you
//  use Spring data mongodb to directly make your SpringBoot application
//  interact with the database without having to use any mongodb commands.
//  Spring Data MongoDB handles these queries for you in the backend.
// */
//
//import net.engineeringdigest.journalApplication.entity.JournalEntry;
//import net.engineeringdigest.journalApplication.service.JournalEntryService;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/journalv2")
//public class JournalEntryControllerV2 {
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//    @GetMapping("/get-entries")
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntryService.getEntries());
//    }
//
//    @GetMapping("/id/{myId}")
//    public JournalEntry getById(@PathVariable ObjectId myId){
//        return journalEntryService.getEntryById(myId).orElse(null);
//    }
//
//    @PostMapping("add-entry")
//    public boolean addEntry(@RequestBody JournalEntry myEntry){
//        journalEntryService.saveEntry(myEntry);
//        return true;
//    }
//
//    @DeleteMapping("remove-entry/{id}")
//    public String removeEntry(@RequestBody ObjectId id){
//        return journalEntryService.deleteEntry(id);
//    }
//
//    @PutMapping("edit-entry/{myId}")
//    public String editEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
//        return null;
//    }
//}
