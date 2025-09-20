package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;

public class HyponymReader {
    //attribute
    In hyponymFile;
    public HashMap<Integer, LinkedList<Integer>> map;

    //constructor
    public HyponymReader(String hyponymFileName) {
        hyponymFile = new In(hyponymFileName);
        map = new HashMap<>();

        this.createHyponymMap();
    }

    //load hyponymFile to a HashMap
    private void createHyponymMap() {
        String[] line;
        String[] hyponyms;
        int synsetID;

        while (hyponymFile.hasNextLine()) {
            line = hyponymFile.readLine().split(",");
            hyponyms = directHyponyms(line);
            synsetID = Integer.parseInt(line[0]);

            for (String hyponym : hyponyms) {
                int hyponymID = Integer.parseInt(hyponym);

                if (map.containsKey(synsetID)) {
                    map.get(synsetID).add(hyponymID);
                }
                else {
                    LinkedList<Integer> hyponymList = new LinkedList<>();
                    hyponymList.add(hyponymID);
                    map.put(synsetID, hyponymList);
                }
            }
        }
    }

    private String[] directHyponyms(String[] line) {
        int n = line.length - 1;
        String[] hyponyms = new String[n];
        System.arraycopy(line, 1, hyponyms, 0, n);
        return hyponyms;
    }

    public LinkedList<Integer> getHyponymsID(int id) {
        return map.get(id);
    }

}
