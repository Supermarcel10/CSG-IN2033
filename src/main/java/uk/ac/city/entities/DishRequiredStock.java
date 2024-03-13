package uk.ac.city.entities;

import jakarta.persistence.*;


@Entity
@SecondaryTable(name = "DishRequiredStock")
class DishRequiredStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;

	@ManyToOne
	@JoinColumn(name = "DishID")
	private Dish dish;

	@ManyToOne
	@JoinColumn(name = "StockID")
	private Stock item;

	@Column(name = "Quantity")
	private Integer quantity;
}
