package br.com.drrockenbach.java8.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

class Curso {

	private String nome;
	private int alunos;
	
	public Curso(String nome, int alunos) {
		this.nome = nome;
		this.alunos = alunos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAlunos() {
		return alunos;
	}
	public void setAlunos(int alunos) {
		this.alunos = alunos;
	}
	
	
}

public class ExemploCursos {
	
	public static void main(String[] args) {
		
		List<Curso> cursos = new ArrayList<>(); 
	
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java 8", 113));
		cursos.add(new Curso("C", 55));
		
		/**
		 * Ordena pela quantidade de alunos
		 */
		cursos.sort(Comparator.comparing(c -> c.getAlunos()));
//		cursos.sort(Comparator.comparing(Curso::getAlunos));
		
		cursos.forEach(c -> System.out.println(c.getNome()));
		System.out.println("----------------------");
		/**
		 * O Stream não altera a lista original, ele gera uma nova lista/coleção
		 */
		
		int sum = cursos.stream()
			.filter(c -> c.getAlunos() >= 100)
//			.map(c -> c.getAlunos()) // o map nesse caso não teria a opção do método sum;
			.mapToInt(c -> c.getAlunos()) // Esse método para int é melhor por que tem mais opções para trabalhar com o resultado, por exemplo o sum abaixo
			.sum();
//			.forEach(total -> System.out.println(total));
		
		System.out.println(sum);
		
		OptionalDouble media = cursos.stream()
				.filter(c -> c.getAlunos() >= 100)
				.mapToInt(c -> c.getAlunos())
				.average();
			
		System.out.println("Media alunos: "+media.orElse(0));
		
		
		Optional<Curso> optionalCurso =	cursos.stream().filter(c -> c.getAlunos() >= 100).findAny();
		Curso curso = optionalCurso.orElse(null);
		System.out.println(curso.getNome());
		
		// Se tiver o curso dentro do option, vai chamar ifPresent, caso contrário não faz nada. Isso é bom para substituir as validações de null com if
		optionalCurso.ifPresent(c -> System.out.println(c.getNome()));
		
		cursos = cursos.stream()
				.filter(c -> c.getAlunos() >= 100)
				.collect(Collectors.toList());
		
		cursos.stream()
			.findAny()
			.ifPresent(c -> System.out.println(" ----" +c.getNome()));
		
		
		Map<String, Integer> map = cursos.stream()
				.filter(c -> c.getAlunos() >= 100)
				.collect(Collectors.toMap(c -> c.getNome(), c -> c.getAlunos()));
		
		System.out.println(map);

		// ****** parallelStream roda em paralelo, com mais threads. Utilizar apenas para processamento mais pesado.
		cursos.parallelStream()
				.filter(c -> c.getAlunos() >= 100)
				.collect(Collectors.toMap(c -> c.getNome(), c -> c.getAlunos()))
				.forEach((nome, alunos) -> System.out.println(nome +" tem "+ alunos +" alunos"));
				;
		
	}
	
}