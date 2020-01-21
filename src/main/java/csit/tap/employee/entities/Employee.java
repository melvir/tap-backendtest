package csit.tap.employee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@javax.persistence.Entity
@Table(name = "Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "Name", columnDefinition = "nvarchar")
	private String name;

	@Column(name = "Department", columnDefinition = "nvarchar")
	private String department;

	@Column(name = "CreatedDateTime", columnDefinition = "datetime")
	private LocalDateTime createdDateTime;

	@Column(name = "ModifiedDateTime", columnDefinition = "datetime")
	private LocalDateTime modifiedDateTime;

	public Employee(String name, String department) {
		this.name = name;
		this.department = department;
	}

	public Employee(String name, String department, LocalDateTime createdDateTime) {
		this.name = name;
		this.department = department;
		this.createdDateTime = createdDateTime;
	}
}