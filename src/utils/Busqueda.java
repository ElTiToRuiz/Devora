package src.utils;

import java.util.ArrayList;
import java.util.List;

import src.gui.components.course.CourseFront;

public class Busqueda {

	public static ArrayList<CourseFront> filtrarCursos(ArrayList<CourseFront> cursos, String busqueda, int index, ArrayList<CourseFront> resultado) {
	    // Validación de la lista
	    if (cursos == null) {
	        throw new IllegalArgumentException("La lista de cursos no puede ser null.");
	    }

	    // Caso base
	    if (index >= cursos.size()) {
	    	for(CourseFront curso2 : resultado) {
	    		System.out.println(curso2.getCourseName());
	    	}
	        return resultado;
	    }

	    CourseFront curso = cursos.get(index);
	    
	    // Validación del curso y su nombre
	    if (curso != null && curso.getCourseName() != null && curso.getCourseName().toLowerCase().contains(busqueda.toLowerCase())) {
	        resultado.add(curso);
	    }

	    // Llamada recursiva
	    return filtrarCursos(cursos, busqueda, index + 1, resultado);
	}
}
