package campus.u2.entrysystem.access.application;

import campus.u2.entrysystem.access.domain.Access;
import campus.u2.entrysystem.access.infrastructure.exceptions.AccessInvalidInputException;
import campus.u2.entrysystem.access.infrastructure.exceptions.AccessNotFoundException;
import campus.u2.entrysystem.access.infrastructure.exceptions.AccessTypeMismatchException;
import campus.u2.entrysystem.accessnotes.application.AccessNoteRepository;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import campus.u2.entrysystem.accessnotes.infrastructure.exceptions.AccessNoteNotFoundException;
import campus.u2.entrysystem.people.application.PeopleRepository;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.people.infrastructure.exceptions.PeopleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import campus.u2.entrysystem.porters.application.PortersRepository;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.porters.infrastructure.exceptions.PorterNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class AccessService {

    // Attributes 
    private final AccessRepository accessRepository;
    private final PortersRepository portersRepository;
    private final PeopleRepository peopleRepository;
    private final AccessNoteRepository noteRepository;

    // Constructor
    @Autowired
    public AccessService(AccessRepository accessRepository, PortersRepository portersRepository, PeopleRepository peopleRepository, AccessNoteRepository noteRepository) {
        this.accessRepository = accessRepository;
        this.portersRepository = portersRepository;
        this.peopleRepository = peopleRepository;
        this.noteRepository = noteRepository;
    }

    // Methods 
    
    // Save an Access
    @Transactional
    public Access saveAccess(Access access) {
        if (access == null) {
            throw new AccessInvalidInputException("Access object cannot be null");
        }
        return accessRepository.saveAccess(access);
    }
    
    // Create an Access
    @Transactional
    public Access createAccess(Date entryAccess, Date exitAccess, Boolean accessType) {
        if (entryAccess == null) {
            throw new AccessInvalidInputException("Entry date cannot be null");
        }
        if (exitAccess == null) {
            throw new AccessInvalidInputException("Exit date cannot be null");
        }
        if (accessType == null) {
            throw new AccessInvalidInputException("Access type cannot be null");
        }
        Access access = new Access(entryAccess, exitAccess, accessType);
        return accessRepository.saveAccess(access);
    }
    
    // Delete an Access
    @Transactional
    public void deleteAccess(Long id) {
        Access access = accessRepository.getAccessById(id)
                .orElseThrow(() -> new AccessNotFoundException("Access with ID " + id + " not found"));

        People people = peopleRepository.getPeopleById(access.getPeople().getId())
                .orElseThrow(() -> new PeopleNotFoundException("Person associated with Access not found"));

        people.removeAccess(access);
        access.setPeople(null);
        accessRepository.deleteAccess(access);
    }
    
    // Get all Access records
    @Transactional
    public List<Access> getAllAccesses() {
        return accessRepository.getAllAccesses();
    }
    
    // Get Access by ID
    @Transactional
    public Access getAccessById(String id) {
        try {
            Long accessId = Long.parseLong(id);

            if (accessId == null) {
                throw new AccessInvalidInputException("ID cannot be null");
            }

            return accessRepository.getAccessById(accessId)
                    .orElseThrow(() -> new AccessNotFoundException("Access with ID " + accessId + " not found"));

        } catch (NumberFormatException ex) {
            throw new AccessTypeMismatchException("Long", id);
        }
    }
    
    // Add a Note to an Access (using an object)
    @Transactional
    public Access addAccessNoteToAccess(Long idAccess, AccessNote accessNote) {
        Access access = accessRepository.getAccessById(idAccess)
                .orElseThrow(() -> new AccessNotFoundException("Access with ID " + idAccess + " not found"));

        if (accessNote == null) {
            throw new AccessInvalidInputException("AccessNote object cannot be null");
        }

        accessNote.setAccess(access);
        access.addAccessNotes(accessNote);
        noteRepository.saveAccessNote(accessNote);
        return accessRepository.saveAccess(access);
    }
    
    // Add a Note to an Access (creating inside the method)
    @Transactional
    public Access addAccessNoteToAccess(Long idAccess, String note) {
        if (idAccess == null || note == null || note.isEmpty()) {
            throw new AccessInvalidInputException("ID and note cannot be null or empty");
        }
        Access access = accessRepository.getAccessById(idAccess)
                .orElseThrow(() -> new AccessNotFoundException("Access with ID " + idAccess + " not found"));

        AccessNote accessNote = new AccessNote(note);
        access.addAccessNotes(accessNote);
        return accessRepository.saveAccess(access);
    }
    
     // Remove a Note from an Access
    @Transactional
    public Access removeAccessNoteFromAccess(Long idAccessNote) {
        AccessNote note = noteRepository.findById(idAccessNote)
                .orElseThrow(() -> new AccessNoteNotFoundException("AccessNote with ID " + idAccessNote + " not found"));

        Access access = accessRepository.getAccessById(note.getAccess().getIdAccess())
                .orElseThrow(() -> new AccessNotFoundException("Access associated with the note not found"));

        note.setAccess(null);
        access.removeAccessNotes(note);
        noteRepository.deleteAccessNote(note);
        return accessRepository.saveAccess(access);
    }
    
    // Add a Porter to an Access
    @Transactional
    public Access addPorterToAccess(Long accessId, Long porterId) {
        Access access = accessRepository.getAccessById(accessId)
                .orElseThrow(() -> new AccessNotFoundException("Access with ID " + accessId + " not found"));

        Porters porter = portersRepository.getPorterById(porterId)
                .orElseThrow(() -> new PorterNotFoundException("Porter with ID " + porterId + " not found"));

        access.getPorters().add(porter);
        porter.getAccesses().add(access);
        accessRepository.saveAccess(access);
        portersRepository.savePorter(porter);
        return access;
    }
    
     // Remove a Porter from an Access
    @Transactional
    public Access removePorterFromAccess(Long accessId, Long porterId) {
        Access access = accessRepository.getAccessById(accessId)
                .orElseThrow(() -> new AccessNotFoundException("Access with ID " + accessId + " not found"));

        Porters porter = portersRepository.getPorterById(porterId)
                .orElseThrow(() -> new PorterNotFoundException("Porter with ID " + porterId + " not found"));

        access.getPorters().remove(porter);
        porter.getAccesses().remove(access);
        accessRepository.saveAccess(access);
        portersRepository.savePorter(porter);
        return access;
    }

    //Find Access between Dates
    @Transactional
    public List<Access> findAccessBetweenDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new AccessInvalidInputException("Start and end dates cannot be null");
        }
        return accessRepository.findAccessBetweenDates(startDate, endDate);
    }

}
