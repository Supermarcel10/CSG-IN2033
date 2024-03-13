package uk.ac.city.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<StockIntake> intakes = new HashSet<>();

	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<StockUsage> usages = new HashSet<>();
}
