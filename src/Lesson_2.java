import java.util.Arrays;
import java.util.Random;


public class Lesson_2 {

    public static int [] selectionSort(int[] a) {
        int iMin;
        int temporaryVar;
        for (int i = 0; i < a.length - 1; i++) {
            iMin = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[iMin]) {
                    iMin = j;
                }
            }
            temporaryVar = a[i];
            a[i] = a[iMin];
            a[iMin] = temporaryVar;
        }
        return a;
    }

    public static int [] insertionSort(int[] a) {
        int temporaryVar;
        for (int i = 1; i < a.length; i++) {
            int j = i;
            temporaryVar = a[i];
            while (j > 0 && (temporaryVar < a[j - 1])) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = temporaryVar;
        }
        return a;
    }

    public static int [] bubbleSort(int[] a) {
        boolean isSwap;
        int temporaryVar;
        for (int i = a.length - 1; i > 0; i--) {
            isSwap = false;
            for (int j = 0; j < i; j++) {
                if (a[j + 1] < a[j]) {
                    temporaryVar = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temporaryVar;
                    isSwap = true;
                }
            }
            if (!isSwap) {
                System.out.println("break " + i);
                break;
            }
        }
        return a;
    }


    public static void main(String[] args) {

//      3. Написать методы, реализующие рассмотренные виды сортировок, и проверить скорость выполнения каждой.
        long currentTime2;
        long currentTime3;
        long currentTime4;
        int length = 1000000;

        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100000);
        }

        int[] arr2 = Arrays.copyOf(arr, length);
        int[] arr3 = Arrays.copyOf(arr, length);
        int[] arr4 = Arrays.copyOf(arr, length);

        currentTime2 = System.currentTimeMillis();
        arr2 = insertionSort(arr2);
        double deltaTime2 = (double)(System.currentTimeMillis() - currentTime2)/1000;
        System.out.println("Время выполнения сортировки вставкой: " + deltaTime2 + " сек");

        currentTime3 = System.currentTimeMillis();
        arr3 = selectionSort(arr3);
        double deltaTime3 = (double)(System.currentTimeMillis() - currentTime3)/1000;
        System.out.println("Время выполнения сортировки выборкой: " + deltaTime3 + " сек");

        currentTime4 = System.currentTimeMillis();
        arr4 = bubbleSort(arr4);
        double deltaTime4 = (double)(System.currentTimeMillis() - currentTime4)/1000;
        System.out.println("Время выполнения пузырьковой сортировки: " + deltaTime4 + " сек");


//  4. * Сделать для MyArrayList возможность увеличения внутреннего массива при близости заполнения. применить в методе add
        Random random = new Random();
        MyArrayList<Integer> mal = new MyArrayList<>(4);
        for (int i = 0; i < 24; i++) {
            mal.add(random.nextInt(20));

//      Проверки:
//            System.out.println(mal);
//            System.out.println("Size " + mal.size());
//            System.out.println("Capacity " + mal.getCapacity());
        }
    }
}
