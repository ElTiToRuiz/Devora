package src.domain;

import java.util.List;

public class Course {
	private int id;
    public String nombre;
    private String desc;
    private int duracion;
    private String categoria;
    private double price;
    private int clases;
    public String instructor;
    private String language;
    private int students;
    private double rating;
    private int numReseñas;
    private String imgPath;
    
    public Course(int id,String nombre, String desc, int duracion, String categoria, double price,
            int clases, String instructor, String language, double rating, int students, int numReseñas, String imgPath) {
  this.id=id;
  this.nombre = nombre;
  this.desc = desc;
  this.duracion = duracion;
  this.categoria = categoria;
  this.price = price;
  this.clases = clases;
  this.instructor = instructor;
  this.language = language;
  this.rating = rating;
  this.students = students;
  this.numReseñas = numReseñas;
  this.imgPath = imgPath;
}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int getClases() {
		return clases;
	}

	public void setClases(int clases) {
		this.clases = clases;
	}

	public int getStudents() {
		return students;
	}

	public void setStudents(int students) {
		this.students = students;
	}

	public int getNumReseñas() {
		return numReseñas;
	}

	public void setNumReseñas(int numReseñas) {
		this.numReseñas = numReseñas;
	}

	@Override
	public String toString() {
		return "Course{" +
				"nombre='" + nombre + '\'' +
				", desc='" + desc + '\'' +
				", duracion=" + duracion +
				", categoria=" + categoria +
				", price=" + price +
				", clases=" + clases +
				", instructor='" + instructor + '\'' +
				", language='" + language + '\'' +
				", students=" + students +
				", rating=" + rating +
				", numReseñas=" + numReseñas +
				", imgPath='" + imgPath + '\'' +
				'}';
	}
}
