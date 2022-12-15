package Ejercicio2;

import java.text.NumberFormat.Style;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.Map.Entry;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import Ejercicio1.Hijo;
import Ejercicio1.Persona;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Map2;
import us.lsi.common.Preconditions;
import us.lsi.common.Set2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;



public class ejercicio2 {

	/*
	 * FUFA :) 
	 * Apartado A: Determine cuántos grupos de Ciudades hay y cuál es su
	 * composición. Dos Ciudades pertenecen al mismo grupo si están relacionadas
	 * directamente entre sí o si existen algunas Ciudades intermedias que las
	 * relacionan. Muestre el grafo configurando su apariencia de forma que se
	 * coloree cada grupo de un color diferente.
	 * 
	 */

	private static Color asignaColor(Ciudades v, List<Set<Ciudades>> ls,
			ConnectivityInspector<Ciudades, Trayecto> alg) {

		Color[] vc = Color.values(); // Array de colores
		Set<Ciudades> s = alg.connectedSetOf(v); // Busco el vertice de las componentes concexas
		return vc[ls.indexOf(s)]; // Asigna el color de la componente en la que está
	}

	public static void apartadoA(Graph<Ciudades, Trayecto> g, String file, String nombreVista) {

		var alg = new ConnectivityInspector<>(g);
		List<Set<Ciudades>> ls = alg.connectedSets();
		
		System.out.println("Hay "+ls.size()+" grupos de Ciudades");
		for (int i=0; i<ls.size(); i++) {
			System.out.println("Grupo " +i+ ": "+ls.get(i));
		}
		
		GraphColors.toDot(g, // grafo al que se le va a aplicar
				"resultados/ejercicio2/Ejercicio_" + file + "_" + nombreVista + ".gv", // fichero de salida
				v -> v.ciudad(), // nombre vertices
				e -> "", // nombre aristas
				v -> GraphColors.color(asignaColor(v, ls, alg)), // Colorear vértices 
				e -> GraphColors.color(asignaColor(g.getEdgeSource(e), ls, alg))// Colorear aristas
		);
	}
	
	/*FUFAAA
	 * 
	 * Apartado B: Determine cuál es el grupo de Ciudades a visitar si se deben elegir las
	 * Ciudades conectadas entre sí que maximice la suma total de las puntuaciones.
	 * Muestre el grafo configurando su apariencia de forma que se resalten dichas
	 * Ciudades.
	 */
	
	
	
	public static void apartadoB(Graph<Ciudades, Trayecto> g, String file, String nombreVista) {

		var alg = new ConnectivityInspector<>(g);
		Map<Set<Ciudades>, Integer> map= Map2.empty();
		Map<Set<Ciudades>, Integer> CiudadesMaxPuntuada= Map2.empty();
		
		for (Set<Ciudades> set: alg.connectedSets()) { //obeter la suma de todas las putuaciones
			Integer suma=0;
			for (Ciudades ci: set) {
				suma+=ci.puntuacion();
			}
			map.put(set,suma);			
		}
		Integer maxValue=0;
		for(int i=0; i<map.size(); i++) {  //Obtener el conjunto de Ciudades mayor valoradas
			Set<Ciudades>Ciudades=map.keySet().stream().toList().get(i);
			Integer p= map.values().stream().toList().get(i);
			if( p > maxValue) {
				maxValue=p;
			CiudadesMaxPuntuada.put(Ciudades, maxValue);
			}	
		}
		//Para mostrar las aristar del subgrafo tamb del mismo color
		Predicate<Ciudades> p= v-> alg.connectedSets().get(0).contains(v);
		Graph<Ciudades,Trayecto> gf= SubGraphView.of(g, p, null);
	
	
		var sol= CiudadesMaxPuntuada.entrySet().stream().sorted(Map.Entry.comparingByValue()).findFirst().get();
		System.out.println("Grupo de Ciudades que maximiza la suma de las puntuaciones "+ sol);

	
		GraphColors.toDot(g, // grafo al que se le va a aplicar
				"resultados/ejercicio2/Ejercicio_" + file + "_" + nombreVista + ".gv", // fichero de salida
				v -> v.ciudad(), // nombre vertices
				e -> "", // nombre aristas
				v -> GraphColors.colorIf(Color.blue, sol.getKey().contains(v)), // Colorear vértices 
				e -> GraphColors.colorIf(Color.blue, gf.containsEdge(e))// Colorear aristas
				
		);
	}
	
	

	/*  FUFAA
	 * Apartado C: Determine cuál es el grupo de Ciudades a visitar si se deben elegir las
	 * Ciudades conectadas entre sí que den lugar al camino cerrado de menor precio
	 * que pase por todas ellas. Muestre el grafo configurando su apariencia de
	 * forma que se resalte dicho camino
	 */
	


	//AUX (Si no queda mu tocho el apartado C con esto detro)
	public static Set<Ciudades> grupoMasBarato(Set<Entry<Set<Ciudades>, Double>> input) {
		Double minPuntos = 1.;
		Boolean primero = true;
		Set<Ciudades> res = Set2.empty();
		for (Entry<Set<Ciudades>, Double> set : input) {
			if (primero) {
				minPuntos = set.getValue();
				res = set.getKey();
				primero = false;
			} else if (set.getValue() < minPuntos) {
				minPuntos = set.getValue();
				res = set.getKey();
			}
		}
		return res;
	}

	
	public static void apartadoC(Graph<Ciudades, Trayecto> g, String file, String nombreVista) {
		
		Map<Set<Ciudades>, Double> map = Map2.empty(); 
		ConnectivityInspector<Ciudades, Trayecto> alg1 = new ConnectivityInspector<>(g);
		HeldKarpTSP<Ciudades, Trayecto> alg2 = new HeldKarpTSP<Ciudades, Trayecto>();  // el camino mínimo (hamilton)
		
		for (Set<Ciudades> set : alg1.connectedSets()) {
			
			Graph<Ciudades, Trayecto> vista = SubGraphView.of(g, v -> set.contains(v), null); //vista con solo los vertices de una componete conexa
								
			GraphPath<Ciudades, Trayecto> path = alg2.getTour(vista);
			
			List<Trayecto> camino = path.getEdgeList(); //las aristas basicamente

			Double precio = 0.;				//igual que en el apartado B
			for (Trayecto t : camino)		//calculamos el total del precio de ambas componetes
				precio += t.precio();
			map.put(set, precio);
		}
		
		
		for (Entry<Set<Ciudades>, Double> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "===>" + entry.getValue() + "euros");
		}
		
		Set<Ciudades> barato = grupoMasBarato(map.entrySet());
		Graph<Ciudades, Trayecto> vista = SubGraphView.of(g, v -> barato.contains(v), null);
		List<Trayecto> camino = alg2.getTour(vista).getEdgeList();

		GraphColors.toDot(g, // grafo al que se le va a aplicar
				"resultados/ejercicio2/Ejercicio_" + file + "_" + nombreVista + ".gv", // fichero de salida
				v -> v.ciudad(), // nombre vertices
				e -> e.precio().toString(), // nombre aristas
				v->GraphColors.colorIf(Color.blue,barato.contains(v)), // Colorear vértices 
				e->GraphColors.colorIf(Color.blue,camino.contains(e))); // Colorear aristas
	}
	
	
	/*
	 * Apartado D: De cada grupo de Ciudades, determinar cuáles son las 2 Ciudades (no
	 * conectadas directamente entre sí) entre las que se puede viajar en un menor
	 * tiempo. Muestre el grafo configurando su apariencia de forma que se resalten
	 * las Ciudades y el camino entre ellas.
	 */
	
	public static void apartadoD(Graph<Ciudades, Trayecto> g, String file, String nombreVista, Predicate<Ciudades> pv, Predicate<Trayecto>pe) {

			Graph<Ciudades, Trayecto> gf = SubGraphView.of(g, pv, pe); // grafo solo con la solución
			
			GraphColors.toDot(g, // grafo al que se le va a aplicar
					"resultados/ejercicio2/Ejercicio_" + file + "_" + nombreVista + ".gv", // fichero de salida
					v -> v.ciudad(), // nombre vertices
					e -> e.precio().toString(), // nombre aristas
					v -> GraphColors.colorIf(Color.blue, gf.containsVertex(v)), // Colorear vértices
					e -> GraphColors.colorIf(Color.blue, gf.containsEdge(e))); // Colorear aristas		
		}
}
