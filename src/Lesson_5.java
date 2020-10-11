import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

//Замена System.out.println на логгирование.


class MainClass {
    public static final int CARS_COUNT = 4;
    public static final Semaphore SEMAPHORE = new Semaphore(CARS_COUNT / 2);
    public static final CountDownLatch cdlStart = new CountDownLatch(CARS_COUNT);
    private static final Logger logger = Logger.getLogger(MainClass.class.getName());


    public static void main(String[] args) {
        logger.warn("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        CountDownLatch cdlEnd = new CountDownLatch(CARS_COUNT * race.getStages().size());
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cdlEnd);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            cdlStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.warn("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            cdlEnd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.warn("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

class Car implements Runnable {
    private static int CARS_COUNT;
    private static final Logger logger = Logger.getLogger(Car.class.getName());

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CountDownLatch cdlEnd;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public CountDownLatch getCdlEnd() {
        return cdlEnd;
    }

    public Car(Race race, int speed, CountDownLatch cdlEnd) {
        this.race = race;
        this.speed = speed;
        this.cdlEnd = cdlEnd;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            logger.info(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            logger.info(this.name + " готов");
            MainClass.cdlStart.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MainClass.cdlStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}

abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}

class Road extends Stage {
    private static final Logger logger = Logger.getLogger(Road.class.getName());

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            logger.info(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            logger.info(c.getName() + " закончил этап: " + description);
            c.getCdlEnd().countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Tunnel extends Stage {
    private static final Logger logger = Logger.getLogger(Tunnel.class.getName());

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                logger.info(c.getName() + " готовится к этапу(ждет): " + description);
                MainClass.SEMAPHORE.acquire();
                logger.info(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                logger.info(c.getName() + " закончил этап: " + description);
                MainClass.SEMAPHORE.release();
                c.getCdlEnd().countDown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Race {
    private ArrayList<Stage> stages;

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
