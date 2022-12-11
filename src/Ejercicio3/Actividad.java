package Ejercicio3;



public record Actividad(Integer id, String nombre)  {

	public static Integer i=0;
	
	public static Actividad of(String nombre) {
		Integer id = i; i++;
		return new Actividad(id,nombre.trim());
	}
	
	public static Actividad of(Integer id, String nombre) {
		return new Actividad(id, nombre.trim());
	}
	
	@Override
	public String toString() {
		return this.nombre();
	}

	
}
