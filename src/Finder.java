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
 * Completed by: [YOUR NAME HERE]
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";
    private static final int length = 10000;
    KeyVal[] table;

    public Finder() {}

    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        // TODO: Complete the buildTable() function!
        String line = "";
        int tableLength = 2;
        int cap = 1;
        ArrayList<KeyVal> values = new ArrayList<>();
        String[] csvLine;
        while (line != null) {
            tableLength *= 2;
            cap *= 2;
            table = new KeyVal[tableLength];
            for (KeyVal keyVal : values) {
                long hash = keyVal.keyHash;
                int index = (int) (hash % tableLength);
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
            for (int i = values.size(); i < cap && (line = br.readLine()) != null; i++) {
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

    public String query(String key){
        long keyHash = keyHash(key);
        int index = (int)(keyHash % table.length);
        while (table[index] != null) {
            if (table[index].keyHash == keyHash) {
                return table[index].value;
            }
            index++;
            if (index >= table.length) {
                index = 0;
            }
        }
        return INVALID;
    }

    public long keyHash(String value) {
        long hash = 0;
        int length = value.length();

        for (int i = 0; i < length; i++) {
            hash = (hash * 256 + value.charAt(i)) % 3372036854775807L;
        }
        return hash;
    }

    private class KeyVal {

        private long keyHash;
        private String value;

        public KeyVal(long keyHash, String value) {
            this.keyHash = keyHash;
            this.value = value;
        }
    }
}