package Ejercicio3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.Files2;


public class test3 {

public static void main(String[] args) {
		
		ApartadoB("3B");

	}

	public static void ApartadoB(String file) {
		Graph<Actividad, DefaultEdge> g = new SimpleDirectedGraph<Actividad,DefaultEdge>(DefaultEdge.class);
		Files2.streamFromFile("ficheros/PI3E"+ file + "_DatosEntrada.txt").forEach(linea ->{
			String[] s1= linea.trim().split(":");
			String[] s2= s1[1].trim().split(",");
			
			for(int i=0; i<s2.length-1; i++) {
				if(!g.containsVertex(getActividad(s2[i], g))) g.addVertex(Actividad.of(s2[i]));
				for(int j=i+1; j<s2.length; j++) {
					if(!g.containsVertex(getActividad(s2[j], g))) g.addVertex(Actividad.of(s2[j]));
					g.addEdge(getActividad(s2[i], g), getActividad(s2[j], g));
				}
			}
		});
		Ejercicio3.ejercicio3.apartadoAyB(g, file, "ApartadoB");
	}


	private static Actividad getActividad(String nombre, Graph<Actividad, DefaultEdge> g) {
		Actividad res=null;
		for(Actividad a:g.vertexSet()) {
			if(a.nombre().equals(nombre.strip()))
			return a;
		}
		return res;
	}
}
