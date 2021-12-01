package com.olx.entity;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="advertises")
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name ="title")
	private String title;
	
	@Column(name ="price")
	private double price;
	
	@Column(name="category")
	private String category;
	
	@Column(name="description")
	private String description;
	
    @CreationTimestamp
	@Column(name="created_date")
	private LocalDate  createdDate;
   
    @UpdateTimestamp
	@Column(name="modified_date")
	private LocalDate  modifiedDate;
	
	@Column(name="status")
	private String status;
	



	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public LocalDate  getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(LocalDate  createdDate) {
		this.createdDate = createdDate;
	}




	public LocalDate getModifiedDate() {
		return modifiedDate;
	}




	public void setModifiedDate(LocalDate  modifiedDate) {
		this.modifiedDate = modifiedDate;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	@Override
	public String toString() {
		return "PostEntity [id=" + id + ", title=" + title + ", price=" + price + ", categoryId=" + category
				+ ", description=" + description + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", status=" + status + "]";
	}




	public PostEntity(int id, String title, double price, String category, String description, LocalDate  createdDate,
			LocalDate  modifiedDate, String status) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
		this.description = description;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.status = status;
	}




	public PostEntity() {
		super();
	}

	
	
	
}
