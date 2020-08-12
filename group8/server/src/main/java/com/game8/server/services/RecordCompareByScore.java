package com.game8.server.services;

import com.game8.server.models.Record;

import java.util.Comparator;

/**
 * A Comparator for comparing the Record objects.
 *
 * This Comparator is responsible for comparing two given Record
 * objects with respect to their corresponding scores. A Record
 * object with a higher score is "higher" than a Record object
 * with a lower score.
 * The purpose of this class is to implement the Highscore table
 * in client side of the application. The given Record objects
 * are sorted by using this Comparator.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
public class RecordCompareByScore implements Comparator<Record> {

    /**

     * This method is responsible for comparing two given Record objects.
     * @param o1 First Record object to be compared
     * @param o2 Second Record object to be compared
     * @return an int describing the relation between the two Record objects.
     * */
    @Override
    public int compare(Record o1, Record o2) {
        return o2.getScore() - o1.getScore();
    }
}
