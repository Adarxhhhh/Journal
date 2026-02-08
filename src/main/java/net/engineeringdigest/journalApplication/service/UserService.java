package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean saveEntry(User user){
        userRepository.save(user);
        return true;
    }

    public List<User> getEntries(){
        return new ArrayList<>(userRepository.findAll());
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public boolean deleteEntry(ObjectId myId){

        if(!userRepository.existsById(myId)){
            return false;
        }else{
            userRepository.deleteById(myId);
            return true;
        }
    }
}


//controller --> service --> repository