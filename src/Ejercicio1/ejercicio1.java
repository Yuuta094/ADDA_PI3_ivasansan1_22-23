package Ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Map2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.views.SubGraphView;

public class ejercicio1 {

	
	
/*
 * COSAS A PREGUNTAR
 * - como se pone  para bajar un dato en el grafo (a la hora de mostrarlo)
 * - apartado b
 * - resultado del apartado c
 * - COMO CHUCHAS SE HACE EL C QUE NO FUFA NI COPIANDO
 */


	/*ESTE FUFA
	 * Apartadp A: Obtenga una vista del grafo que sólo incluya las personas cuyos
	 * padres aparecen en el grafo, y ambos han nacido en la misma ciudad y en el
	 * mismo año. Muestre el grafo configurando su apariencia de forma que se
	 * resalten los vértices y las aristas de la vista.
	 */

	public static void apartadoA(Graph<Persona, Hijo> g, String file, String nombreVista, Predicate<Persona> p) {

		Graph<Persona, Hijo> gf= SubGraphView.of(g,p,null);

		GraphColors.toDot(
				g, // grafo al que se le va a aplicar
				"resultados/ejercicio1/Ejercicio_" + file+ "_"  + nombreVista  + ".gv", // fichero de salida
				v -> v.nombre(), // nombre vertices
				e -> "",// nombre aristas
				v -> GraphColors.colorIf(Color.blue, gf.containsVertex(v)), // Colorear vértices (con condición, si un vértice tiene más de una arista si pinta)
				e -> GraphColors.colorIf(Color.black, gf.containsEdge(e))); // Colorear aristas
		
	}
	
	/* FUFAAA
	 * Apartado B: Implemente un algoritmo que dada una persona devuelva un conjunto con todos
	 * sus ancestros que aparecen en el grafo. Muestre el grafo configurando su
	 * apariencia de forma que se resalte la persona de un color y sus ancestros de otro. 
	 */
	
	public static void apartadoB(SimpleDirectedGraph<Persona, Hijo> g, String file, String nombreVista, String nombre) {

		Persona p = g.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();  //Obtenemos la raiz 
		Graph<Persona, Hijo> gf = Graphs2.inversedDirectedGraph(g);  //Nuevo grafo dirijido pero invertido	
		DepthFirstIterator<Persona, Hijo> dfi = new DepthFirstIterator<>(gf, p); //busqueda en profundidad
		
		
		List<Persona> ancestros=new ArrayList<>(); //Lista con todos los ancestros 
		dfi.forEachRemaining(v->ancestros.add(v));
		ancestros.remove(0);  //removemos el primero que es la raiz 	
		
		Map<Persona, Color> map= Map2.empty();
		
		for(Persona per : g.vertexSet()) {  // asignación del color
			if(per.equals(p)) {
				map.put(p, Color.red);
			}
			else if(ancestros.contains(per)) {
				map.put(per, Color.blue);
			}
			else {
				map.put(per, Color.black);
			}
		}
				GraphColors.toDot(
				g, // grafo al que se le va a aplicar
				"resultados/ejercicio1/Ejercicio_" + file + "_" + nombreVista + ".gv", // fichero de salida
				v -> v.nombre(), // nombre vertices
				e -> "",// nombre aristas
		
				v -> GraphColors.color(map.get(v)), // Colorear vértices (con condición, si un vértice tiene más de una arista si pinta)
				e -> GraphColors.color(Color.black)); // Colorear aristas
		
	}
	
	/* NO FUFAA:(
	 * HACE FALTA UN ENUMERADO?
	 * 
	 * Apartado C: Implemente un algoritmo que dadas dos personas devuelva un valor entre los
	 * posibles del enumerado {Hermanos, Primos, Otros} en función de si son
	 * hermanos, primos hermanos, o ninguna de las dos cosas. Tenga en cuenta que 2
	 * personas son hermanas en caso de que tengan al padre o a la madre en común, y
	 * primas en caso de tener al menos un abuelo/a en común
	 */
	


	//aplicar Dijstrack
	

	public static void apartadoC(SimpleDirectedGraph<Persona, Hijo> g, String file, String nombreVista, String p1, String p2) {
		
			
			
			Persona per1 = g.vertexSet().stream().filter(c->c.nombre().equals("Sara")).findFirst().get();  //Obtenemos la raiz 
			Persona per2 = g.vertexSet().stream().filter(c->c.nombre().equals("Rafael")).findFirst().get();  //Obtenemos la raiz 
			
			DijkstraShortestPath<Persona, Hijo> alg= new DijkstraShortestPath<>(g); 
	
			Integer len= alg.getPath(per1,per2).getLength();
			
	
			if(len==2) {
				System.out.println(p1+" y "+ p2+ " son Hermanos");
			}else if (len == 4) {
				System.out.println(p1+" y "+ p2+ " son Primos");
			}else {
				System.out.println(p1+" y "+ p2+ " son Otros");
			}

}
	
	/* FUFA BINE :)
	 * Apartado D: Implemente un algoritmo que devuelva un conjunto con todas las personas
	 * que tienen hijos/as con distintas personas. Muestre el grafo configurando su
	 * apariencia de forma que se resalten las personas de dicho conjunto.
	 */

	
	public static void apartadoD(Graph<Persona, Hijo> g, String file, String nombreVista, Predicate<Persona> p) {

		Graph<Persona, Hijo> gf= SubGraphView.of(g,p,null);
		
				GraphColors.toDot(
				g, // grafo al que se le va a aplicar
				"resultados/ejercicio1/Ejercicio_" + file + "_"+ nombreVista  + ".gv", // fichero de salida
				v -> v.nombre(), // nombre vertices
				e -> "",// nombre aristas
				v -> GraphColors.colorIf(Color.blue, gf.containsVertex(v)), // Colorear vértices 
				e -> GraphColors.color(Color.black)); // Colorear aristas
	
	}

	/* FUFA SUUUUUU
	 * Apartado E: Se desea seleccionar el conjunto mínimo de personas para que se cubran
	 * todas las relaciones existentes. Implemente un método que devuelva dicho
	 * conjunto. Muestre el grafo configurando su apariencia de forma que se
	 * resalten las personas de dicho conjunto.
	 */

	public static void apartadoE(Graph<Persona, Hijo> g, String file, String nombreVista) {
				
		GreedyVCImpl<Persona,Hijo> vCover = new GreedyVCImpl<Persona, Hijo>(g); //obeter la mínima cantidad de V que tocan al resto de V
		Set<Persona> vertices = vCover.getVertexCover();
		
		GraphColors.toDot(g,"resultados/ejercicio1/Ejercicio_" + file + "_"+ nombreVista  + ".gv",
				v->v.nombre(),
				e->"",
				v->GraphColors.colorIf(Color.blue, vertices.contains(v)),
				e->GraphColors.style(Style.solid));
		
		System.out.println(vertices);
	
	}
	

}
