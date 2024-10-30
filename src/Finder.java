import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

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
    ArrayList<KeyVal>[] table;

    public Finder() {}

    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        // TODO: Complete the buildTable() function!
        table = new ArrayList[length];

        String[] line = br.readLine().split(" ");
        while (line != null) {
            long hash = keyHash(line[keyCol]);
            int shortHash = (int) hash % length;
            table[shortHash] = new ArrayList<>();
            table[shortHash].add(new KeyVal(hash, line[valCol]));
            line = br.readLine().split(" ");
        }
        br.close();
    }

    public String query(String key){
        // TODO: Complete the query() function!
        long keyHash = keyHash(key);

        ArrayList<KeyVal> possibilities = table[(int)keyHash % 10000];
        if (possibilities != null) {
            int length = possibilities.size();
            for (int i = 0; i < length; i++) {
                if (keyHash == possibilities.get(i).keyHash) {
                    return possibilities.get(i).value;
                }
            }
        }
        return INVALID;
    }

    public long keyHash(String value) {
        long hash = 0;
        int length = value.length();

        for (int i = 0; i < length ; i++) {
            hash = (hash * 256 + value.charAt(i)) % 2305843009213693951L;
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