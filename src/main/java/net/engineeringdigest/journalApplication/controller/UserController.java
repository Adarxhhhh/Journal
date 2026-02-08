package net.engineeringdigest.journalApplication.controller;

import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> entries = userService.getEntries();

        if(entries.isEmpty()){
            return new ResponseEntity<>("Empty List", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable String userName){
        User userInDb = userService.findUserByUserName(userName);

        if(userInDb == null) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> saveUser(@RequestBody User user){

        boolean saved = userService.saveEntry(user);

        if(!saved){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody User user){
        User userInDb = userService.findUserByUserName(user.getUserName());

        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not edit user..", HttpStatus.BAD_REQUEST);
    }


//    @DeleteMapping("/delete-user/{myId}")
//    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
//        boolean deleted= userService.deleteEntry(myId);
//
//        if(!deleted){
//            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Deleted", HttpStatus.OK);
//    }
}
