package entities;

import uk.ac.city.entities.Dish;
import uk.ac.city.entities.Ingredient;
import uk.ac.city.entities.Order;
import java.time.LocalDateTime;
import java.util.HashMap;


import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntityCreationTest {
	@Test
	public void testIngredientCreation() {
		// TODO: Implement test
	}

	@Test
	public void testIngredientTransactionCreation() {
		// TODO: Implement test
	}

	@Test
	public void testDishCreation() {
		// TODO: Implement test
	}

	@Test
	public void testChefCreation() {
		// TODO: Implement test
	}

	@Test
	public void testOrderCreation() {
		// Ingredients
		Ingredient flour = new Ingredient("Flour");
		Ingredient tomato = new Ingredient("Tomato");
		Ingredient cheese = new Ingredient("Cheese");
		Ingredient sugar = new Ingredient("Sugar");
		Ingredient milk = new Ingredient("Milk");

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
		Order testOrder = new Order(LocalDateTime.now(), 1, orderItems);

		// Expected required items for the order
		Map<Ingredient, Integer> expectedOrderItems = new HashMap<>();
		expectedOrderItems.put(flour, 5); // 1 (pancakes) + 2 * 2 (pizzas)
		expectedOrderItems.put(sugar, 2);
		expectedOrderItems.put(milk, 1);
		expectedOrderItems.put(tomato, 2); // 2 (pizzas)
		expectedOrderItems.put(cheese, 2); // 2 (pizzas)

		// Assert required items for the order
		assertEquals(expectedOrderItems, testOrder.getRequiredItems());
	}

	@Test
	public void testOrderCreationWithNoItems() {
		// Order
		HashMap<Dish, Integer> orderItems = new HashMap<>();
		Order testOrder = new Order(LocalDateTime.now(), 1, orderItems);

		// Expected required items for the order
		Map<Ingredient, Integer> expectedOrderItems = new HashMap<>();

		// Assert required items for the order
		assertEquals(expectedOrderItems, testOrder.getRequiredItems());
	}
}
