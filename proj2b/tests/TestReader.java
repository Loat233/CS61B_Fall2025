import main.HyponymsHandler;
import ngrams.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

public class TestReader {
    // wordnet Files
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    private static final String HYPONYMS_FILE_SUBSET = "data/wordnet/hyponyms1000-subgraph.txt";
    private static final String SYNSETS_FILE_SUBSET = "data/wordnet/synsets1000-subgraph.txt";
    // NGramMap Files
    public static final String VERY_SHORT_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String SMALL_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    private static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";

    @Test
    public void HyponymReaderTest() {
        HyponymReader hr = new HyponymReader(SMALL_HYPONYM_FILE);
        HashMap<Integer, LinkedList<Integer>> map = hr.map;
    }

    @Test
    public void SynsetReaderTest() {
        SynsetReader sr = new SynsetReader(SMALL_SYNSET_FILE);
        TreeMap<Integer, LinkedList<String>> map = sr.map;
    }

    @Test
    public void ReaderHandlerTest() {
        ReaderHandler rh = new ReaderHandler(SMALL_HYPONYM_FILE, SMALL_SYNSET_FILE);
        String[] hyponyms = rh.getHyponyms("act");
        System.out.println(Arrays.toString(hyponyms));
    }

    @Test
    public void ReaderHandlerMultiWordTest() {
        ReaderHandler rh = new ReaderHandler(LARGE_HYPONYM_FILE, LARGE_SYNSET_FILE);
        List<String> words = new ArrayList<>();
        words.add("occurrence");
        words.add("change");

        String[] hyponyms = rh.getHyponyms(words);
        System.out.println(Arrays.toString(hyponyms));
    }

    @Test
    public void getKWordsTest() {
        HyponymsHandler hh = new HyponymsHandler();
        LinkedList<String> words = new LinkedList<>();
        String[] result;
        words.add("food");
        words.add("cake");
        result = hh.getKWords(words, 5, 1950, 1990);

        assertThat(Arrays.toString(result)).isEqualTo("[cake, cookie, kiss, snap, wafer]");
    }
}
