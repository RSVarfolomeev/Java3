package HomeWork;

import java.util.ArrayList;
import java.util.Arrays;

class Array {
    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public static <T> void method_1(T[] arrays) {
        T i = arrays[0];
        arrays[0] = arrays[1];
        arrays[1] = i;
    }

    //2. Написать метод, который преобразует массив в ArrayList;
    public static <T> ArrayList<T> method_2(T[] arrays) {
        return new ArrayList<>(Arrays.asList(arrays));
    }



    public static void main(String[] args) {
        Double[] arr = {1.1, 2.2, 3.3, 4.4, 5.5};
        String[] arr2 = {"1.1", "2.2", "3.3", "4.4", "5.5"};
        method_1(arr);
        System.out.println(Arrays.toString(arr));

        System.out.println(method_2(arr2));

//            3. Большая задача:
//            a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
//            b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
//            c. Для хранения фруктов внутри коробки можете использовать ArrayList;
//            d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
//            e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
//            f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
//            g. Не забываем про метод добавления фрукта в коробку.

        Box<Apple> boxApple = new Box<>();
        boxApple.add(new Apple());
        boxApple.add(new Apple());
        boxApple.add(new Apple());

        Box<Orange> boxOrange = new Box<>();
        boxOrange.add(new Orange());
        boxOrange.add(new Orange());

        Box<Orange> boxOrange2 = new Box<>();
        boxOrange2.add(new Orange());
        boxOrange2.add(new Orange());
        boxOrange2.add(new Orange());


        System.out.println("Коробка с яблоками весит: " + boxApple.getWeight());
        System.out.println("Коробка с апельсинами весит: " + boxOrange.getWeight());
        System.out.println("Коробка №2 с апельсинами весит: " + boxOrange2.getWeight());

        System.out.println(boxOrange.compare(boxApple));

        boxOrange.addToBox(boxOrange2);
        System.out.println("Коробка с апельсинами после пересыпки весит: " + boxOrange.getWeight());
        System.out.println("Коробка №2 с апельсинами после досыпки весит: " + boxOrange2.getWeight());
    }
}