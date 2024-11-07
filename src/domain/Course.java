package src.domain;

import java.util.List;

public class Course {
    public String nombre;
    private String desc;
    private int duracion;
    private List<String> categories;
    private double price;
    public String instructor;
    private String language;
    private double rating;
    private String imgPath;
    
    public Course(String nombre, String desc, int duracion, List<String> categories, double price,
            String instructor, String language, double rating, int students, String imgPath) {
  this.nombre = nombre;
  this.desc = desc;
  this.duracion = duracion;
  this.categories = categories;
  this.price = price;
  this.instructor = instructor;
  this.language = language;
  this.rating = rating;
  this.imgPath = imgPath;
}

	public String getName() {
		return nombre;
	}

	public void setName(String name) {
		this.nombre = name;
	}

	public String getDescription() {
		return desc;
	}

	public void setDescription(String description) {
		this.desc = description;
	}

	public int getDuration() {
		return duracion;
	}

	public void setDuration(int duration) {
		this.duracion = duration;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}


	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
