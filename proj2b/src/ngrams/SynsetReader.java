package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;


public class SynsetReader {
    In synsetFile;
    public TreeMap<Integer, LinkedList<String>> map;

    public SynsetReader(String synsetFileName) {
        synsetFile = new In(synsetFileName);
        map = new TreeMap<>();

        createSynsetMap();
    }

    private void createSynsetMap() {
        String[] line;
        int id;
        String[] synset;

        while (synsetFile.hasNextLine()) {
            line = synsetFile.readLine().split(",");
            id = Integer.parseInt(line[0]);
            synset = line[1].split(" ");

            map.put(id, new LinkedList<>(Arrays.asList(synset)));
        }
    }

    public LinkedList<String> getWords(int id) {
        return map.get(id);
    }

    public LinkedList<Integer> getIDs(String word) {
        LinkedList<String> words;
        LinkedList<Integer> ids = new LinkedList<>();
        int id;

        for(Map.Entry<Integer, LinkedList<String>>entry : map.entrySet()) {
            id = entry.getKey();
            words = entry.getValue();

            if (words.contains(word)) {
                ids.add(id);
            }
        }

        return ids;
    }


}
