package Ejercicio1;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.Set2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class test1 {

	public static void main(String[] args) {
			
			ApartadoA("1A");
			ApartadoB("1A", "Maria");
			//ApartadoC("1A", "Rafael", "Sara");
			ApartadoD("1A");
			ApartadoE("1A");
		}
	
	
	
	
		public static SimpleDirectedGraph<Persona, Hijo> Grafo(String file) {
			SimpleDirectedGraph<Persona, Hijo> g= GraphsReader.newGraph("ficheros/PI3E"+ file + "_DatosEntrada.txt",
					Persona::ofFormat,  //factoria vértices
					Hijo::ofFormat, //factoria aristas
					Graphs2::simpleDirectedGraph //Grafo sin peso y con dirección
					);
			return g;
		}

		
		public static void ApartadoA(String file) {

			SimpleDirectedGraph<Persona, Hijo> g= Grafo(file);
			Predicate<Persona> pv1= v-> mismoAñoyCiudad(g, g.incomingEdgesOf(v));
			Ejercicio1.ejercicio1.apartadoA(g, file,"ApartadoA", pv1);
		}
//AUX
//-------------------------------------------------------------------------------------------------------------------------------\\
		public static Boolean mismoAñoyCiudad (Graph<Persona, Hijo> g, Set<Hijo> hijo) {
			Set<String> ciudades = Set2.empty();
			Set<Integer> year = Set2.empty();
			if(hijo.size()==2) {
				for (Hijo h: hijo) {
					ciudades.add(g.getEdgeSource(h).ciudad());
					year.add(g.getEdgeSource(h).year());
				}
				return year.size()==1 && ciudades.size()==1;
			}
			return false;
		}
//-------------------------------------------------------------------------------------------------------------------------------\\
		
		
		public static void ApartadoB(String file, String nombre) {
			SimpleDirectedGraph<Persona, Hijo> g= Grafo(file);
			Ejercicio1.ejercicio1.apartadoB(g, file,"apartadoB", nombre);
		}
			
		public static void ApartadoC(String file, String p1, String p2) {
		
			SimpleDirectedGraph<Persona, Hijo> g= Grafo(file);
			Ejercicio1.ejercicio1.apartadoC(g, file,"apartadoC", p1, p2);
		}
		
		
		public static void ApartadoD(String file) {
			SimpleDirectedGraph<Persona, Hijo> g= Grafo(file);
			Predicate<Persona> pv1= v-> HijosConDifPadres(g, v);
			Ejercicio1.ejercicio1.apartadoD(g, file,"apartadoD", pv1);
	
		}
//AUX
//-------------------------------------------------------------------------------------------------------------------------------\\
			private static Boolean HijosConDifPadres(SimpleDirectedGraph<Persona, Hijo> g, Persona p) {
				Set<Persona> set = Set2.empty();
				if(g.outDegreeOf(p)>0) {
					for(Hijo edge: g.outgoingEdgesOf(p)) {
						Persona hijo= g.getEdgeTarget(edge);
						for (Hijo edgePadre: g.incomingEdgesOf(hijo)) {
							set.add(g.getEdgeSource(edgePadre));
						}
					}
				}
				
			return set.size()>2;
		}
//-------------------------------------------------------------------------------------------------------------------------------\\


			public static void ApartadoE(String file) {
				Graph<Persona, Hijo> g= GraphsReader.newGraph("ficheros/PI3E"+ file + "_DatosEntrada.txt",
						Persona::ofFormat,  //factoria vértices
						Hijo::ofFormat, //factoria aristas
						Graphs2::simpleGraph //Grafo sin peso y con dirección
						);
				
				Ejercicio1.ejercicio1.apartadoE(g, file, "apartadoE");
				
				
				
			
	}

}
