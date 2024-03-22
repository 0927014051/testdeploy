package com.javaweb.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "topping")
public class Topping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long topping_id;
	
	@Column
	private String topping_name;
	
	@Column
	private String image;
	
	@Column
	private Long created_by;
	
	@Column
	private Long updated_by;
	
	@Column
	private LocalDateTime created_at;
	
	@Column
	private LocalDateTime updated_at;
	
	@JsonIgnore
	@OneToMany(mappedBy = "topping")
	private List<Topping_Category> topping_category;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "created_by",updatable = false, insertable =  false)
	private Staff staff_created;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "updated_by",insertable = false, updatable =  false)
	private Staff staff_updated;
	
	public Long getTopping_id() {
		return topping_id;
	}

	public void setTopping_id(Long topping_id) {
		this.topping_id = topping_id;
	}

	public String getTopping_name() {
		return topping_name;
	}

	public void setTopping_name(String topping_name) {
		this.topping_name = topping_name;
	}

	public Long getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Long created_by) {
		this.created_by = created_by;
	}

	public Long getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Long updated_by) {
		this.updated_by = updated_by;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public List<Topping_Category> getTopping_category() {
		return topping_category;
	}

	public void setTopping_category(List<Topping_Category> topping_category) {
		this.topping_category = topping_category;
	}

	public Staff getStaff_created() {
		return staff_created;
	}

	public void setStaff_created(Staff staff_created) {
		this.staff_created = staff_created;
	}

	public Staff getStaff_updated() {
		return staff_updated;
	}

	public void setStaff_updated(Staff staff_updated) {
		this.staff_updated = staff_updated;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Topping(Long topping_id, String topping_name, String image, Long created_by, Long updated_by,
			LocalDateTime created_at, LocalDateTime updated_at, List<Topping_Category> topping_category,
			Staff staff_created, Staff staff_updated) {
		super();
		this.topping_id = topping_id;
		this.topping_name = topping_name;
		this.image = image;
		this.created_by = created_by;
		this.updated_by = updated_by;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.topping_category = topping_category;
		this.staff_created = staff_created;
		this.staff_updated = staff_updated;
	}

	public Topping() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
