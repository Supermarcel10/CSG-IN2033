package uk.ac.city.tab.order;

import uk.ac.city.database.entities.Dish;
import uk.ac.city.database.entities.Ingredient;
import uk.ac.city.database.entities.Order;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import static uk.ac.city.tab.order.OrdersTab.pane;


public class OrderHandler implements Runnable {
	private static final HashSet<OrderDisplay> activeOrderDisplays = new HashSet<>();

	@Override
	public void run() {
		// Add some sample active orders
		// Ingredients
		Ingredient flour = new Ingredient("Flour");
		flour.setCurrentQuantity(100);
		Ingredient tomato = new Ingredient("Tomato");
		tomato.setCurrentQuantity(50);
		Ingredient cheese = new Ingredient("Cheese");
		cheese.setCurrentQuantity(30);
		Ingredient sugar = new Ingredient("Sugar");
		sugar.setCurrentQuantity(20);
		Ingredient milk = new Ingredient("Milk");
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

		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 1, orderItems)));
		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 2, orderItems)));
		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 3, orderItems)));
		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 4, orderItems)));
		addOrder(new OrderDisplay(new Order(LocalDateTime.now(), 5, orderItems)));

		// TODO: Implement waiting forever for orders until shutdown.
	}

	protected static void addOrder(OrderDisplay od) {
		activeOrderDisplays.add(od);
		pane.getChildren().add(od);
	}

	protected static void removeOrder(OrderDisplay od) {
		activeOrderDisplays.stream()
			.filter(orderDisplay -> orderDisplay.equals(od))
			.findFirst()
			.ifPresent(orderDisplay -> {
				activeOrderDisplays.remove(orderDisplay);
				pane.getChildren().remove(orderDisplay);
			});
	}
}
