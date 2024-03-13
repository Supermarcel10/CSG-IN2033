package uk.ac.city.entities;

import jakarta.persistence.*;
import java.util.HashMap;


@Entity
@Table(name = "Dish")
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DishID")
	private int ID;

	@Column(name = "Name")
	private String name;

	@Column(name = "PricePence")
	private int price;

	// TODO: Check if this is right
	@ElementCollection
	@CollectionTable(name = "DishRequiredStock", joinColumns = @JoinColumn(name = "DishID"))
	@Column(name = "Quantity")
	private HashMap<Stock, Integer> requiredItems = new HashMap<>();
}
