package br.com.drrockenbach.java8.livrocasacodigo.capitulo8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class FlatMap {

	public static void main(String[] args) throws IOException {

		lendoArquivos();
		
		trabalhandoComListaDeLista();
		
	}

	private static void trabalhandoComListaDeLista() {
		
		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 90);
		
		Grupo englishSpeakers = new Grupo();
		englishSpeakers.add(user1);
		englishSpeakers.add(user2);
		
		Grupo spanishSpeakers = new Grupo();
		
		spanishSpeakers.add(user2);
		spanishSpeakers.add(user3);
		
		List<Grupo> groups = Arrays.asList(englishSpeakers, spanishSpeakers);
		
		/**
		 * Pode ser que queiramos todos os usuários desses grupos. Se fizermos um
		 * simples groups.stream().map(g -> g.getUsuarios().stream()), teremos um
		 * Stream<Stream<Usuario>>, que não desejamos. O flatMap vai desembrulhar esses
		 * Streams, achatando-os.
		 */
		
		groups.stream()
			.flatMap(g -> g.getUsuarios().stream())
			.distinct()
			.forEach(System.out::println);
		
	}

	private static void lendoArquivos() throws IOException {
		/**
		 * Podemos achatar um Stream de Streams com o flatMap. Basta trocar a invocação,
		 * que teremos no final um Stream<String>:
		 */
		
		Stream<String> strings=
				Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
				.filter(p -> p.toString().endsWith(".java"))
				.flatMap(p -> lines(p));

		IntStream chars=
				Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
				.filter(p -> p.toString().endsWith(".java"))
				.flatMap(p -> lines(p))
				.flatMapToInt((String s) -> s.chars());
		
		chars.forEach(System.out::println);
	}
	
	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch(IOException e){
			
			throw new UncheckedIOException(e);
		}
	}
	
	static class Grupo {
		private Set<Usuario> usuarios = new HashSet<>();

		public void add(Usuario u) {
			usuarios.add(u);
		}

		public Set<Usuario> getUsuarios() {
			return Collections.unmodifiableSet(this.usuarios);
		}
	}
	
	

}
