package uk.ac.city.entities;

import jakarta.persistence.*;
import java.util.HashSet;


@Entity
@Table(name = "Stock")
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StockID")
	private int ID;

	@Column(name = "ItemName")
	private String name;

	@Column(name = "CurrentQuantity")
	private int currentQuantity;

	@Column(name = "MaxQuantity")
	private int maxQuantity;

	@OneToMany(mappedBy = "item")
	private HashSet<DishRequiredStock> dishesUsing;

	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
	private HashSet<StockIntake> intakes = new HashSet<>();

	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
	private HashSet<StockUsage> usages = new HashSet<>();
}
