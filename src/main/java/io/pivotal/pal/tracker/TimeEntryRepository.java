package io.pivotal.pal.tracker;

import java.util.Collection;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long id);

    Collection list();

    TimeEntry update(long id, TimeEntry timeEntry);

    void delete(long id);
}
