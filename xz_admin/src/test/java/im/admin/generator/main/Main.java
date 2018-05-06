package im.admin.generator.main;


public class Main {
    public static void main(String[] args) {
        new DaoRobot().main(args);
        new ServiceRobot().main(args);
        new AccessRobot().main(args);
        new HtmlRobot().main(args);
    }
}
