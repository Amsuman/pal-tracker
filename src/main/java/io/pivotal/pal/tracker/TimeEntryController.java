package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository repo;
    private TimeEntry timeEntryToCreate;
    //private ResponseEntity responseEntity;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repo = timeEntryRepository;
    }

    @DeleteMapping (value="/time-entries/{Id}")
    public ResponseEntity delete(@PathVariable("Id") long timeEntryId) {
        repo.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping (value="/time-entries/{Id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("Id")long timeEntryId) {
        TimeEntry  result = repo.find(timeEntryId);
        if (result == null)
           return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping (value="/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> result = (List<TimeEntry>) repo.list();
        return new ResponseEntity<List<TimeEntry>>(result, HttpStatus.OK);
    }
    @PutMapping (value="/time-entries/{Id}")
    public ResponseEntity update(@PathVariable("Id")long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry result = repo.update(timeEntryId, expected);
        if (result == null)
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PostMapping (value="/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry result = repo.create(timeEntryToCreate);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }
}
