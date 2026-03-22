# 🏨 Hotel Booking System

A Java-based console application for managing hotel reservations, room availability, and customer records.

---

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **Room Management** — Browse available rooms by type (Single, Double, Suite) and price range
- **Booking & Reservations** — Make, view, modify, and cancel bookings
- **Customer Management** — Register new customers and look up existing profiles
- **Check-in / Check-out** — Process guest arrivals and departures
- **Billing** — Auto-calculate charges based on room rate and duration of stay
- **Admin Panel** — Add/remove rooms, view all bookings, and generate occupancy reports

---

## Prerequisites

- Java Development Kit (JDK) **17** or higher
- Git (to clone the repository)
- A terminal / command prompt

Verify your Java installation:

```bash
java -version
```

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/hotel-booking-system.git
cd hotel-booking-system
```

### 2. Compile the Source Code

```bash
javac -d out src/**/*.java
```

Or, if you are using a flat source layout:

```bash
javac -d out src/*.java
```

### 3. Run the Application

```bash
java -cp out Main
```

---

## Usage

Once launched, you will be greeted by the main menu:

```
============================================
         HOTEL BOOKING SYSTEM
============================================
1. View Available Rooms
2. Make a Reservation
3. View My Booking
4. Cancel Booking
5. Check In
6. Check Out
7. Admin Panel
0. Exit
============================================
Enter your choice:
```

### Guest Flow

| Step | Action |
|------|--------|
| 1 | View available rooms filtered by date and type |
| 2 | Enter guest details and select a room to book |
| 3 | Receive a booking confirmation ID |
| 4 | Use the booking ID to check in on arrival |
| 5 | Check out and receive a final bill |

### Admin Access

Select **option 7** from the main menu and enter the admin password when prompted. Admin features include:

- Add or remove rooms
- View all current and upcoming reservations
- Generate a daily occupancy report

---

## Project Structure

```
hotel-booking-system/
├── src/
│   ├── Main.java               # Application entry point
│   ├── model/
│   │   ├── Room.java           # Room entity
│   │   ├── Booking.java        # Booking entity
│   │   └── Customer.java       # Customer entity
│   ├── service/
│   │   ├── BookingService.java # Reservation logic
│   │   ├── RoomService.java    # Room management
│   │   └── BillingService.java # Billing and invoice generation
│   ├── repository/
│   │   └── DataStore.java      # In-memory data storage
│   └── ui/
│       └── ConsoleMenu.java    # Menu display and input handling
├── README.md
└── .gitignore
```

---

## Technologies Used

- **Java 17** — Core language
- **Java Collections Framework** — In-memory data storage (ArrayList, HashMap)
- **Scanner** — Console input handling
- **OOP Principles** — Encapsulation, inheritance, and polymorphism throughout

---

## Contributing

Contributions are welcome! To get started:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "Add your-feature-name"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

Please ensure your code follows standard Java naming conventions and includes comments where appropriate.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

> Built as a Java console application project. Contributions and feedback are welcome!
