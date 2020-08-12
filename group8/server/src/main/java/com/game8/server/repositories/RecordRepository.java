package com.game8.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.game8.server.models.Record;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface for handling the Record Repository.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
@Repository
public interface RecordRepository extends JpaRepository<Record, Integer>{
    /**
     * A method that fetches the Records that are recorded into the
     * database after time date.
     * @param date, a LocalDate object that represents the date after which
     * the resulting Record objects are recorded.
     * @return List of Records, the Record objects that recorded after date.
     */
    @Query("SELECT a FROM Record a WHERE a.recordDate >= :date")
    List<Record> findAllWithRecordDateAfter(@Param("date") LocalDate date);
}
