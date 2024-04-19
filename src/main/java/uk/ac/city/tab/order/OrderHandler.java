package uk.ac.city.tab.order;

import uk.ac.city.database.entities.Category;
import uk.ac.city.database.entities.Dish;
import uk.ac.city.database.entities.Ingredient;
import uk.ac.city.database.entities.Order;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import static uk.ac.city.tab.order.OrdersTab.pane;


/**
 * This class is responsible for handling orders as they come in.
 */
public class OrderHandler implements Runnable {
	private static final HashSet<OrderDisplay> activeOrderDisplays = new HashSet<>();

	/**
	 * This method is called when the thread is started.
	 * In the current state it adds some sample orders to the system, since the APIs are not established.
	 * Once the APIs are established, this method will be modified to wait for orders to come in.
	 */
	@Override
	public void run() {
		// Add some sample active orders
		// Categories
		Category baking = new Category("Baking");
		Category vegetables = new Category("Vegetables");
		Category dairy = new Category("Dairy");

		// Ingredients
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(100);
		Ingredient tomato = new Ingredient("Tomato", vegetables);
		tomato.setCurrentQuantity(50);
		Ingredient cheese = new Ingredient("Cheese", dairy);
		cheese.setCurrentQuantity(30);
		Ingredient sugar = new Ingredient("Sugar", baking);
		sugar.setCurrentQuantity(20);
		Ingredient milk = new Ingredient("Milk", dairy);
		milk.setCurrentQuantity(40);

		// Dishes
		Dish pancakes = new Dish("Pancakes");
		pancakes.addItem(flour, 1);
		pancakes.addItem(sugar, 2);
		pancakes.addItem(milk, 1);

		Dish pizza = new Dish("Pizza");
		pizza.addItem(flour, 2);
		pizza.addItem(tomato, 1);
		pizza.addItem(cheese, 1);

		// Order
		HashMap<Dish, Integer> orderItems = new HashMap<>();
		orderItems.put(pancakes, 1);
		orderItems.put(pizza, 2);

		HashMap<Dish, Integer> orderItems2 = new HashMap<>();
		orderItems2.put(pancakes, 2);
		orderItems2.put(pizza, 1);

		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 1, orderItems)));
		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 2, orderItems2)));

		// TODO: Implement waiting forever for orders until shutdown.
	}

	/**
	 * Adds an order to the system.
	 * @param od The order to add.
	 */
	static void addOrder(OrderDisplay od) {
		activeOrderDisplays.add(od);
		pane.getChildren().add(od);
	}

	/**
	 * Removes an order from the system.
	 * @param od The order to remove.
	 */
	static void removeOrder(OrderDisplay od) {
		activeOrderDisplays.stream()
			.filter(orderDisplay -> orderDisplay.equals(od))
			.findFirst()
			.ifPresent(orderDisplay -> {
				activeOrderDisplays.remove(orderDisplay);
				pane.getChildren().remove(orderDisplay);
			});
	}
}
