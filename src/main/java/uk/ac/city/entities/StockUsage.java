package uk.ac.city.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "StockUsage")
public class StockUsage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StockUsageID")
	private int ID;

	@ManyToOne
	@JoinColumn(name = "StockID", nullable = false)
	private Stock item;

	@Column(name = "UsageDateTime")
	private LocalDateTime dateTime;

	@Column(name = "UsageType")
	private UsageType usageType;

	@Column(name = "Quantity")
	private int quantity;

	public enum UsageType {
		USED,
		WASTED,
		EXPIRED
	}
}
