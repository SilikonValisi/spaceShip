package com.game8.server.services;

import com.game8.server.models.Record;
import com.game8.server.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the RecordService Interface.
 *
 * This class implements the methods described in RecordService
 * interface. The methods implemented here provides the logic
 * for the web service of the application.
 *
 *  @author Group 8
 *  @version 1.0
 *  @since 2020-03-30
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    /**
     * This method adds a new Record into the
     * database.
     * @param record New Record object to be added.
     * @return Record object added to the database.
     */
    @Override
    public Record addRecord(Record record) {
        return this.recordRepository.save(record);
    }

    /**
     * This method returns a Record object with a ID
     * value equal to the recordId parameter.
     * @param recordId the ID value for fetching the
     * Record object.
     * @return Record object with ID value of recordId.
     */
    @Override
    public Optional<Record> getRecord(int recordId) {
        return this.recordRepository.findById(recordId);
    }

    /**
     * This method returns the top-10 Records of all time.
     * @return a List of Record objects, where the Record
     * objects contain the highest 10 scores of all time.
     */
    @Override
    public List<Record> getAllRecords() {
        // Fetch all Records
        List<Record> recordList =  this.recordRepository.findAll();
        List<Record> resultList = new ArrayList<>();

        // Sort them with respect to their scores by using RecordCompareByScore Comparator
        recordList.sort(new RecordCompareByScore());

        // Get the top 10 of the Record objects.
        for(int i=0; i<10 && i < recordList.size(); i++){
            resultList.add(recordList.get(i));
        }

        return resultList;
    }

    /**
     * This method returns the top-10 Records of that week.
     * @return a List of Record objects, where the Record
     * objects contain the highest 10 scores of that week.
     */
    @Override
    public List<Record> getWeeklyRecords() {
        // Fetch the current time
        LocalDate now = LocalDate.now();
        // Determine 1 week before *now*
        LocalDate weekBefore = now.minusWeeks(1);
        // Fetch the Records that are recorded after *weekBefore*
        List<Record> recordList =  this.recordRepository.findAllWithRecordDateAfter(weekBefore);
        List<Record> resultList = new ArrayList<>();

        // Sort the Records with respect to their scores
        recordList.sort(new RecordCompareByScore());

        // Get the top 10 of the Record objects
        for(int i=0; i<10 && i < recordList.size(); i++){
            resultList.add(recordList.get(i));
        }
        return resultList;
    }

    /**
     * This method returns the top-10 Records of that month.
     * @return a List of Record objects, where the Record
     * objects contain the highest 10 scores of that month.
     */
    @Override
    public List<Record> getMonthlyRecords() {
        // Fetch the current time
        LocalDate now = LocalDate.now();
        // Determine 1 month before *now*
        LocalDate monthBefore = now.minusMonths(1);
        // Fetch the Records that are recorded after *monthBefore*
        List<Record> recordList =  this.recordRepository.findAllWithRecordDateAfter(monthBefore);
        List<Record> resultList = new ArrayList<>();
        // Sort the Records with respect to their scores.
        recordList.sort(new RecordCompareByScore());

        // Get the top 10 of the Record objects
        for(int i=0; i<10 && i < recordList.size(); i++){
            resultList.add(recordList.get(i));
        }

        return resultList;
    }
}
