Overview

This project builds upon the "Byte Me!" food ordering system from Assignment 3, enhancing its functionality with advanced features such as a Graphical User Interface (GUI), I/O Stream Management, and JUnit Testing. The primary objective is to introduce modern programming concepts while maintaining seamless integration with existing functionalities.

Features

1. Graphical User Interface (15 Marks)
Menu Page:
Displays the canteen menu, including details such as item name, price, and availability in an intuitive interface.
Pending Orders Page:
Displays a list of pending orders with details like order number, items ordered, and current status (e.g., preparing, out for delivery).
Navigation:
Buttons to switch between the Menu Page and Pending Orders Page.
GUI operates as a display-only interface, with data updates managed through the CLI.
Design Choices:
Built using Swing (JPanel, JFrame, JTable) or JavaFX (TableView, ListView, Scene Builder) for a user-friendly and modern design.
2. I/O Stream Management (20 Marks)
Implemented two of the following file-handling features:

Order History Storage:
Saves user-specific order histories (items, quantity, and price) to a file, ensuring persistence across sessions.
User Management:
Handles user data by retrieving details for existing users or appending new user details upon registration.
Temporary Cart Storage:
Saves cart data during the session in real-time, ensuring updates are reflected immediately in the file.
3. JUnit Testing (15 Marks)
JUnit tests implemented for two scenarios to validate system functionality:

Out-of-Stock Item Orders:
Tests whether the system prevents orders for items that are unavailable, displaying an appropriate error message.
Invalid Login Attempts:
Simulates incorrect usernames or passwords to ensure the system denies access and provides the correct error message.
Cart Operations:
Verifies the following actions:
Adding items to the cart and accurately updating the total price.
Modifying item quantities in the cart and recalculating the total price.
Preventing invalid actions, such as setting a negative quantity for items.
