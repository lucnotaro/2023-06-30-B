package it.polito.tdp.exam.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;

public class Model {
	
	private BaseballDAO dao;
	private List<Integer> allNodes;
	private Graph<Integer,DefaultWeightedEdge> graph;
	
	public Model() {
		this.dao=new BaseballDAO();
		this.allNodes=new ArrayList<>();
	}
	
	private void loadNodes(String team) {
		if(this.allNodes.isEmpty())
			this.allNodes.addAll(this.dao.getYearsOf(team));
	}
	
	public void buildGraph(String team) {
		this.allNodes.clear();
		this.loadNodes(team);
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph,this.allNodes);
		for(Integer y1:this.graph.vertexSet()) {
			for(Integer y2:this.graph.vertexSet()) {
				if(y1!=y2 && this.graph.getEdge(y1,y2)==null) {
					Graphs.addEdge(this.graph,y1,y2,this.getWeight(team,y1,y2));
				}
			}
		}
//		System.out.println("v: "+this.graph.vertexSet().size());
//		System.out.println("e: "+this.graph.edgeSet().size());
	}
	
	public List<String> getTeams(){
		return this.dao.getTeams();
	}
	
	public List<Integer> getNodes(){
		return this.allNodes;
	}
	
	public Integer getWeight(String t,Integer y1,Integer y2) {
		return Math.abs(this.dao.getTotSalaryOfIn(t,y1)-this.dao.getTotSalaryOfIn(t,y2));
	}
	
	public Integer getVsize() {
		return this.graph.vertexSet().size();
	}
	public Integer getEsize() {
		return this.graph.edgeSet().size();
	}
	
	public List<Successore> getSuccessorsOf(Integer year){
		List<Successore> successori=new ArrayList<>();
		for(Integer y:Graphs.successorListOf(this.graph,year)) {
			successori.add(new Successore(y,(int)this.graph.getEdgeWeight(this.graph.getEdge(year,y))));
		}
		Collections.sort(successori);
		return successori;
	}
}
