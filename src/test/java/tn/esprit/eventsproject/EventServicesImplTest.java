package tn.esprit.eventsproject;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class EventServicesImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    @Test
    public void addParticipantTest() {
        Participant participant = new Participant();
        when(participantRepository.save(participant)).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        assertEquals(participant, result);
        verify(participantRepository).save(participant);
    }

    @Test
    public void addAffectEvenParticipantByIdTest() {
        int idParticipant = 1;
        Event event = new Event();
        Participant participant = new Participant();
        participant.setIdPart(idParticipant);

        when(participantRepository.findById(idParticipant)).thenReturn(Optional.of(participant));
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event, idParticipant);

        assertNotNull(result);
        verify(participantRepository).findById(idParticipant);
        verify(eventRepository).save(event);
    }



    @Test
    public void addAffectLogTest() {
        Logistics logistics = new Logistics();
        String descriptionEvent = "TestEvent";
        Event event = new Event();

        when(eventRepository.findByDescription(descriptionEvent)).thenReturn(event);
        when(logisticsRepository.save(logistics)).thenReturn(logistics);

        Logistics result = eventServices.addAffectLog(logistics, descriptionEvent);

        assertNotNull(result);
        verify(eventRepository).findByDescription(descriptionEvent);
        verify(logisticsRepository).save(logistics);
    }

    @Test
    public void getLogisticsDatesTest() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        Event event1 = new Event();
        Event event2 = new Event();

        Logistics logistics1 = new Logistics();
        logistics1.setReserve(true);

        Logistics logistics2 = new Logistics();
        logistics2.setReserve(false);

        Set<Logistics> logisticsSet1 = new HashSet<>(Collections.singletonList(logistics1));
        Set<Logistics> logisticsSet2 = new HashSet<>(Collections.singletonList(logistics2));

        event1.setLogistics(logisticsSet1);
        event2.setLogistics(logisticsSet2);

        when(eventRepository.findByDateDebutBetween(startDate, endDate)).thenReturn(Arrays.asList(event1, event2));

        List<Logistics> result = eventServices.getLogisticsDates(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(logistics1, result.get(0));
    }

    // Add more tests for other methods as needed
}