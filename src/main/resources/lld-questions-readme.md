# System Design Problem Statements

## Hotel Reservation
Assumptions:
- Constructor takes total number of rooms.
- All rooms are equal.
- Ignore cost/payment.

APIs:
- String reserveRoom(int noOfRoomsRequested, LocalDate from, LocalDate to)
- boolean cancelReservation(String reservationId)

Focus Areas:
- Basic validations.
- Continuous availability across days.
- No partial reservations.
- Thread safety.

---

## Uber-like Ride Hailing
Assumptions:
- Drivers register with vehicles.
- Riders request rides.
- Ignore pricing/payments.

APIs:
- String requestRide(String userId, Location pickup, Location drop)
- boolean cancelRide(String rideId)

Focus Areas:
- Match riders to drivers.
- One ride per driver.
- Handle cancellations.

---

## Zomato-like Food Delivery
Assumptions:
- Restaurants register with menus.
- Orders from one restaurant.
- Ignore payments/delivery.

APIs:
- String placeOrder(String userId, String restaurantId, List<Item> items)
- boolean cancelOrder(String orderId)

Focus Areas:
- Validate item availability.
- Maintain order history.
- No partial orders.

---

## Zerodha-like Trading Platform
Assumptions:
- Users buy/sell stocks.
- Ignore brokerage/taxes.

APIs:
- String placeOrder(String userId, String stockSymbol, int quantity, OrderType type)
- boolean cancelOrder(String orderId)

Focus Areas:
- Validate balance/holdings.
- Maintain portfolio.
- Atomic buy/sell.

---

## BookMyShow-like Ticket Booking
Assumptions:
- Shows have fixed seats.
- All seats equal.

APIs:
- String bookTickets(String userId, String showId, int noOfSeats)
- boolean cancelBooking(String bookingId)

Focus Areas:
- Validate seat availability.
- No partial bookings.
- Prevent double-booking.

---

## Online Library
Assumptions:
- Books can be borrowed/returned.
- Ignore fines/due dates.

APIs:
- String borrowBook(String userId, String bookId)
- boolean returnBook(String borrowId)

Focus Areas:
- Validate availability.
- Maintain borrow history.
- Prevent multiple users borrowing same copy.

---

## E-commerce System
Assumptions:
- Products have stock counts.
- Ignore payments/shipping.

APIs:
- String placeOrder(String userId, List<Item> items)
- boolean cancelOrder(String orderId)

Focus Areas:
- Validate stock availability.
- No partial orders.
- Maintain order history.

---

## Splitwise-like Expense Sharing
Assumptions:
- Users create groups and add expenses.
- Split equally.
- Ignore payments.

APIs:
- void addExpense(String groupId, String userId, double amount, String description)
- Map<String, Double> getBalances(String groupId)

Focus Areas:
- Maintain balances.
- Validate group membership.
- No partial splits.

---

## Elevator System
Assumptions:
- Building has multiple elevators.
- Each serves floor requests.

APIs:
- void requestElevator(int floor, Direction direction)
- int getElevatorPosition(int elevatorId)

Focus Areas:
- Assign nearest elevator.
- Prevent duplicate servicing.
- Simple scheduling.

---

## Parking Lot
Assumptions:
- Fixed capacity.
- All spots equal.

APIs:
- String parkCar(String carId)
- boolean unparkCar(String ticketId)

Focus Areas:
- Validate availability.
- Prevent duplicate parking.
- Maintain car → spot mapping.

---

## ATM System
Assumptions:
- Accounts with balances.
- Supports withdraw, deposit, inquiry.

APIs:
- boolean withdraw(String accountId, double amount)
- void deposit(String accountId, double amount)
- double checkBalance(String accountId)

Focus Areas:
- Validate balance.
- Prevent overdrafts.
- Simple account storage.

---

## Car Rental System
Assumptions:
- Fleet of cars.
- All cars equal.

APIs:
- String rentCar(String userId, LocalDate from, LocalDate to)
- boolean returnCar(String rentalId)

Focus Areas:
- Validate availability.
- No partial rentals.
- Maintain rental history.

---

## Logging Framework
Assumptions:
- Applications log at levels.
- Logs stored in memory.

APIs:
- void log(LogLevel level, String message)
- List<String> getLogs(LogLevel level)

Focus Areas:
- Support multiple levels.
- Simple filtering.
- Thread safety optional.

---

## File System
Assumptions:
- Supports files/directories.
- Ignore permissions.

APIs:
- void createFile(String path)
- void deleteFile(String path)
- List<String> listDirectory(String path)

Focus Areas:
- Maintain hierarchy.
- Validate paths.
- Prevent duplicate names.

---

## Notification Service
Assumptions:
- Users subscribe to topics.
- Messages delivered to subscribers.

APIs:
- void subscribe(String userId, String topic)
- void publish(String topic, String message)

Focus Areas:
- Maintain subscriptions.
- Deliver to all subscribers.
- Prevent duplicates.

---

## Task Management System
Assumptions:
- Users create tasks with deadlines.
- Tasks can be completed.

APIs:
- String createTask(String userId, String description, LocalDate deadline)
- boolean completeTask(String taskId)

Focus Areas:
- Validate deadlines.
- Maintain task lists.
- Prevent duplicate IDs.


# Game System Design Problem Statements

## Snake & Ladder
Assumptions:
- Board has fixed size (e.g., 100 cells).
- Snakes and ladders are predefined.
- Multiple players can play.
- Dice rolls are standard (1–6).

APIs:
- void addPlayer(String playerId)
- String playTurn(String playerId) // rolls dice, moves player
- boolean hasWinner()

Focus Areas:
- Maintain player positions.
- Handle snakes and ladders correctly.
- Ensure fair dice rolls.
- Simple turn-based flow.

---

## Tic Tac Toe
Assumptions:
- Standard 3x3 board.
- Two players (X and O).
- Ignore AI opponent for now.

APIs:
- boolean makeMove(String playerId, int row, int col)
- String checkWinner()

Focus Areas:
- Validate moves (empty cell, correct turn).
- Detect winner or draw.
- Maintain board state.

---

## Chess Game
Assumptions:
- Standard 8x8 board.
- Two players (white and black).
- Ignore advanced rules like timers, AI.

APIs:
- boolean makeMove(String playerId, Move move)
- String checkGameStatus() // ongoing, checkmate, stalemate

Focus Areas:
- Validate legal moves per piece.
- Maintain board state.
- Detect check, checkmate, stalemate.
- Keep implementation modular.

🔝 High Priority (Frequently Asked in LLD Interviews)
Parking Lot – Classic OOD problem, tests entity modeling, slot allocation, and extensibility.

Elevator System – Tests scheduling, concurrency, and state management.

BookMyShow-like Ticket Booking – Seat allocation, concurrency, and validations.

Hotel Reservation System – Availability checks, locking, and booking flows.

Car Rental System – Similar to hotel booking, but with fleet management.

ATM System – Account operations, validations, concurrency.

Task Management System – CRUD operations, deadlines, and state transitions.

📈 Medium Priority (Common but slightly less “classic”)
Splitwise-like Expense Sharing – Balance tracking, group modeling, fairness.

Uber-like Ride Hailing – Matching algorithms, driver/rider states.

Zomato-like Food Delivery – Order placement, restaurant menus, item availability.

E-commerce System – Inventory management, order lifecycle.

Online Library System – Borrow/return flows, availability checks.

Notification Service – Pub-sub model, subscriptions, message delivery.

📉 Lower Priority (Occasional, more specialized)
Zerodha-like Trading Platform – Order matching, portfolio management (more niche).

Logging Framework – Levels, filtering, extensibility.

File System – Hierarchical structure, CRUD operations.

✅ Why This Order?
Parking Lot, Elevator, BookMyShow, Hotel Reservation, ATM → These are “canonical” LLD problems that interviewers love because they test modeling, concurrency, and extensibility.

Splitwise, Uber, Zomato, E-commerce → Common real-world systems, but slightly less canonical.

Zerodha, Logging, File System → Good practice, but less frequently asked unless the company is finance-heavy or systems-oriented.

👉 If you’re preparing, I’d recommend mastering the top 7 first (Parking Lot, Elevator, BookMyShow, Hotel Reservation, Car Rental, ATM, Task Management). These cover most of the patterns interviewers want to see.

Would you like me to also give you a study roadmap (e.g., which design patterns and concepts to focus on for each of these top-priority problems)?

add the snakeand ladder, tictactoe and chess game as well
Got it — let’s add Snake & Ladder, Tic Tac Toe, and Chess into the same style problem statements, and then I’ll update the priority list for LLD interviews including these game-based designs.

🎲 Snake & Ladder
Assumptions

Board has fixed size (e.g., 100 cells).

Snakes and ladders are predefined.

Multiple players can play.

Ignore advanced rules like dice variations.

APIs

void addPlayer(String playerId)

String playTurn(String playerId) // rolls dice, moves player

boolean hasWinner()

Focus Areas

Maintain player positions.

Handle snakes and ladders correctly.

Ensure fair dice rolls.

Simple turn-based flow.

❌⭕ Tic Tac Toe
Assumptions

3x3 board.

Two players (X and O).

Ignore AI opponent for now.

APIs

boolean makeMove(String playerId, int row, int col)

String checkWinner()

Focus Areas

Validate moves (empty cell, correct turn).

Detect winner or draw.

Maintain board state.

♟️ Chess Game
Assumptions

Standard 8x8 board.

Two players (white and black).

Ignore advanced rules like timers, AI.

APIs

boolean makeMove(String playerId, Move move)

String checkGameStatus() // ongoing, checkmate, stalemate

Focus Areas

Validate legal moves per piece.

Maintain board state.

Detect check, checkmate, stalemate.

Keep implementation modular.

🔝 Updated Priority List for LLD Interviews
## High Priority (Classic, frequently asked)
- Parking Lot
- Elevator System
- BookMyShow Ticket Booking
- Hotel Reservation
- Car Rental System
- ATM System
- Task Management System
- Snake & Ladder (common game design, tests turn-based modeling)
- Tic Tac Toe (simple but tests board state, validations)
- Chess Game (complex, tests extensibility, rules engine)

## Medium Priority
- Splitwise Expense Sharing
- Uber Ride Hailing
- Zomato Food Delivery
- E-commerce System
- Online Library
- Notification Service

## Lower Priority
- Zerodha Trading Platform
- Logging Framework
- File System