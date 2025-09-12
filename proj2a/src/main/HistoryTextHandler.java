package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

import static utils.Utils.TOP_14337_WORDS_FILE;
import static utils.Utils.TOTAL_COUNTS_FILE;

public class HistoryTextHandler extends NgordnetQueryHandler {
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        NGramMap ngm = new NGramMap(TOP_14337_WORDS_FILE, TOTAL_COUNTS_FILE);

        String response = "";
        for (String word : words) {
            TimeSeries ts = ngm.weightHistory(word, startYear, endYear);
            response += word + ":"+ ts.toString() + "\n";
        }

        return response;
    }
}
