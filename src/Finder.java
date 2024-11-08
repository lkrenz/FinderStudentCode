import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Liam Krenz
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";
    private static int length = 10000;
    private static int tableLength = 20000;
    KeyVal[] table;

    public Finder() {}

    // Reads in key value pairs and creates hash table
    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        String line = "";
        ArrayList<KeyVal> values = new ArrayList<>();
        String[] csvLine;

        // Runs until end of file
        while (line != null) {

            // Doubles the length of the table
            tableLength *= 2;
            length *= 2;
            table = new KeyVal[tableLength];

            // Adds all previously added values from the array list
            for (KeyVal keyVal : values) {
                long hash = keyVal.keyHash;
                int index = (int) (hash % tableLength);

                // Places the value into the hash table
                // My idea with using try and catch was to avoid having to check if index has exceeded table length.
                // I think this speeds it up very slightly.
                while (true) {
                    try {
                        if (table[index] == null) {
                            break;
                        }
                        index++;
                    }
                    catch (Exception e) {
                        index = 0;
                    }
                }
                table[index] = keyVal;
            }

            // Reads in and adds new values until the file ends or the cap is reached
            for (int i = values.size(); i < length && (line = br.readLine()) != null; i++) {

                // Creates and adds new KeyVal with data from line
                csvLine = line.split(",");
                KeyVal value = new KeyVal(keyHash(csvLine[keyCol]), csvLine[valCol]);
                int index = (int)(value.keyHash % tableLength);
                while (true) {
                    try {
                        if (table[index] == null) {
                            break;
                        }
                        index++;
                    }
                    catch (Exception e) {
                        index = 0;
                    }
                }
                table[index] = value;
                values.add(value);
            }
        }
        br.close();
    }

    // Searches hashmap for specified key and returns the value or INVALID
    public String query(String key){
        long keyHash = keyHash(key);
        int index = (int)(keyHash % tableLength);

        // Iterates through cluster until a key match or a null value is found
        while (true) {
            try {
                if (table[index] == null) {
                    return INVALID;
                }
                if (table[index].keyHash == keyHash) {
                    return table[index].value;
                }
                index++;
            }
            catch (Exception e) {
                index = 0;
            }
        }
    }

    // Method for hashing strings
    public long keyHash(String value) {
        long hash = 0;
        int length = value.length();

        for (int i = 0; i < length; i++) {
            hash = (hash * 256 + value.charAt(i)) % 3372036854775807L;
        }
        return hash;
    }

    // Class to store key value pairs
    private static class KeyVal {

        private final long keyHash;
        private final String value;

        public KeyVal(long keyHash, String value) {
            this.keyHash = keyHash;
            this.value = value;
        }
    }
}