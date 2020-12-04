import java.util.LinkedList;
import java.util.ListIterator;

public class Lesson_4 {
    public static void main(String[] args) {
        MyLinkedList<String> mll = new MyLinkedList<>();


        mll.insertFirst("40");
        mll.insertFirst("30");
        mll.insertFirst("20");
        mll.insertFirst("10");

        ListIterator<String> listIter = mll.listIterator();
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.next());
        System.out.println("index - " + listIter.nextIndex());
        listIter.remove();
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.next());
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.next());
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.next());
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.hasNext());
        System.out.println(listIter.hasPrevious());
        System.out.println(listIter.previous());
        System.out.println("index - " + listIter.nextIndex());
        listIter.remove();
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.previous());
        System.out.println("index - " + listIter.nextIndex());
        System.out.println(listIter.next());
        System.out.println("index - " + listIter.nextIndex());


//        Iterator<String> iter = mll.iterator();
//
//        System.out.println(iter.next());
//        iter.remove();
//        System.out.println(iter.next());
//        System.out.println(iter.next());


        for (String s : mll) {
            System.out.println(s);
        }


        MyStack<Integer> st = new MyStack<>();
        System.out.println(st.isEmpty());
        System.out.println(st.size());
        st.push(4);
        st.push(3);
        st.push(2);
        st.push(1);
        System.out.println(st.isEmpty());
        System.out.println(st.size());
        System.out.println(st.peek());

        for (int i = 0; i < 4; i++) {
            System.out.println(st.pop());
        }
    }
}
