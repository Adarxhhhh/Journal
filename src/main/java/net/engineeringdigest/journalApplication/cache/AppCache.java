package net.engineeringdigest.journalApplication.cache;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalApplication.entity.ConfigJournalApp;
import net.engineeringdigest.journalApplication.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    private Map<String, String> appCache;

    @Autowired
    private ConfigJournalAppRepository  configJournalAppRepository;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();

        for(ConfigJournalApp configJournalApp : all){
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }

    public String find(String key){
        return appCache.get(key);
    }
}
