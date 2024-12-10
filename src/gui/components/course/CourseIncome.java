package src.gui.components.course;

public class CourseIncome {
    private int idCurso;
    private double porcentaje;

    public CourseIncome(int idCurso, double porcentaje) {
        this.idCurso = idCurso;
        this.porcentaje = porcentaje;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    @Override
    public String toString() {
        return "Curso " + idCurso + ": " + porcentaje + "%";
    }
}