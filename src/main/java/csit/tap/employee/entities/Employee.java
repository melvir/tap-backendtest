package csit.tap.employee.entities;

import java.util.Date;
import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.extern.java.Log;

@javax.persistence.Entity
@Table(name = "Employee")
@Data
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

}