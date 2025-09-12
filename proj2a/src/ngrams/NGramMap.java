package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    final String wordsFilename;
    final String countFilename;
    In words;
    In counts;
    String[] wordlineReader;
    String[] countlineReader;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsFilename = wordsFilename;
        this.countFilename = countsFilename;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        resetReaderPointer();
        TimeSeries ts = new TimeSeries();

        if (fileIsEmpty(words)) {
            return ts;
        }

        while (readerHasNextLine(words)) {
            wordsReaderMoveToNextLine();
            String wordInLine = wordlineReader[0];
            int year = Integer.parseInt(wordlineReader[1]);
            double times = Double.parseDouble(wordlineReader[2]);

            if (word.equals(wordInLine) && year >= startYear && year <= endYear) {
                ts.put(year, times);
            }
        }
        return ts;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return totalCountHistory(MIN_YEAR, MAX_YEAR);
    }

    private TimeSeries totalCountHistory(int startYear, int endYear) {
        resetReaderPointer();
        TimeSeries ts = new TimeSeries();

        if (fileIsEmpty(counts)) {
            return ts;
        }

        while (readerHasNextLine(counts)) {
            countsReaderMoveToNextLine();
            int year = Integer.parseInt(countlineReader[0]);
            double count = Double.parseDouble(countlineReader[1]);
            if (year >= startYear && year <= endYear) {
                ts.put(year, count);
            }
        }
        return ts;
    }


    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        TimeSeries countTS = totalCountHistory(startYear, endYear);
        TimeSeries wordTs = countHistory(word, startYear, endYear);
        List<Integer> years =  wordTs.years();
        double freq = 0.0;

        for (int year : years) {
            freq = wordTs.get(year) / countTS.get(year);
            ts.put(year, freq);
        }
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        TimeSeries yearTs = totalCountHistory(startYear, endYear);
        double sum = 0;
        for (int year : yearTs.years()) {
            for (String word : words) {
                TimeSeries wordTs = countHistory(word, startYear, endYear);
                if (wordTs.containsKey(year)) {
                    sum += wordTs.get(year);
                }
            }
            sum /= yearTs.get(year);
            ts.put(year, sum);
        }

        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    private void resetReaderPointer() {
        words = new In(wordsFilename);
        counts = new In(countFilename);
    }

    private void wordsReaderMoveToNextLine() {
        wordlineReader = words.readLine().split("\t");
    }

    private void countsReaderMoveToNextLine() {
        countlineReader = counts.readLine().split(",");
    }
    private boolean readerHasNextLine(In file) {
        return file.hasNextLine();
    }

    private boolean fileIsEmpty(In file) {
        return file.isEmpty();
    }
}
