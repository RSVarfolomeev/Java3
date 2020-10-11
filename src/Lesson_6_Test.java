import org.junit.Assert;
import org.junit.Test;


public class Lesson_6_Test {
    private Assert Assertions;
    private Lesson_6 lesson_6;

    // Тест Method_2 ко 2-му заданию.
    @Test
    public void test_1_Method_2() {
        lesson_6 = new Lesson_6();
        //1
        Assertions.assertArrayEquals(new int[]{2, 3, 3, 1, 7}, lesson_6.method_2(new int[]{1, 2, 4, 4, 2, 3, 3, 1, 7}));
        //2
        Assertions.assertArrayEquals(new int[]{2, 2, 9, 2, 3, 3, 1, 7}, lesson_6.method_2(new int[]{4, 2, 2, 9, 2, 3, 3, 1, 7}));
        //3
        try {
            lesson_6.method_2(new int[]{1, 2, 2, 9, 2, 3, 3, 1, 5});
            Assertions.assertTrue(false);
        } catch (RuntimeException e) {
        }
    }

    // Тест Method_3 к 3-му заданию.
    @Test
    public void test_2_Method_3() {
        lesson_6 = new Lesson_6();
        //1
        Assertions.assertTrue(lesson_6.method_3(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
        //2
        Assertions.assertFalse(lesson_6.method_3(new int[]{0, 2, 0, 0, 2, 3, 3, 0, 7}));
        //3
        Assertions.assertFalse(lesson_6.method_3(new int[]{1, 2, 0, 0, 2, 3, 0, 1, 7}));
        //4
        Assertions.assertFalse(lesson_6.method_3(new int[]{0, 2, 4, 4, 2, 3, 4, 0, 7}));
    }
}