package Ejercicio3;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;


public class ejercicio3 {

	public static void apartadoAyB(Graph<Actividad, DefaultEdge> g, String file, String nombreVista) {
		
		VertexColoringAlgorithm<Actividad> verColor= new GreedyColoring<>(g);
		Coloring<Actividad> sol =verColor.getColoring();  //obetener los colores
		
		//Apartado A
		System.out.println("Numero de franjas: "+ sol.getNumberColors());
		List<Set<Actividad>> franjas = sol.getColorClasses();
		
		for(int i=0; i< franjas.size() ;i++) {
			System.out.println("Franja numero "+i+ franjas.get(i));
		}
		Map<Actividad, Integer> map= sol.getColors();
		
		GraphColors.toDot(g, // grafo al que se le va a aplicar
				"resultados/ejercicio3/Ejercicio_" + file + "_" + nombreVista + ".gv", // fichero de salida
				v -> v.toString(), // nombre vertices
				e -> "", // nombre aristas
				v -> GraphColors.color(map.get(v)), // Colorear vértices 
				e -> GraphColors.color(Color.black)// Colorear aristas
		);
		
	}

}
