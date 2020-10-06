public class Lesson_4 {

// 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
// (порядок – ABСABСABС). Используйте wait/notify/notifyAll.

    static final Object monitor = new Object();
    static char currentLetter;

    public static void main(String[] args) {
        currentLetter = 'A';
        Thread r1 = new Thread(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (currentLetter != 'A') {
                            monitor.wait();
                        }
                        System.out.print("A");
                        currentLetter = 'B';
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        r1.start();

        Thread r2 = new Thread(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (currentLetter != 'B') {
                            monitor.wait();
                        }
                        System.out.print("B");
                        currentLetter = 'C';
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        r2.start();

        Thread r3 = new Thread(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (currentLetter != 'C') {
                            monitor.wait();
                        }
                        System.out.print("C");
                        currentLetter = 'A';
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        r3.start();
    }
}