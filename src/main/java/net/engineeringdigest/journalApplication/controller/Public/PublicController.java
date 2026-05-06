package net.engineeringdigest.journalApplication.controller.Public;

import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @GetMapping("/health-check")
    public String healthCheck(){

        return "Systems are up..." + "\n" + "Health Check Successful";
    }
}
