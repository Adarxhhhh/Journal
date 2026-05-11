package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.entity.User;
import net.engineeringdigest.journalApplication.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    //This is the manual way of opening and closing mocks.
//    private AutoCloseable closeable;
//
//    @BeforeEach
//    void initService(){
//        closeable = MockitoAnnotations.openMocks(this);
//    }
//
//    @AfterEach
//    void terminateService() throws Exception {
//        closeable.close();
//    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("adarxhhhh").password("Amk#22041999").Roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("adarxhhhh");
        Assertions.assertNotNull(user);
    }
}
