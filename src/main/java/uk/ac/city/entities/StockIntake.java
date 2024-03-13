package uk.ac.city.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "StockIntake")
public class StockIntake {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StockIntakeID")
	private int ID;

	@ManyToOne
	@JoinColumn(name = "StockID", nullable = false)
	private Stock item;

	@Column(name = "IntakeDateTime")
	private LocalDateTime dateTime;

	@Column(name = "Quantity")
	private int quantity;
}
