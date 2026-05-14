package net.engineeringdigest.journalApplication.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Getter
@Setter
public class ConfigJournalApp {
    private String key;
    private String value;
}
