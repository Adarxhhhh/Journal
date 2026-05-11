package net.engineeringdigest.journalApplication.service;

import net.engineeringdigest.journalApplication.entity.JournalEntry;
import net.engineeringdigest.journalApplication.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JournalEntryServiceTest {
    @InjectMocks
    private JournalEntryService journalEntryService;

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @Test
    public void findJournalById(){
        ObjectId id = new ObjectId("69fa5cdc1fee1b12753298b9");
        JournalEntry entry = JournalEntry.builder()
                .id(id)
                .title("Argentinian Mate")
                .content("Used for Rehabilitation")
                .date(LocalDateTime.now())
                .build();

        when(journalEntryService.findById(id)).thenReturn(Optional.of(entry));
        Optional<JournalEntry> entry1 = journalEntryRepository.findById(id);

        Assertions.assertTrue(entry1.isPresent());
        Assertions.assertEquals("Argentinian Mate", entry1.get().getTitle());
        Assertions.assertEquals(id, entry1.get().getId());
    }
}
