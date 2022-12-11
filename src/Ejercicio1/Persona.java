package Ejercicio1;



public record Persona(Integer id, String nombre, Integer year, String ciudad)  {

	public static Persona ofFormat(String[] formato) {
		Integer id = Integer.parseInt(formato[0]);
		String nombre= formato[1];
		Integer year = Integer.parseInt(formato[2]);
		String ciudad= formato[3];
		return new Persona(id, nombre, year, ciudad);
	}
	
	public static Persona of(Integer id, String nombre, Integer year, String ciudad) {
		return new Persona(id, nombre, year, ciudad);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	} 
	
	
}
