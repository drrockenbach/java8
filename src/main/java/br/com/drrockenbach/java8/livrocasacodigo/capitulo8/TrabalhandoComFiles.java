package br.com.drrockenbach.java8.livrocasacodigo.capitulo8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrabalhandoComFiles {

	public static void main(String[] args) throws IOException {
		
		listandoArquivosDiretorio();
		
		listandoArquivosFiltrandoExtensao();
		
		exibirAsLinhasDoArquivo();
		
		contarLinhasArquivo();
		
		mapComLinhasDoArquivo();
	}

	private static void mapComLinhasDoArquivo() throws IOException {

		/**
		 * Se quisermos gerar um mapa de cada arquivo para toda a lista de linhas
		 * contidas nos arquivos, podemos utilizar um outro coletor e gerar um Map<Path,
		 * List<String>>:
		 */
		
		Map<Path, List<String>> content=
				Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap(
				Function.identity(), // mesmo coisa que p -> p
				p -> lines(p).collect(Collectors.toList())));
		
	}

	private static void contarLinhasArquivo() throws IOException {

		
		/**
		 * O toMap recebe duas Functions. A primeira produzirá a chave (no nosso caso o
		 * próprio Path) e a segunda produzirá o valor (a quantidade de linhas). Como é
		 * comum precisarmos de um lambda que retorna o próprio argumento (o nosso p ->
		 * p), podemos utilizar Function.identity() para deixar mais claro.
		 */
		
		Map<Path, Long> lines=
				Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap(
				p -> p, // aqui daria para utilizar Function.identity(), que teria o mesmo resultado
				p -> lines(p).count()));
		
		lines.forEach((path, count) -> System.out.println("Arquivo: "+ path+ " tem "+count +" linhas."));
	}

	private static void exibirAsLinhasDoArquivo() throws IOException {

		/**
		 * Infelizmente esse código não compila. O problema é que lança Files.lines
		 * IOException. Mesmo que o método que invoca o lance essa exception, map não
		 * compilará, pois nesse caso é a implementação do lambda que estará lançando
		 * IOException. O recebe uma Function, que tem o método e que map apply não
		 * lança exception alguma na assinatura.
		 */
//		Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
//		.filter(p -> p.toString().endsWith(".java"))
//		.map(p -> Files.lines(p))
//		.forEach(System.out::println);
		
		/**
		 * Uma solução seria escrever uma classe anônima ou um lambda definido com as
		 * chaves e com try/catch por dentro. Outra seria fazerummétodo estático
		 * simples, que faz o wrap da chamada para evitar a checked exception:
		 */
		
		/**
		 * A saída disso vai ser algo assim. java.util.stream.ReferencePipeline$Head@404b9385
		 */
		Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
			.filter(p -> p.toString().endsWith(".java"))
			.map(p -> lines(p))
			.forEach(System.out::println);
		
		/**
		 * O problema é que, com esse map, teremos um Stream<Stream<String>>, pois a
		 * invocação de lines(p) devolve um Stream<String> para cada Path do nosso
		 * Stream<Path> original! Isso fica mais claro de observar se não usarmos o
		 * forEach e atribuirmos o resultado a uma variável:
		 */
		
		Stream<Stream<String>> strings =
		Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
		.filter(p -> p.toString().endsWith(".java"))
		.map(p -> lines(p));
	
		
		/**
		 * SOLUÇÃO NA CLASSE FlatMap para o problema acima de Stream<Stream<String>>
		 * 
		 * 
		 * Abaixo mostrando todas as linhas dos arquivos
		 */
		
		Stream<String> linhas =
				Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
				.filter(p -> p.toString().endsWith(".java"))
				.flatMap(p -> lines(p));
		
		linhas.forEach(System.out::println);
		
	}
	
	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch(IOException e){
			
			throw new UncheckedIOException(e);
		}
	}

	private static void listandoArquivosFiltrandoExtensao() throws IOException {

		Files.list(Paths.get(".\\src\\main\\java\\br\\com\\drrockenbach\\java8\\livrocasacodigo\\capitulo8"))
		.filter(p -> p.toString().endsWith(".java") )
		.forEach(System.out::println);
		
	}

	private static void listandoArquivosDiretorio() throws IOException {
		
		Files.list(Paths.get("."))
			.forEach(System.out::println);
		
	}
	
}
