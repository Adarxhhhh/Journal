//package net.engineeringdigest.journalApplication.controller;
//
////This is for your test with non-persistent in-memory datastore and REST apis to work on this in-memory data
//
//import net.engineeringdigest.journalApplication.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/journalv1")
//public class JournalEntryController {
//    public Map<String, JournalEntry> entries = new HashMap<>();
//
//    @GetMapping("/get-entries")
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(entries.values());
//    }
//
//    @GetMapping("/id/{myId}")
//    public JournalEntry getById(@PathVariable Long myId){
//        if(entries.containsKey(myId)){
//            return entries.get(myId);
//        }else{
//            return null;
//        }
//    }
//
//    @PostMapping("add-entry")
//    public String addEntry(@RequestBody JournalEntry myEntry){
//        if(!entries.containsKey(myEntry.getId())){
//            entries.put(myEntry.getId(), myEntry);
//            return "Added...";
//        }else{
//            return "Cannot add Duplicate entry. \nId is unique";
//        }
//    }
//
//    @DeleteMapping("remove-entry")
//    public String removeEntry(@RequestBody JournalEntry entry){
//        String id = entry.getId();
//        if(entries.isEmpty() || !entries.containsKey(id)){
//            return "ID not found";
//        }else{
//            entries.remove(id);
//            return "ID removed successfully";
//        }
//    }
//
//    @PutMapping("edit-entry/{myId}")
//    public String editEntry(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
//        if(entries.containsKey(myId)){
//            if(myEntry.getContent() != null){
//                entries.get(myId).setContent(myEntry.getContent());
//            }
//
//            if(myEntry.getTitle() != null){
//                entries.get(myId).setTitle(myEntry.getTitle());
//            }
//            return "Updates continued successfully";
//        }else{
//            return "Id absent from records";
//        }
//    }
//}
