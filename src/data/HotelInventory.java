package data;
import java.util.ArrayList;

public class HotelInventory {

    public static ArrayList<Room> rooms = new ArrayList<>();

    public static void addRoom(Room room) {
        rooms.add(room);
    }

    public static void displayAllRooms() {
        for (Room r : rooms) {
            r.displayRoomDetails();
        }
    }

    public static Room findAvailableRoom(Class<?> roomType) {
        for (Room r : rooms) {
            if (roomType.isInstance(r) && r.isAvailable()) {
                return r;
            }
        }
        return null;
    }
}