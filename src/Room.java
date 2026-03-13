public abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight){
        this.numberOfBeds=numberOfBeds;
        this.pricePerNight=pricePerNight;
        this.squareFeet=squareFeet;
    }
    public void displayRoomDetails(){
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet);
        System.out.println("Price per night: " + pricePerNight);
    }
}
