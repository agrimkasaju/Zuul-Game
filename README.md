# Zuul Game - Text-Based Adventure

This project is a Java-based, text-driven adventure game developed as part of SYSC 2004 (Object-Oriented Software Development) at Carleton University during the Winter 2024 term. It is inspired by the classic *Zuul* game and demonstrates core object-oriented programming concepts such as encapsulation, inheritance, and polymorphism.

## 🚀 Features

- **Interactive Room Navigation**: Players can explore interconnected rooms using simple text commands.
- **Object Interactions**: Players can pick up, drop, and examine items found in various rooms.
- **Command Parser**: Modular system for handling user inputs like `go`, `look`, `help`, `quit`, `back`, and more.
- **Inventory System**: Items can be added to and removed from the player's inventory.
- **Backtracking Mechanism**: Players can return to previously visited rooms using a `back` command.
- **Extensible Design**: New commands, items, or rooms can be added with minimal changes to the core structure.

## 🛠 Technologies Used

- Java 17+
- OOP Principles
- Command Line Interface (CLI)

## 📁 Project Structure

```bash
src/
├── game/
│   ├── Game.java           # Main entry point
│   ├── Parser.java         # Parses player input
│   ├── Command.java        # Encapsulates a command
│   ├── CommandWords.java   # Valid command definitions
│   ├── Room.java           # Room structure
│   ├── Item.java           # Item representation
