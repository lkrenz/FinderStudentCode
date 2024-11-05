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
        ArrayList<KeyVal> vals = new ArrayList<>();
        int length = 2;
        int tableLength = 4;
        boolean isTrue = true;
        while (isTrue) {
            tableLength *= 2;
            length *= 2;
            table = new KeyVal[tableLength];
            for (int i = 0; i < vals.size(); i++) {
                KeyVal num = vals.get(i);
                int index = (int) (num.keyHash % tableLength);
                while (table[index] != null) {
                    index++;
                    if (index >= tableLength) {
                        index = 0;
                    }
                }
                table[index] = num;
            }
            for (int i = vals.size(); i < length; i++) {
                String csvLine = br.readLine();
                if (csvLine == null) {
                    isTrue = false;
                    break;
                }
                String[] line = csvLine.split(",");
                long hash = keyHash(line[keyCol]);
                KeyVal num = new KeyVal(hash, line[valCol]);
                vals.add(num);
                int index = (int)(hash % tableLength);
                while (table[index] != null) {
                    index++;
                    if (index >= tableLength) {
                        index = 0;
                    }
                }
                table[index] = num;
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