import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    public void getTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        System.out.println(array.get(0));
        System.out.println(array.get(1));
        System.out.println(array.get(2));
        System.out.println(array.get(3));
        System.out.println(array.get(4));
        System.out.println(array.get(5));
        System.out.println(array.get(6));
        System.out.println(array.get(7));
    }

    @Test
    public void isEmptyTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        assertThat(array.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest(){
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");

        assertThat(array.size()).isEqualTo(7);
    }

    @Test
    public void toListTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");

        List<String> l = array.toList();
        assertThat(l.toString()).isEqualTo("[f, c, a, b, d, e, g]");
    }

    @Test
    public void removeFirstTest(){
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        assertThat(array.removeFirst()).isEqualTo("f");

        array.addLast("i");

        assertThat(array.toString()).isEqualTo("[c, a, b, d, e, g, h, i]");
    }

    @Test
    public void removeLastTest(){
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        assertThat(array.removeLast()).isEqualTo("h");

    }

    @Test
    public void resizingTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        /*
        array.addLast("i");
        array.addLast("j");
        array.addLast("k");
         */

        array.addFirst("l");
        array.addFirst("m");

        assertThat(array.toList().toString()).isEqualTo("[m, l, f, c, a, b, d, e, g, h]");
    }

    @Test
    public void iterableTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        for(String s : array) {
            System.out.println(s);
        }
    }

    @Test
    public void equalTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        ArrayDeque61B<String> brray = new ArrayDeque61B<>();
        brray.addLast("a");
        brray.addLast("b");
        brray.addFirst("c");
        brray.addLast("d");
        brray.addLast("e");
        brray.addFirst("f");
        brray.addLast("g");
        brray.addLast("h");

        assertThat(array).isEqualTo(brray);
    }

    @Test
    public void toStringTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

       assertThat(array.toString()).isEqualTo("[f, c, a, b, d, e, g, h]");
    }

    @Test
    public void replaceTest() {
        ArrayDeque61B<String> array = new ArrayDeque61B<>();
        array.addLast("a");
        array.addLast("b");
        array.addFirst("c");
        array.addLast("d");
        array.addLast("e");
        array.addFirst("f");
        array.addLast("g");
        array.addLast("h");

        array.removeFirst();

        array.addLast("i");

        array.replace(0, "j");
        array.replace(1, "k");
        array.replace(7, "l");
        array.replace(0, "m");

        assertThat(array.toString()).isEqualTo("[m, k, b, d, e, g, h, l]");
    }
}
