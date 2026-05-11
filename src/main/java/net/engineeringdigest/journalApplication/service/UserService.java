package net.engineeringdigest.journalApplication.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.repository.JournalEntryRepository;
import net.engineeringdigest.journalApplication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




    public void saveEntry(User user){
        userRepository.save(user);
    }

    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
        }catch (Exception e){
            log.error("Error in saving user: {} -> {}", user.getUserName(), e.getClass());
        }
    }

    public void saveNewAdmin(User adminUser){
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        adminUser.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(adminUser);
    }

    public List<User> getEntries(){
        return new ArrayList<>(userRepository.findAll());
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

//    public boolean getUserByUserName(String userName){
//        if(userRepository.findAll().contains(userName)){
//            return false;
//        }else{
//            return true;
//        }
//    }

//    public boolean deleteEntry(ObjectId myId, String userName){
//
//        if(!userRepository.existsById(myId)){
//            return false;
//        }else{
//            userRepository.deleteById(myId);
//            return true;
//        }
//    }

    @Transactional
    public boolean deleteByUserName(String userName){
        try{
            User user = userRepository.findByUserName(userName);

            if(user != null){
                List<JournalEntry> entries = user.getJournalEntries();
                entries.forEach(entry -> journalEntryRepository.deleteById(entry.getId()));
                userRepository.delete(user);
                return true;
            }
        }catch (Exception e){
            log.error("Username not found: {}", userName);
            System.out.println(e.getMessage());
        }
        return false;
    }
}


//controller --> service --> repository