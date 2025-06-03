import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.31
 * 
 * @author Agrim Kasaju
 * @version February 18, 2024
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Item items;
    private Stack<Room> currentRooms;
    private int count;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        items = null;
        currentRooms = new Stack<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside

        // create items
        Item tree = new Item("Tree", 500.5);
        Item building = new Item("Building", 700.5);
        Item book = new Item("Book", 0.5);
        Item table = new Item("Table", 5);
        Item computer = new Item("Computer", 3.0);
        Item pen = new Item("Pen", 0.3);

        //add items to rooms
        outside.addItem(tree);
        outside.addItem(building);
        theatre.addItem(book);
        pub.addItem(table);
        lab.addItem(computer);
        office.addItem(pen);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if(commandWord.equals("look")){
            look(command);
        }else if(commandWord.equals("eat")){
            eat(command);
        }else if(commandWord.equals("back")){
            back(command);
        }else if(commandWord.equals("stackBack")){
            stackBack(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRooms.push(currentRoom);
            count += 1;
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Output the detailed description of the current room.
     * 
     * @param command The command to be processed
     */
    private void look(Command command){
        if(command.hasSecondWord()){
            System.out.println("Look what?");
            return;
        }else{
            String description = currentRoom.getLongDescription();
            System.out.println(description);
        }
    }

    /**
     * Output a message to say that you have eaten and are no longer hungry.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command){
        if(command.hasSecondWord()){
            System.out.println("Eat what?");
            return;
        }else{
            System.out.println("You have eaten and are no longer hungry.");  
        }
    }

    /**
     * takes you to the previous room that you visited.
     * 
     * @param command The command to be processed
     */
    private void back(Command command){
        if(command.hasSecondWord()){
            System.out.println("Back what?");
            return;
        }else{
            Room temp = currentRoom; // stores current room.
            currentRooms.push(currentRoom);
            currentRoom = previousRoom; //make current room previous room
            previousRoom = temp; //previous room becomes the current room
            System.out.println("You have gone back. " + 
                currentRoom.getLongDescription());
            count += 1;
        }
    }

    /**
     * takes you back one room at a time until you reach the beginning of the
     * game.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command){
        if(command.hasSecondWord()){
            System.out.println("stackBack what?");
            return;
        }else if(count == 0){
            System.out.println("No room to go back to.");
        }else{
            previousRoom = currentRoom;
            currentRoom = currentRooms.pop();
            System.out.println("You have gone back. " + 
                currentRoom.getLongDescription());
            count -= 1;
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
