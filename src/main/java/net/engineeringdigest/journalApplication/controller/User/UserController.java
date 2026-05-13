package net.engineeringdigest.journalApplication.controller.User;

import net.engineeringdigest.journalApplication.api.response.WeatherResponse;
import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.service.UserService;
import net.engineeringdigest.journalApplication.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;

//    @GetMapping("/get-users")
//    public ResponseEntity<?> getAllUsers(){
//        List<User> entries = userService.getEntries();
//
//        if(entries.isEmpty()){
//            return new ResponseEntity<>("Empty List", HttpStatus.BAD_REQUEST);
//        }else{
//            return new ResponseEntity<>(entries, HttpStatus.OK);
//        }
//    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);

        if(userInDb == null) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting = "Hi " + authentication.getName();

        WeatherResponse weatherResponse = weatherService.getWeather("Austin");

        StringBuilder message = new StringBuilder(greeting);

        // Append weather if available
        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            message.append(", Weather in Austin feels like ") //
                    .append(weatherResponse.getCurrent().getFeelslike());
        }

        return new ResponseEntity<>(message.toString(), HttpStatus.OK);
    }


    @PutMapping("/edit-user")
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }


    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = userService.deleteByUserName(userName);

        if(!deleted){
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
