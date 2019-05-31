package br.com.drrockenbach.java8.livrocasacodigo.capitulo8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

public class ClassificacaoCampeonato {

	public static void main(String[] args) {
		
		List<Time> times = new ArrayList<>();
		
		geraTimes(times);
		
		times.stream().sorted(Comparator.comparing(Time::getPontos).reversed()).forEach(System.out::println);
		
		times.stream().max(Comparator.comparing(Time::getPontos)).ifPresent(t -> System.out.println("Campeão: "+t.getNome()));
		times.stream().min(Comparator.comparing(Time::getPontos)).ifPresent(t -> System.out.println("Rebaixado: "+t.getNome()));
		
		
		
		String reduce = times.stream().sorted(Comparator.comparing(Time::getPontos).reversed()).limit(2)
			.map(Time::getNome).reduce("Libertadores: ", (acumulador, time) -> acumulador +" "+ time);
		
		System.out.println(reduce);
		
	}

	private static void geraTimes(List<Time> times) {
		
		times.add(new Time("Grêmio", 10, 6, 2, 2));
		times.add(new Time("Inter", 10, 5, 1, 3));
		times.add(new Time("São Paulo", 10, 4, 4, 2));
		times.add(new Time("Flamengo", 10, 6, 3, 1));
	}
	
}

class Time {
	private String nome;
	private Integer jogos;
	private Integer vitorias;
	private Integer derrotas;
	private Integer empates;
	
	public Time(String nome, Integer jogos, Integer vitorias, Integer derrotas, Integer empates) {
		super();
		this.nome = nome;
		this.jogos = jogos;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
		this.empates = empates;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getJogos() {
		return jogos;
	}
	public void setJogos(Integer jogos) {
		this.jogos = jogos;
	}
	public Integer getVitorias() {
		return vitorias;
	}
	public void setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
	}
	public Integer getDerrotas() {
		return derrotas;
	}
	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}
	public Integer getEmpates() {
		return empates;
	}
	public void setEmpates(Integer empates) {
		this.empates = empates;
	}
	
	public Integer getPontos() {
		return (vitorias * 3) + (empates * 1);
	}
	@Override
	public String toString() {
		return "Time [nome=" + nome + ", jogos=" + jogos + ", vitorias=" + vitorias + ", derrotas=" + derrotas
				+ ", empates=" + empates + ", getPontos()=" + getPontos() + "]";
	}
	
}