package uk.ac.city.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;


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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private HashSet<OrderDish> dishes;
}
