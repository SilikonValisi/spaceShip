package com.game8.server.services;


import com.game8.server.models.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordServiceImplTest{

    @Autowired
    private RecordService recordService;

    @Test
    public void addRecordTester(){
        Record r1 = new Record((int)(Math.random()*100), "Test");
        int oldSize = recordService.getAllRecords().size();
        recordService.addRecord(r1);
        int newSize = recordService.getAllRecords().size();
        assertEquals(oldSize+1, newSize);
    }

    @Test
    public void getRecordTester() {
        // Manually added to the database;
        Record r1 = new Record(30000, "Test");
        Optional<Record> r2 = recordService.getRecord(16);
        assertTrue(r2.isPresent());
        r2.ifPresent(record -> {
            assertEquals(record.getUsername(), r1.getUsername());
            assertEquals(r1.getScore(), record.getScore());
        });
    }
    @Test
    public void getTimelyRecordsTester(){
        // This method tests the getAllRecords, getMonthlyRecords and getWeeklyRecords
        int oldAll = recordService.getAllRecords().size();
        int oldMonthly = recordService.getMonthlyRecords().size();
        int oldWeekly = recordService.getWeeklyRecords().size();
        Record r1 = new Record((int)(Math.random()*100), "Test");
        Record r2 = new Record((int)(Math.random()*100), "Test");
        Record r3 = new Record((int)(Math.random()*100), "Test");
        Record r4 = new Record((int)(Math.random()*100), "Test");
        LocalDate date1 = LocalDate.parse("2020-03-28");
        LocalDate date2 = LocalDate.parse("2020-03-26");
        LocalDate date3 = LocalDate.parse("2020-03-12");
        LocalDate date4 = LocalDate.parse("2016-01-28");
        r1.setRecordDate(date1);
        r2.setRecordDate(date2);
        r3.setRecordDate(date3);
        r4.setRecordDate(date4);

        int newAll = recordService.getAllRecords().size();
        int newMonthly = recordService.getMonthlyRecords().size();
        int newWeekly = recordService.getWeeklyRecords().size();


        assertEquals(newAll, oldAll+4);
        assertEquals(newWeekly, oldWeekly+2);
        assertEquals(newMonthly, oldMonthly+3);

    }

}