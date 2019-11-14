package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository repo;
    //private TimeEntryRepository timeEntriesRepo;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;



 /*   public TimeEntryController(TimeEntryRepository timeEntryRepository, DistributionSummary timeEntrySummary, Counter actionCounter) {

        this.repo = timeEntryRepository;
        this.timeEntrySummary = timeEntrySummary;

        this.actionCounter = actionCounter;
    }*/

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            MeterRegistry meterRegistry
    ) {
        this.repo = timeEntriesRepo;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @DeleteMapping (value="/time-entries/{Id}")
    public ResponseEntity delete(@PathVariable("Id") long timeEntryId) {
        repo.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(repo.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping (value="/time-entries/{Id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("Id")long timeEntryId) {
        TimeEntry  result = repo.find(timeEntryId);
        actionCounter.increment();
        if (result == null)
           return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping (value="/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> result = (List<TimeEntry>) repo.list();
        actionCounter.increment();
        return new ResponseEntity<List<TimeEntry>>(result, HttpStatus.OK);
    }
    @PutMapping (value="/time-entries/{Id}")
    public ResponseEntity update(@PathVariable("Id")long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry result = repo.update(timeEntryId, expected);
        actionCounter.increment();
        if (result == null)
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PostMapping (value="/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry result = repo.create(timeEntryToCreate);
        actionCounter.increment();
        return new ResponseEntity(result, HttpStatus.CREATED);
    }
}
