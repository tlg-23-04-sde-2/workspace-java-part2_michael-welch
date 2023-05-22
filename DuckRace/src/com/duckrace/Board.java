package com.duckrace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.*;

/*
 * This is a lookup table of ids to student names.
 * When a duck wins for the first time, we need to find out who that is.
 * This lookup table could be hardcoded with the data, or we could read the data 
 * in from a file, so that no code changes would need to be made for each cohort.
 *
 * Map<Integer,String> studentIdMap;
 * 
 * Integer    String
 * =======    ======
 *    1       John
 *    2       Jane
 *    3       Danny
 *    4       Armando
 *    5       Sheila
 *    6       Tess
 * 
 *
 * We also need a data structure to hold the results of all winners.
 * This data structure should facilitate easy lookup, retrieval, and storage.
 *
 * Map<Integer,DuckRacer> racerMap;
 *
 * Integer    DuckRacer
 * =======    =========
 *            id    name     wins   rewards
 *            --    ----     ----   -------
 *    5        5    Sheila     2    PRIZES, PRIZES
 *    6        6    Tess       1    PRIZES
 *   13       13    Zed        3    PRIZES, DEBIT_CARD, DEBIT_CARD
 *   17       17    Dom        1    DEBIT_CARD
 */

public class Board {
    private final Map<Integer,String> studentIdMap = loadStudentIdMap();
    private final Map<Integer,DuckRacer> racerMap  = new TreeMap<>();
    public int studentIdSize = studentIdMap.size();

    /*
     * Updates the board (racerMap) by making a DuckRacer "win."
     * This could mean fetching an existing DuckRacer from racerMap,
     * or we might need to create a new DuckRacer and put it in the map.
     * Either way, we need to make it "win."
     */
    public void update(int id, Reward reward) {
        DuckRacer racer = null;

        if (racerMap.containsKey(id)){      // id exists in racerMap, so get DuckRacer next to it
            racer = racerMap.get(id);
        }
        else {
            racer = new DuckRacer(id, studentIdMap.get(id));
            racerMap.put(id,racer);
        }
        racer.win(reward);
    }

    // FOR TESTING PURPOSES
    // see Java part 1, Session 5 for fixed width for text fields
    // show the DuckRacers (only), ie., the right side of the map
    public void show() {
        // if racerMap is empty, print "currently no results to show"
        // otherwise, show the data (as we are doing below)

        if (racerMap.isEmpty()) {
            System.out.println("There are currently no results to show. \n");
        }
        else {
            Collection<DuckRacer> racers = racerMap.values();

            System.out.println("\nDuck Race Results");
            System.out.println("=================\n");

            System.out.println("id      name     wins     rewards");
            System.out.println("__      ____     ____     _______");

            for (DuckRacer racer : racers) {
                System.out.printf("%2s %10s     %2s     %s \n",
                        racer.getId(), racer.getName(),racer.getWins(),racer.getRewards());
            }
        }

    }

    private Map<Integer, String> loadStudentIdMap() {
        Map<Integer,String> idMap = new HashMap<>();

        // read all lines from conf/student-ids.csv
        try {
            List<String> lines = Files.readAllLines(Path.of("conf/student-ids.csv"));

            // for each line (String) in the file, split it into "tokens"
            // convert the tokens as necessary and put each pair in 'idMap'
            for (String line : lines) {
                String[] tokens = line.split(",");      // ["1", "Bullen"] the 2 tokens
                idMap.put(Integer.valueOf(tokens[0]),tokens[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return idMap;
    }

}   // end of class