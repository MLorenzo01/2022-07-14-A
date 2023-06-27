package it.polito.tdp.nyc.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;



public class Simulazione {

	double p;
	int d;
	private PriorityQueue<Event> queue ;
	
	// Stato del sistema
	private Graph<nta, DefaultWeightedEdge> grafo;
	private Map<nta, Integer> numShare;

	private List<nta> vertici;

	// Output
	private Map<nta, Integer> numTotShare;

	
	public Simulazione(Graph<nta, DefaultWeightedEdge> grafo, double p, int d) {
		this.grafo = grafo;
		this.d = d;
		this.p = p;
	}
	public void initialize() {
		this.numShare = new HashMap<nta, Integer>();
		this.numTotShare = new HashMap<nta, Integer>();

		for (nta n : this.grafo.vertexSet()) {
			this.numShare.put(n, 0);
			this.numTotShare.put(n, 0);
		}

		this.vertici = new ArrayList<>(this.grafo.vertexSet());

		this.queue = new PriorityQueue<Event>();

		// creo eventi iniziali
		for (int t = 0; t < 100; t++) {
			if (Math.random() <= this.p) {
				int n = (int) (Math.random() * this.vertici.size());
				this.queue.add(new Event(t, EventType.SHARE, this.vertici.get(n), this.d));
			}
		}
	}
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			if (e.getGiorno() >= 100)
				break;

			int time = e.getGiorno();
			int duration = e.getDuration();
			nta nta = e.getNt();

//			System.out.println(e.getType() + " " + time + " " + nta.getNTACode() + " " + duration);

			switch (e.getEv()) {
			case SHARE:
				this.numShare.put(nta, this.numShare.get(nta) + 1);
				this.numTotShare.put(nta, this.numTotShare.get(nta) + 1);

				this.queue.add(new Event(time + duration, EventType.STOP, nta, 0));

				// ri-condivisione
				if (duration / 2 > 0) {
					nta nuovo = trovaNTA(nta);
					if (nuovo != null) {
						this.queue.add(new Event(time + 1,  EventType.SHARE, nuovo, duration / 2));
					}
				}

				break;

			case STOP:
				this.numShare.put(nta, this.numShare.get(nta) - 1);
				break;
			}
		}
	}

	private nta trovaNTA(nta nta) {
		int max = -1;
		nta best = null;

		for (DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(nta)) {
			nta vicino = Graphs.getOppositeVertex(this.grafo, e, nta);
			int peso = (int) this.grafo.getEdgeWeight(e);

			if (peso > max && this.numShare.get(vicino) == 0) {
				max = peso;
				best = vicino;
			}
		}

		return best;
	}

	public Map<nta, Integer> getNumTotShare() {
		return numTotShare;
	}
			
}
