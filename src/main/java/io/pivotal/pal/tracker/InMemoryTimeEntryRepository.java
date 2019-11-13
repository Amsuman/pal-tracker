package io.pivotal.pal.tracker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    HashMap repo = new HashMap();
    long myId = 0L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
         myId = myId+1;
        timeEntry.setId(myId);
        repo.put(myId, timeEntry);
        return timeEntry;

    }

    @Override
    public TimeEntry find(long id) {
        if (repo.get(id)==null) return null;
        return (TimeEntry) repo.get(id);
    }

    @Override
    public List<TimeEntry> list() {
       List <TimeEntry>result = new ArrayList(repo.values());
       return result;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (repo.get(id)==null) return null;

        timeEntry.setId(id);
        repo.replace(id, timeEntry);

        return timeEntry;


    }

    @Override
    public void delete(long id) {
        repo.remove(id);
    }
}
