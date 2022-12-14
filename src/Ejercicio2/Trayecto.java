package Ejercicio2;


public record Trayecto(String c1,String c2, Double precio, Double tiempo) {

	
	public static Trayecto ofFormat(String[] formato) {
		String c1= formato[0];
		String c2= formato[1];
		Double precio = Double.parseDouble(formato[2].replace("euros", ""));
		Double tiempo = Double.parseDouble(formato[3].replace("min", ""));
		
		return new Trayecto(c1,c2,precio,tiempo);
	}
	
	public static Trayecto of(String c1,String c2, Double precio, Double tiempo) {
		return new Trayecto(c1,c2,precio,tiempo);
	}

}