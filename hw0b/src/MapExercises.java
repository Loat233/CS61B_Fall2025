import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> M = new TreeMap<>();
        int i = 1;
        for (char c = 'a';c <= 'z';c++) {
            M.put(c, i++);
        }
        return M;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> M = new TreeMap<>();
        for(int i : nums) {
            int square = i * i;
            M.put(i, square);
        }
        return M;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> M = new TreeMap<>();
        for(String word: words) {
            if (M.containsKey(word)) {
                int time = M.get(word) + 1;
                M.put(word, time);
            }
            else {
                M.put(word, 1);
            }
        }
        return M;
    }
}
