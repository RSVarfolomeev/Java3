public class Lesson_5 {

    public static double degree(double number, int degree) {
        double value = 1;
        if (degree >= 0) {
            for (int i = 0; i < degree; i++) {
                value *= number;
            }
        } else {
            for (int i = 0; i > degree; i--) {
                value *= 1 / number;
            }
        }
        return value;
    }

    public static double recDegree(double number, int degree) {
        if (degree == 0) {
            return 1;
        }
        if (degree >= 0) {
            return recDegree(number, degree - 1) * number;
        } else {
            return recDegree(number, degree + 1) * 1 / number;
        }
    }


    public static void main(String[] args) {
//      1. Написать программу по возведению числа в степень с помощью рекурсии.
        System.out.println(degree(3, -4));
        System.out.println(recDegree(3, -4));

//      2. Написать программу «Задача о рюкзаке» с помощью рекурсии.
        double maxWeight = 14;
        Box a1 = new Box("#1", 3, 5);
        Box a2 = new Box("#2", 5, 10);
        Box a3 = new Box("#3", 4, 6);
        Box a4 = new Box("#4", 2, 5);

        MyArrayList<Box> list = new MyArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        MyArrayList<Box> resultItems = backpack(maxWeight, list);

//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("Name - " + list.get(i).getName() + " cost - " + list.get(i).getCost() + " weight - " + list.get(i).getWeight()+ " costWeight - " + list.get(i).getCostWeight());
        for (int i = 0; i < resultItems.size(); i++) {
            System.out.println("Name - " + resultItems.get(i).getName() + ", cost - " + resultItems.get(i).getCost() + " $, weight - " + resultItems.get(i).getWeight() + " kg, costWeight - " + resultItems.get(i).getCostWeight() + " $/kg");
        }
    }

    public static MyArrayList<Box> backpack(double packWeight, MyArrayList<Box> list) {
        double maxCostWeight = 0;
        int maxIndex = 0;
        double maxWeight;
        Box maxBox;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCostWeight() > maxCostWeight && list.get(i).getWeight() <= packWeight) {
                maxCostWeight = list.get(i).getCostWeight();
                maxIndex = i;
            }
        }
            if (list.get(maxIndex).getWeight() > packWeight) {
                return new MyArrayList<>();
            }
        maxWeight = list.get(maxIndex).getWeight();
        maxBox = list.get(maxIndex);
        if (list.size() > 0) {
            list.remove(maxIndex);
        } else {
            MyArrayList<Box> resultItems = new MyArrayList<>();
            resultItems.add(maxBox);
            return resultItems;
        }
        MyArrayList<Box> resultItems = backpack(packWeight - maxWeight, list);
        resultItems.add(maxBox);
        return resultItems;
    }
}