public class SingleRoom extends Room {

    static int available = 0;

    public SingleRoom(int beds, int area, double price) {
        super(beds, area, price);
        available++;
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Single Room ===");
        super.displayRoomDetails();
        System.out.println("Available: " + available + "\n");
    }
}