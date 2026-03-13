public class SuiteRoom extends Room {

    static int available = 0;

    public SuiteRoom(int beds, int area, double price) {
        super(beds, area, price);
        available++;
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Suite Room ===");
        super.displayRoomDetails();
        System.out.println("Available: " + available + "\n");
    }
}