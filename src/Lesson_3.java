import java.util.Scanner;

public class Lesson_3 {
    public static void main(String[] args) {

        //    2. Создать класс для реализации дека.
        DoubleSideQueue<Integer> queue = new DoubleSideQueue<>(5);

        //    Проверки реализации.
        System.out.println(queue);
        queue.insertLeft(4);
        System.out.println(queue);
        queue.insertLeft(3);
        System.out.println(queue);
        queue.insertLeft(2);
        System.out.println(queue);
        System.out.println(queue.removeRight());
        System.out.println(queue);
        queue.insertLeft(1);
        System.out.println(queue);
        queue.insertLeft(0);
        System.out.println(queue);
        queue.insertLeft(-1);
        System.out.println(queue);
        queue.removeRight();
        queue.removeRight();
        queue.removeRight();
        queue.insertLeft(-2);
        System.out.println(queue);
        queue.insertLeft(-3);
        System.out.println(queue);
        queue.insertLeft(-4);
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        queue.insertLeft(-5);
        System.out.println(queue);
        queue.insertRight(1);
        System.out.println(queue);
        queue.insertRight(2);
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        queue.insertRight(3);
        System.out.println(queue);
        queue.insertRight(4);
        System.out.println(queue);
        queue.insertRight(5);
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        queue.insertRight(6);
        System.out.println(queue);
        queue.insertRight(7);
        System.out.println(queue);
        System.out.println(queue.removeLeft());
        System.out.println(queue);
        queue.insertRight(8);
        System.out.println(queue);



//        1. Создать программу, которая переворачивает вводимые строки (читает справа налево).
        Scanner in = new Scanner(System.in);
        String strIn = in.nextLine();
        in.close();
        StringBuilder out = new StringBuilder();

        MyStack<Character> stack = new MyStack<>();
        for (int i = 0; i < strIn.length(); i++) {
            stack.push(strIn.charAt(i));
        }

        for (int i = 0; i < strIn.length(); i++) {
            out.append(stack.pop());
        }
        String strOut = out.toString();
        System.out.println(strOut);
    }
}


