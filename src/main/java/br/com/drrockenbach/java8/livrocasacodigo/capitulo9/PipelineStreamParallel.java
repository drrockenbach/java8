package br.com.drrockenbach.java8.livrocasacodigo.capitulo9;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class PipelineStreamParallel {

	public static void main(String[] args) {

		/**
		 * As collections oferecem uma implementação de Stream diferente, o stream
		 * paralelo. Ao usarumstreamparalelo, ele vai decidir quantas threads deve
		 * utilizar, como deve quebrar o processamento dos dados e qual será a forma de
		 * unir o resultado final em um só. Tudo isso sem você ter de configurar nada.
		 * Basta apenas invocar parallelStream em vez de Stream:
		 */

		List<Usuario> usuarios = getUsuarios();
		
		filtrarSemParallelStream(usuarios);
		
		filtrarComParallelStream(usuarios);
		
	}

	private static void filtrarComParallelStream(List<Usuario> usuarios) {

		DateTimeFormatter comHoraLocaleLocal = DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
		
		LocalDateTime inicio = LocalDateTime.now();
		System.out.println("Inicio com Parallel: "+ inicio.format(comHoraLocaleLocal));
	
		int size = usuarios.parallelStream()
			.filter(usu -> usu.getPontos() > 100)
			.sorted(Comparator.comparing(Usuario::getNome))
			.collect(Collectors.toList())
			.size();
		
		LocalDateTime finall = LocalDateTime.now();
		System.out.println("Final com Parallel: "+ finall.format(comHoraLocaleLocal));
		
		System.out.println("Quantidade usuários com mais de 100 pontos: "+ size);
		
	}

	private static void filtrarSemParallelStream(List<Usuario> usuarios) {
		
		DateTimeFormatter comHoraLocaleLocal = DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
		
		LocalDateTime inicio = LocalDateTime.now();
		System.out.println("Inicio sem Parallel: "+ inicio.format(comHoraLocaleLocal));
		
		int size = usuarios.stream()
			.filter(usu -> usu.getPontos() > 100)
			.sorted(Comparator.comparing(Usuario::getNome))
			.collect(Collectors.toList())
			.size();
		
		LocalDateTime finall = LocalDateTime.now();
		System.out.println("Final sem Parallel: "+ finall.format(comHoraLocaleLocal));
		
		System.out.println("Quantidade usuários com mais de 100 pontos: "+ size);
	}

	public static List<Usuario> getUsuarios() {
		
		Stream<Usuario> generate = Stream.generate(() -> new Usuario("Teste" + new Random().nextInt(300), new Random().nextInt(300)));
		
		List<Usuario> usuarios = generate.limit(new Random().longs(10000000l, 10000001l).findFirst().getAsLong()).collect(Collectors.toList());
		
		return usuarios;
	}
	
}
