public class DoubleRoom extends Room {

    static int available = 0;

    public DoubleRoom(int beds, int area, double price) {
        super(beds, area, price);
        available++;
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Double Room ===");
        super.displayRoomDetails();
        System.out.println("Available: " + available + "\n");
    }
}