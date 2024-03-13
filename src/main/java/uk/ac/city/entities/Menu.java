package uk.ac.city.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;


@Entity
@Table(name = "Menu")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MenuID")
	private int ID;

	@Column(name = "StartDate")
	private LocalDateTime startDate;

	@Column(name = "EndDate")
	private LocalDateTime endDate;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private HashSet<Dish> dishes = new HashSet<>();
}
