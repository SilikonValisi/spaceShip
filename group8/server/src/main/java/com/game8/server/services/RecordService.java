package com.game8.server.services;


import com.game8.server.models.Record;

import java.util.List;
import java.util.Optional;


/**
 * A Service Interface for Record Entities.
 *
 * This interface defines the methods that are used
 * in API controller.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
public interface RecordService {
    Record addRecord(Record record);
    public Optional<Record> getRecord(int recordId);
    List<Record> getAllRecords();
    List<Record> getWeeklyRecords();
    List<Record> getMonthlyRecords();
}
