package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	NYCDao dao;
	SimpleWeightedGraph<nta, DefaultWeightedEdge> graph;
	ArrayList<Edge> archi;
	
	public Model() {
		this.dao = new NYCDao();
	}
	
	public List<String> getBoroughs(){
		return dao.getAllBorough();
	}
	
	public void creaGrafo(String s) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		archi = new ArrayList<>();
		List<nta> lista = dao.getAllNTA(s);
		Graphs.addAllVertices(graph, lista);
		
		for(nta n1: lista) {
			for(nta n2: lista) {
				if(n1.getCode().compareTo(n2.getCode()) != 0) {
					Set<String> insieme = n1.getSsid();
					insieme.addAll(n2.getSsid());
					Graphs.addEdge(graph, n1, n2, insieme.size());
					archi.add(new Edge(n1, n2, insieme.size()));
				}
			}
		}
		System.out.println("Il grafo creato ha " + graph.vertexSet().size() + " vertici e " + graph.edgeSet().size() + " archi\n");
	}
	
	public ArrayList<Edge> getMigliori(){
		double peso = 0;
		for(Edge e: archi) {
			peso += e.getPeso();
		}
		peso = peso/archi.size();
		ArrayList<Edge> migliori = new ArrayList<>();
		for(Edge e: archi) {
			if(e.getPeso() > peso)
				migliori.add(e);
		}
		Collections.sort(migliori);
		return migliori;
	}
}
