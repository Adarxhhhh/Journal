//package net.engineeringdigest.journalApplication.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//public class TransactionConfig {
//
//
//    @Bean
//    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
//        return new MongoTransactionManager(dbFactory);
//    }
//}
//
////Transaction Manager
////MongoTransactionManager


//The implementation to this file has been done in the JournalApplication.java class
//This is an optional, cleaner implementation of any configuration that might have been done.
//Use this structure in production.