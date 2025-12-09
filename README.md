# Westminster-Shopping-System

This project is a dual-interface online shopping system developed in Java for the Object Oriented Programming module.

It features a Console Interface (CLI) for the system manager and a Graphical User Interface (GUI) for the customer.

The primary goal was demonstrating mastery of Object-Oriented Programming (OOP) principles like inheritance and encapsulation.

OOP Structure: The system is designed with an abstract Product class, two subclasses (Electronics and Clothing), and supporting classes like User and ShoppingCart.

Manager Functionality (CLI): Includes options to add new products (max 50), delete products by ID, print the product list (sorted alphabetically by ID), and save/load data to/from a file.

Customer Functionality (GUI): Displays products in an interactive table and allows filtering by category.

Stock Highlight: Products with less than 3 items available are visually highlighted in red on the table.

Shopping Cart Logic: Allows users to add items and view the cart with the calculated Final Total.

Discount Rules: Applies a 20% discount when a user buys at least three products of the same category.

Discount Rules: Also applies a 10% discount for the customer's very first purchase.

Documentation: The project includes UML Use Case and Class Diagrams to illustrate the system's design.
