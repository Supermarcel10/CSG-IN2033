package uk.ac.city.entities;

import jakarta.persistence.*;
import java.util.HashSet;


@Entity
@Table(name = "Chef")
public class Chef {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ChefID")
	private int ID;

	@Column(name = "Name")
	private String name;

	@Column(name = "Role")
	private ChefType role;

	// TODO: Ask customer what their stance is on security
	@Column(name = "Password")
	private String password;

	@OneToMany(mappedBy = "assignedChef")
	private HashSet<Order> orders = new HashSet<>();

	public enum ChefType {
		HEAD_CHEF,
		SOUS_CHEF,
		LINE_CHEF
	}
}
