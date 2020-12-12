public class Lesson_6 {
    public static void main(String[] args) {
        int balCount = 0;
        int countTree = 1000;
        for (int i = 0; i < countTree; i++) {
            MyTreeMap<Integer, Integer> map = new MyTreeMap<>();
            int a = 0;
            while (map.height() < 6) {
                a = (int) (Math.random() * (200 + 1)) - 100;
                map.put(a, a);
            }
            map.delete(a);
            if (map.isBalance()) {
                balCount++;
            }
        }
        System.out.println("balCount = " + balCount + ", countTree = " + countTree);
        System.out.println("No balanced Tree = " + (double) (countTree - balCount) / countTree * 100 + " %");
    }
}
