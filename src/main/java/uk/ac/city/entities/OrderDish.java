package uk.ac.city.entities;

import jakarta.persistence.*;


@Entity
@SecondaryTable(name = "OrderDish")
class OrderDish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;

	@ManyToOne
	@JoinColumn(name = "OrderID")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "DishID")
	private Dish dish;

	@Column(name = "Quantity")
	private int quantity;
}
