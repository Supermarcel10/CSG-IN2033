package uk.ac.city.supermarcel10.apis;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The IManagement interface is intended to be implemented by the management developers.
 * This interface provides the methods to retrieve the menu and stock.
 *
 * @author Marcel
 * @see ZonedDateTime
 * @see ArrayList
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface IManagement {
	/**
	 * Return the quantity of all items in stock.
	 * @return HashMap of all items, as well as their corresponding stock quantity.
	 */
	HashMap<String, Integer> getStock();

	/**
	 * Returns the quantity in stock of the specified item.
	 * @param itemName The name of the item to check the stock of.
	 * @return The stock of the item. Null if the item does not exist in stock.
	 */
	Integer getStock(String itemName);

	/**
	 * Gets the current menu.
	 * @return The current menu.
	 */
	Menu getMenu();

	/**
	 * Gets the menu for the specified date &amp; time.
	 * @param dateTime The date &amp; time to get the menu for.
	 * @return The menu active during the specified date &amp; time. Null if no menu exists.
	 * @see ZonedDateTime
	 */
	Menu getMenu(ZonedDateTime dateTime);

	/**
	 * Represents a menu, containing a list of dishes and the next time the menu will be updated.
	 * The nextMenuUpdate field can be used to determine if the menu has been updated since the last getMenu call, or to make an event to update the menu after the specified date &amp; time.
	 */
	class Menu {
		/**
		 * The list of dishes in the menu.
		 */
		private final ArrayList<Dish> dishes;
		/**
		 * The next time the menu will be updated.
		 * @see ZonedDateTime
		 */
		private final ZonedDateTime nextMenuUpdate;

		/**
		 * Creates a new Menu object with the specified parameters.
		 *
		 * @param dishes The list of dishes in the menu.
		 * @param nextMenuUpdate The next time the menu will be updated.
		 * @see ZonedDateTime
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *    // Assume an implementation of dishService
		 *    Menu menu = dishService.getMenu();
		 * </pre>
		 * {@code @menu creation (internal)}
		 * <pre>
		 *    // Assume an implementation of dishService
		 *    ArrayList&lt;Dish&gt; dishes = new ArrayList&lt;Dish&gt;();
		 *    dishes.add(new Dish(123, 1000, "Pizza", "A delicious pizza", new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;(), true));
		 *    dishes.add(new Dish(456, 1500, "Pasta", "A delicious pasta", new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;(), true));
		 *    dishes.add(new Dish(789, 2000, "Salad", "A delicious salad", new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;(), true));
		 *    Menu menu = new Menu(dishes, ZonedDateTime.now().plusDays(7));
		 * </pre>
		 */
		protected Menu(ArrayList<Dish> dishes, ZonedDateTime nextMenuUpdate) {
			this.dishes = dishes;
			this.nextMenuUpdate = nextMenuUpdate;
		}

		/**
		 * Returns the list of dishes in the menu.
		 * @see ArrayList The ArrayList class is used to create a dynamic array that contains a list of elements.
		 * @return The list of dishes in the menu.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     ArrayList&lt;Dish&gt; dishes = dishService.getMenu().getDishes();
		 *     System.out.println("The menu contains " + dishes.size() + " dishes.");
		 *     // Output: The menu contains 3 dishes.
		 *
		 *     System.out.println("The first dish is " + dishes.get(0).getName());
		 *     // Output: The first dish is Pizza
		 * </pre>
		 */
		public ArrayList<Dish> getDishes() {
			return dishes;
		}

		/**
		 * Returns the next time the menu will be updated.
		 * @see ZonedDateTime
		 * @return The next time the menu will be updated.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     ZonedDateTime nextMenuUpdate = dishService.getMenu().getNextMenuUpdate();
		 *     System.out.println("The menu will be updated on " + nextMenuUpdate);
		 *     // Output: The menu will be updated on 2024-02-07T00:00:00Z
		 * </pre>
		 */
		public ZonedDateTime getNextMenuUpdate() {
			return nextMenuUpdate;
		}
	}

	/**
	 * Represents a dish in the menu.
	 */
	class Dish {
		/**
		 * The ID of the dish.
		 * This can be used to identify the dish when placing an order.
		 */
		private final int dishID;
		/**
		 * The price of the dish in pence.
		 */
		private final int price;
		/**
		 * The name of the dish.
		 */
		private final String name;
		/**
		 * The description of the dish.
		 */
		private final String description;
		/**
		 * The list of ingredients in the dish.
		 */
		private final ArrayList<String> ingredients;
		/**
		 * The list of allergens in the dish.
		 */
		private final ArrayList<String> allergens;
		/**
		 * The availability of the dish.
		 */
		private boolean isAvailable;

		/**
		 * Creates a new Dish object with the specified parameters.
		 *
		 * @param dishID The ID of the dish.
		 * @param price The price of the dish in pence.
		 * @param name The name of the dish.
		 * @param description The description of the dish.
		 * @param ingredients The list of ingredients in the dish.
		 * @param allergens The list of allergens in the dish.
		 * @param isAvailable The availability of the dish.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *  // Assume an implementation of dishService
		 *  Dish pizzaDish = new Dish(123, 1000, "Pizza", "A delicious pizza", new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;(), true);
		 * </pre>
		 */
		protected Dish(int dishID, int price, String name, String description, ArrayList<String> ingredients, ArrayList<String> allergens, boolean isAvailable) {
			this.dishID = dishID;
			this.price = price;
			this.name = name;
			this.description = description;
			this.ingredients = ingredients;
			this.allergens = allergens;
			this.isAvailable = isAvailable;
		}

		/**
		 * Returns the ID of the dish.
		 *
		 * @return The ID of the dish.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     int dishID = dish.getDishID();
		 *     System.out.println("The ID of the dish is " + dishID);
		 *     // Output: The ID of the dish is 123
		 * </pre>
		 */
		public int getDishID() {
			return dishID;
		}

		/**
		 * Returns the price of the dish in pence.
		 *
		 * @return The price of the dish in pence.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     int dishPrice = dish.getPrice();
		 *     System.out.println("The cost of the dish is " + dishPrice + " pence or £" + (dishPrice / 100));
		 *     // Output: The cost of the dish is 1000 pence or £10
		 * </pre>
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * Returns the name of the dish.
		 *
		 * @return The name of the dish.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     String dishName = dish.getName();
		 *     System.out.println("The name of the dish is " + dishName);
		 *     // Output: The name of the dish is Pizza
		 * </pre>
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the description of the dish.
		 *
		 * @return The description of the dish.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     String dishDescription = dish.getDescription();
		 *     System.out.println("The description of the dish is " + dishDescription);
		 *     // Output: The description of the dish is A delicious pizza
		 * </pre>
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Returns the list of ingredients in the dish.
		 *
		 * @return The list of ingredients in the dish.
		 * <hr/>
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     ArrayList&lt;String&gt; dishIngredients = dish.getIngredients();
		 *     dishIngredients.stream().forEach(ingredient -> System.out.println("The dish contains " + ingredient));
		 * </pre>
		 */
		public ArrayList<String> getIngredients() {
			return ingredients;
		}

		/**
		 * Returns the list of allergens in the dish.
		 *
		 * @return The list of allergens in the dish.
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     ArrayList&lt;String&gt; dishAllergens = dish.getAllergens();
		 *     dishAllergens.stream().forEach(allergen -> System.out.println("People allergic to " + allergen + " cannot eat this dish!"));
		 * </pre>
		 */
		public ArrayList<String> getAllergens() {
			return allergens;
		}

		/**
		 * Returns the availability of the dish.
		 *
		 * @return The availability of the dish.
		 * {@code @example}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     boolean isDishAvailable = dish.isAvailable();
		 *     System.out.println("Is the dish available? " + isDishAvailable);
		 * </pre>
		 */
		public boolean isAvailable() {
			return isAvailable;
		}

		/**
		 * Sets the availability of the dish.
		 *
		 * @param isAvailable The availability of the dish.
		 * {@code @example (internal)}
		 * <pre>
		 *     // Assume an implementation of dishService
		 *     Dish dish = dishService.getMenu().getDishes().get(0);
		 *     dish.setAvailable(false);
		 * </pre>
		 */
		protected void setAvailable(boolean isAvailable) {
			this.isAvailable = isAvailable;
		}
	}
}
