package Ejercicio2;



public record Ciudades(String ciudad, Integer puntuacion)  {

	public static Ciudades ofFormat(String[] formato) {
		String ciudad = formato[0];
		Integer puntuacion = Integer.parseInt(formato[1].replace("p", ""));
		return new Ciudades(ciudad,puntuacion);
	}
	
	public static Ciudades of(String ciudad, Integer puntuacion) {
		return new Ciudades(ciudad,puntuacion);
	}
	
	@Override
	public String toString() {
		return this.ciudad;
	}
}
