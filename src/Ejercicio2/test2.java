package Ejercicio2;



import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import Ejercicio1.Hijo;
import Ejercicio1.Persona;
import us.lsi.common.Map2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class test2 {

	public static void main(String[] args) {
		
		ApartadoAyB("2");
		ApartadoC("2");
		ApartadoD("2");
	}

	
	public static Graph<Ciudades, Trayecto> Grafo(String file) {
		Graph<Ciudades, Trayecto> g= GraphsReader.newGraph("ficheros/PI3E"+ file + "_DatosEntrada.txt",
				Ciudades::ofFormat,  //factoria vértices
				Trayecto::ofFormat, //factoria aristas
				Graphs2::simpleGraph //Grafo sin peso y sin dirección
				);
		return g;
	}
	
	public static void ApartadoAyB(String file) {
		Graph<Ciudades, Trayecto> g= Grafo(file);
		
		Ejercicio2.ejercicio2.apartadoA(g, file,"apartadoA");
		System.out.println("-------------------------------"+"\n");
		Ejercicio2.ejercicio2.apartadoB(g, file,"apartadoB");
		
	}
	
	public static void ApartadoC(String file) {
		
		SimpleWeightedGraph<Ciudades,Trayecto> g= GraphsReader.newGraph("ficheros/PI3E"+ file + "_DatosEntrada.txt",
				Ciudades::ofFormat,  //factoria vértices
				Trayecto::ofFormat, //factoria aristas
				Graphs2::simpleWeightedGraph, //Grafo 
				Trayecto::precio
				);
		System.out.println("-------------------------------"+"\n");
		Ejercicio2.ejercicio2.apartadoC(g, file,"apartadoC");
}
	
	public static void ApartadoD(String file) {
		
		SimpleWeightedGraph<Ciudades,Trayecto> g= GraphsReader.newGraph("ficheros/PI3E"+ file + "_DatosEntrada.txt",
				Ciudades::ofFormat,  //factoria vértices
				Trayecto::ofFormat, //factoria aristas
				Graphs2::simpleWeightedGraph, //Grafo 
				Trayecto::tiempo
				);
		System.out.println("-------------------------------"+"\n");
		
		Map<GraphPath<Ciudades, Trayecto>, Double> map = Map2.empty();
		ConnectivityInspector<Ciudades, Trayecto> alg1 = new ConnectivityInspector<>(g);
		Integer compoConexa=1;
		for (Set<Ciudades> set : alg1.connectedSets()) {  //Por cada  componente conexa...
			Graph<Ciudades, Trayecto> vista = SubGraphView.of(g, v -> set.contains(v), null); //creo  una vista con los verx de la componente
			FloydWarshallShortestPaths<Ciudades, Trayecto> alg2 = new FloydWarshallShortestPaths<>(vista); //Camino mínimo
			for (Ciudades c1 : set) {
				for (Trayecto t1 : g.edgesOf(c1)) {
					for (Trayecto t2 : g.edgesOf(g.getEdgeTarget(t1))) {
						Ciudades c2 = g.getEdgeTarget(t2);
						Integer saltos = alg2.getPath(c1, c2).getLength();
						if (saltos > 1 && g.edgesOf(c2)
								.stream()
								.allMatch(a -> g.getEdgeTarget(a) != c1 && g.getEdgeSource(a) != c1)) {
							map.put(alg2.getPath(c1, c2), alg2.getPathWeight(c1, c2));
						}
					}
				}
			}
		GraphPath<Ciudades, Trayecto> barata = Collections.min(map.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
		System.out.println("Barata: " + barata + "---" + map.get(barata));		
		Predicate<Ciudades> pv = p -> barata.getVertexList().contains(p);
		Predicate<Trayecto> pe = e -> barata.getEdgeList().contains(e);
	
		Ejercicio2.ejercicio2.apartadoD(g, file,"apartadoD"+compoConexa, pv, pe);
		compoConexa++;
	}
	
}
		
	

}
