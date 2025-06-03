
/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class creates items which can be stored in rooms.
 *
 * @author Agrim Kasaju
 * @version version February 18, 2024
 */
public class Item
{
    private String description; // long description of item
    private double weight; // weight of item

    /**
     * Constructor for items in each room.
     */
    public Item(String description, double weight)
    {
        this.description = description;
        this.weight = weight;
    }

    /**
     * gets the description of the items.
     *
     * @return string represetation of the item containing its description
     * and weight.
     */
    public String getDescription()
    {
        return description + " that weights " + weight + "kg.";
    }
}
