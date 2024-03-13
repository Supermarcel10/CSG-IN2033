package uk.ac.city.entities;

import jakarta.persistence.*;
import java.util.HashSet;


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

	@OneToMany(mappedBy = "dish")
	private HashSet<OrderDish> orders;

	@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
	private HashSet<DishRequiredStock> requiredItems = new HashSet<>();
}
