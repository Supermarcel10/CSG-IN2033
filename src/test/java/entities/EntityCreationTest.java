package entities;

import org.junit.jupiter.api.BeforeAll;
import uk.ac.city.database.Database;
import uk.ac.city.database.entities.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class EntityCreationTest {
	@BeforeAll
	public static void setUp() {
		Database.initiateDB();
	}

	@Test
	public void testIngredientCreation() {
		Category baking = new Category("Baking");

		// Create ingredients
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(10);

		Ingredient sugar = new Ingredient("Sugar", baking);
		sugar.setCurrentQuantity(5);

		// Assert ingredient properties
		assertEquals("Flour", flour.getName());
		assertEquals(baking, flour.getCategory());
		assertEquals(10, flour.getCurrentQuantity());

		assertEquals("Sugar", sugar.getName());
		assertEquals(baking, sugar.getCategory());
		assertEquals(5, sugar.getCurrentQuantity());
	}

	@Test
	public void testIngredientTransactionCreation() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		LocalDateTime now = LocalDateTime.now();
		IngredientTransaction transaction = new IngredientTransaction(flour, now, 100, IngredientTransaction.TransactionType.INTOOK);

		assertSame(flour, transaction.getItem(), "Transaction should contain the correct ingredient");
		assertEquals(now, transaction.getDateTime(), "Transaction should have the correct date-time");
		assertEquals(100, transaction.getQuantity(), "Transaction should have the correct quantity");
		assertEquals(IngredientTransaction.TransactionType.INTOOK, transaction.getType(), "Transaction should have the correct type");
	}

	@Test
	public void testIngredientQuantityUpdates() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(50);
		flour.increaseQuantity(10);
		assertEquals(60, flour.getCurrentQuantity(), "Flour quantity should be correctly updated to 60");

		flour.decreaseQuantity(30);
		assertEquals(30, flour.getCurrentQuantity(), "Flour quantity should be correctly decreased to 30");
	}



	@Test
	public void testBoundarySetCurrentQuantityToZero() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(0);
		assertEquals(0, flour.getCurrentQuantity(), "Quantity should be set to 0");
	}

	@Test
	public void testBoundaryIncreaseQuantityAtMaxInt() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(Integer.MAX_VALUE - 1);
		flour.increaseQuantity(1);
		assertEquals(Integer.MAX_VALUE, flour.getCurrentQuantity(), "Quantity should match MAX_VALUE");
	}

	@Test
	public void testBoundaryDecreaseQuantityBelowZeroThrows() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(0);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> flour.decreaseQuantity(1),
			"Decreasing quantity below zero should throw IllegalArgumentException");
		assertEquals("Cannot decrease Flour quantity below 0!", exception.getMessage());
	}

	@Test
	public void testDishCreation() {
		// Create categories
		Category baking = new Category("Baking");
		Category vegetables = new Category("Vegetables");
		Category dairy = new Category("Dairy");

		// Create ingredients
		Ingredient flour = new Ingredient("Flour", baking);
		Ingredient tomato = new Ingredient("Tomato", vegetables);
		Ingredient cheese = new Ingredient("Cheese", dairy);

		// Create dish
		Dish pizza = new Dish("Pizza");
		pizza.addItem(flour, 2);
		pizza.addItem(tomato, 1);
		pizza.addItem(cheese, 1);

		// Access dish's items directly
		Map<Ingredient, Integer> items = pizza.getRequiredItems();

		// Assert dish properties
		assertEquals("Pizza", pizza.getName());
		assertEquals(3, items.size()); // 3 ingredients added
		assertEquals(2, items.get(flour).intValue()); // 2 units of flour
		assertEquals(1, items.get(tomato).intValue()); // 1 unit of tomato
		assertEquals(1, items.get(cheese).intValue()); // 1 unit of cheese
	}

	@Test
	public void testChefCreation() {
		Chef chef = new Chef("John Doe", Chef.ChefType.HEAD_CHEF);
		assertEquals("John Doe", chef.getName());
		assertEquals(Chef.ChefType.HEAD_CHEF, chef.getRole());
	}

	@Test
	public void testChefSetName() {
		Chef chef = new Chef("John Doe", Chef.ChefType.HEAD_CHEF);
		chef.setName("Jane Doe");
		assertEquals("Jane Doe", chef.getName());
	}

	@Test
	public void testLoginMethod() {
		// Assuming the login method should check a password, here is a placeholder.
		// In real testing, you would mock the database or password checking logic.
		Chef chef = new Chef("John Doe", Chef.ChefType.HEAD_CHEF);
		assertFalse(chef.login("incorrect-password"), "Login should fail with incorrect password");
		// Here you might want to add another assertion for a correct password scenario, once implemented.
	}

	@Test
	public void testOrderCreation() {
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
	public void testSetNegativeQuantity() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		assertThrows(IllegalArgumentException.class, () -> flour.setCurrentQuantity(-1),
			"Setting a negative quantity should throw IllegalArgumentException");
	}

	@Test
	public void testDecreaseMoreThanAvailable() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(5);
		assertThrows(IllegalArgumentException.class, () -> flour.decreaseQuantity(10),
			"Decreasing more than available should throw IllegalArgumentException");
	}

	@Test
	public void testAddInvalidIngredientToDish() {
		Dish pizza = new Dish("Pizza");
		assertThrows(NullPointerException.class, () -> pizza.addItem(null, 1),
			"Adding a null ingredient should throw NullPointerException");
	}

	@Test
	public void testAddNegativeQuantityOfIngredientToDish() {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		Dish pizza = new Dish("Pizza");
		assertThrows(IllegalArgumentException.class, () -> pizza.addItem(flour, -1),
			"Adding a negative quantity of ingredient should throw IllegalArgumentException");
	}

	@Test
	public void testCreateOrderWithNullDish() {
		Order order = new Order(LocalDateTime.now(), 1, new HashMap<>());
		assertThrows(NullPointerException.class, () -> order.addDish(null, 1),
			"Adding a null dish to an order should throw NullPointerException");
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

	@Test
	public void testIngredientConcurrency() throws InterruptedException {
		Category baking = new Category("Baking");
		Ingredient flour = new Ingredient("Flour", baking);
		flour.setCurrentQuantity(100);

		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			service.submit(() -> flour.increaseQuantity(1));
			service.submit(() -> flour.decreaseQuantity(1));
		}

		service.shutdown();
		service.awaitTermination(1, TimeUnit.MINUTES);

		// Check the final quantity should still be 100 after all increments and decrements
		assertEquals(100, flour.getCurrentQuantity(), "Final quantity should remain unchanged after concurrent updates");
	}

	@Test
	public void testDismissedOrder() {
		// Create Categories and Ingredients
		Category dairy = new Category("Dairy");
		Ingredient cheese = new Ingredient("Cheese", dairy);
		cheese.setCurrentQuantity(50);

		// Create Dish and add Ingredients
		Dish pizza = new Dish("Pizza");
		pizza.addItem(cheese, 2); // Uses 2 units of Cheese

		// Create Order and add Dishes
		HashMap<Dish, Integer> orderItems = new HashMap<>();
		orderItems.put(pizza, 3); // Order 3 Pizzas
		Order order = new Order(LocalDateTime.now(), 1, orderItems);

		// Dismiss the order
		order.dismissOrder();

		// Check final states
		int expectedCheeseUsed = 6; // 3 pizzas * 2 units of Cheese each
		assertEquals(expectedCheeseUsed, pizza.getRequiredItems().get(cheese).intValue(), "Correct amount of cheese should be used in the order");
		assertEquals(44, cheese.getCurrentQuantity(), "Remaining cheese quantity should be correct after order processing");
	}
}

