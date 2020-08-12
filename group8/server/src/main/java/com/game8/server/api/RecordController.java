package com.game8.server.api;

import com.game8.server.models.Record;
import com.game8.server.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * API Controller for Record entities.
 *
 * This class is responsible for handling the API requests
 * regarding the Record entities. The methods that responses
 * to the API requests are handled by this class.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
@RequestMapping("api/record")
@RestController
public class RecordController {


    /**
     * The RecordService interface describes the logic
     * used in this controller.
     */
    @Autowired
    private RecordService recordService;


    /**
     * This method is responsible for the requests to add
     * new records into the database.
     * @param record A Record object that is requested by API
     * to be recorded into the database.
     * @return A ResponseEntity containing the Record object.
     */
    @PostMapping(path="/addRecord")
    public ResponseEntity<Record> addRecord(@RequestBody Record record) {
        return ResponseEntity.ok().body(this.recordService.addRecord(record));
    }

    /**
     * This method is responsible for the GET requests for the
     * all time records.
     * @return A ResponseEntity containing Record objects.
     */
    @GetMapping("/getAllRecords")
    public ResponseEntity< List<Record> > getAllRecords(){
        return ResponseEntity.ok().body(this.recordService.getAllRecords());
    }

    /**
     * This method is responsible for the GET requests for the
     * weekly records.
     * @return A ResponseEntity containing Record objects.
     */
    @GetMapping("/getWeeklyRecords")
    public ResponseEntity< List<Record> > getWeeklyRecords(){
        return ResponseEntity.ok().body(this.recordService.getWeeklyRecords());
    }

    /**
     * This method is responsible for the GET requests for the
     * monthly records.
     * @return A ResponseEntity containing Record objects.
     */
    @GetMapping("/getMonthlyRecords")
    public ResponseEntity< List<Record> > getMonthlyRecords(){
            return ResponseEntity.ok().body(this.recordService.getMonthlyRecords());
    }

    /**
     * This method is Responsible for the GET requests for a
     * Record object from the database with an ID value of recordId.
     * @param recordId an Integer, the ID value of the requested Record object.
     * @return A ResponseEntity containing the requested Record object.
     */
    @PostMapping("/getRecord/{recordId}")
    public ResponseEntity<Optional<Record>> getRecord(@PathVariable int recordId){
        return ResponseEntity.ok().body(this.recordService.getRecord(recordId));
    }

}
