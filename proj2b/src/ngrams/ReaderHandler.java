package ngrams;

import java.util.*;

public class ReaderHandler {
    HyponymReader hr;
    SynsetReader sr;

    public ReaderHandler(String hyponymsFileName, String synsetFileName) {
        hr = new HyponymReader(hyponymsFileName);
        sr = new SynsetReader(synsetFileName);
    }

    public String[] getHyponyms(String word) {
        //获取主词的id
        LinkedList<Integer> ids = sr.getIDs(word);

        if (ids.isEmpty()) {
            return null;
        }
        //添加所有主词id的下位词
        LinkedList<String> words = new LinkedList<>();
        for (int id : ids) {
            getHyponyms(words, id);
        }
        Collections.sort(words);
        //去重
        Set<String> uniqueSet = new LinkedHashSet<>(words);
        words.clear();
        words.addAll(uniqueSet);

        return words.toArray(new String[0]);
    }

    public String[] getHyponyms(List<String> words) {
        if (words.size() == 1) {
            return getHyponyms(words.getFirst());
        }
        return getSameString(words);
    }



    private void getHyponyms(LinkedList<String> words,int id) {
        //获取主词的同级词的全部单词
        LinkedList<String> synWords = sr.getWords(id);
        words.addAll(synWords);
        //获取主词的下位词的全部id
        LinkedList<Integer> hypIDList = hr.getHyponymsID(id);
        //如果没有下位词
        if (hypIDList == null) {
            return;
        }
        //如果有下位词
        for (Integer hypID : hypIDList) {
            getHyponyms(words, hypID);
        }
    }

    private String[] getSameString(List<String> words) {
        HashSet<String> hs = new HashSet<>();
        LinkedList<String> returnList = new LinkedList<>();

        for (String word : words) {
            String[] hyponymWords = getHyponyms(word);

            if (hs.isEmpty()) {
                hs.addAll(Arrays.asList(hyponymWords));
            }
            else {
                for (String hyponymWord : hyponymWords) {
                    if (hs.contains(hyponymWord)) {
                        returnList.add(hyponymWord);
                    }
                }
            }
        }
        return returnList.toArray(new String[0]);
    }
}
