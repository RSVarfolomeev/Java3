public class Tests {

    public Tests() {
    }

    @BeforeSuite
    public void start() {
        System.out.println("start");
    }

    //   Для проверки исключения!
//    @BeforeSuite
//    public void start2() {
//        System.out.println("start");
//    }

    @Test(priority = 8)
    public void test1() {
        System.out.println("test1");
    }

    @Test(priority = 3)
    public void test2() {
        System.out.println("test2");
    }

    @Test(priority = 2)
    public void test3() {
        System.out.println("test3");
    }

    @Test(priority = 7)
    public void test4() {
        System.out.println("test4");
    }

    @AfterSuite
    public void end() {
        System.out.println("end");
    }
}
