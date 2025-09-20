package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.HyponymReader;
import ngrams.NGramMap;
import ngrams.ReaderHandler;
import ngrams.TimeSeries;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
    public NGramMap ng;
    public ReaderHandler rh;

    public HyponymsHandler(){
        ng = new NGramMap("data/ngrams/top_14377_words.csv", "data/ngrams/total_counts.csv");
        rh = new ReaderHandler("data/wordnet/hyponyms.txt", "data/wordnet/synsets.txt");
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        String[] handledWords;
        if (q.k() == 0) {
            handledWords = rh.getHyponyms(words);
        }
        else {
            handledWords = getKWords(words, q.k(), q.startYear(), q.endYear());
        }

        return Arrays.toString(handledWords);
    }

    public String[] getKWords(List<String> words, int k ,int startYear, int endYear) {
        TreeMap<String, Double> tm = new TreeMap<>(Comparator.reverseOrder());
        String[] orderWords;
        TimeSeries ts;
        double sum;

        for (String word : rh.getHyponyms(words)) {
            ts = ng.countHistory(word, startYear, endYear);
            sum = 0;

            for (double value : ts.values()) {
                sum += value;
            }

            if (sum > 0) {
                tm.put(word, sum);
            }
        }
        //根据value降值排序tm中的key值
        List<String> descendingKeys = tm.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        String[] array = descendingKeys.toArray(new String[0]);
        String[] sortedArray = new String[k];
        System.arraycopy(array, 0, sortedArray, 0, k);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

}
