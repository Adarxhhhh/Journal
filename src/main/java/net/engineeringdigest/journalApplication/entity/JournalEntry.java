package net.engineeringdigest.journalApplication.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userid;

    private String title;

    private String content;

    private LocalDateTime date;

}
