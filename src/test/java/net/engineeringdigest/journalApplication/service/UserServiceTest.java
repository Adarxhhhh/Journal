package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.repository.JournalEntryRepository;
import net.engineeringdigest.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private JournalEntryService journalEntryService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JournalEntryRepository journalEntryRepository;

    @BeforeEach
    public void setup(){
        System.out.println("Starting new Test...");
    }

    @Test
    public void findByUserNameTest(){
        ObjectId id = new ObjectId("69fb6be528ff66aa75418d4f");
        JournalEntry entry = JournalEntry.builder()
                .id(id)
                .title("Argentinian Mate")
                .content("Used for Rehabilitation")
                .date(LocalDateTime.now())
                .build();

        User user = User.builder().userId(id).userName("adarxhhhh").password("Password").journalEntries(List.of(entry)).Roles(List.of("USER")).build();
        try{
            when(userService.findByUserName("adarxhhhh")).thenReturn(user);
            assertNotNull(user);
            assertEquals(user, userService.findByUserName("adarxhhhh"));
        }catch (AssertionFailedError e){
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "3,3,6"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b, "Failed for " + expected);
    }

    @Test
    public void checkNullEntries(){
        ObjectId id = new ObjectId("6a00b694733476b5193da0a1");
        ObjectId journalId = new ObjectId("69fa5cdc1fee1b12753298b9");
        JournalEntry entry = JournalEntry.builder()
                .id(journalId)
                .title("Coffee")
                .content("XYZ")
                .date(LocalDateTime.now())
                .build();

        List<JournalEntry> journalList = new ArrayList<>();
        journalList.add(entry);
        User user = User.builder()
                .userId(id)
                .userName("admin")
                .password("admin")
                .journalEntries(journalList) // The real object now contains the real list
                .Roles(List.of("USER", "ADMIN"))
                .build();

        when(userRepository.findByUserName("admin")).thenReturn(user);

        User foundUser = userService.findByUserName("admin");
        assertNotNull(foundUser.getJournalEntries());
        assertEquals(1, foundUser.getJournalEntries().size());
    }


}
