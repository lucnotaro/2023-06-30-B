package it.polito.tdp.exam.model;

public class Successore implements Comparable<Successore> {
	
	private Integer year;
	private Integer peso;
	public Successore(Integer year, Integer peso) {
		super();
		this.year = year;
		this.peso = peso;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Successore o) {
		return this.peso-o.getPeso();
	}
	
	
}
