package net.engineeringdigest.journalApplication.config;

import net.engineeringdigest.journalApplication.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OrderedFormContentFilter formContentFilter) throws Exception {


        return http.authorizeHttpRequests(request -> request
                        //any request coming from the add-user endpoint doesn't need authentication because it will get caught in the chicken-egg problem.
                        .requestMatchers("/public/**").permitAll() //any requests coming from the /public endpoint doesn't need authentication
                        .requestMatchers("/journal/**", "/user/**").authenticated() //all requests from the journal and user endpoints need authentication.
                        .requestMatchers("/admin/**").hasRole("ADMIN") //Only authenticate admin requests if the owner has an admin role.
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    //whenever your authentication requests come in the Authentication provider authenticates these requests and calls the setPasswordMethod()
    //The setPassword() method encodes the password by calling the declared passwordEncoder().
    //Then this new user and encoded password is saved to your mongodb
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
