package it.polito.tdp.nyc.model;

public class Edge implements Comparable<Edge>{
	nta n1;
	nta n2;
	double peso;
	
	public Edge(nta n1, nta n2, double peso) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}
	
	public nta getN1() {
		return n1;
	}
	public void setN1(nta n1) {
		this.n1 = n1;
	}
	public nta getN2() {
		return n2;
	}
	public void setN2(nta n2) {
		this.n2 = n2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Edge o) {
		return -(int)(this.peso-o.getPeso());
	}
	
}
