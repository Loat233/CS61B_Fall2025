package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.ReaderHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        HyponymsHandler hh = new HyponymsHandler();
        hh.ng = new NGramMap(wordFile, countFile);
        hh.rh = new ReaderHandler(hyponymFile, synsetFile);

        return hh;
    }
}
