package uk.ac.city.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "Order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderID")
	private int ID;

	@Column(name = "OrderDateTime")
	private LocalDateTime orderDateTime;

	@Column(name = "TableNumber")
	private int tableNumber;

	@ManyToOne
	@JoinColumn(name = "AssignedChefID")
	private Chef assignedChef;

	@Column(name = "TotalPencePrice")
	private int price;

	// TODO: Check if this is right
	@ElementCollection
	@CollectionTable(name = "OrderDish", joinColumns = @JoinColumn(name = "OrderID"))
	@Column(name = "Quantity")
	private Map<Dish, Integer> dishes = new HashMap<>();
}
