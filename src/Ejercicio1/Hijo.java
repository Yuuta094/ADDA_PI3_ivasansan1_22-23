package Ejercicio1;

public record Hijo(Integer p1, Integer p2) {
	
	public static Hijo ofFormat(String[] formato) {
		Integer p1 = Integer.parseInt(formato[0]);
		Integer p2 = Integer.parseInt(formato[1]);
	
		return new Hijo(p1,p2);
	}

	public static Hijo of(Integer p1, Integer p2) {
		return new Hijo(p1,p2);
	}
}
