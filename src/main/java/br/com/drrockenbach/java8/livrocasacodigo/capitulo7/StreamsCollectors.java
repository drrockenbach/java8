package br.com.drrockenbach.java8.livrocasacodigo.capitulo7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class StreamsCollectors {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 90);
		Usuario user4 = new Usuario("Diomar Vem Antes guilherme", 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);
		usuarios.add(user4);

		tonarModeradorXUsuariosComMaisPontos(usuarios, 2);
		
		System.out.println("----------------------");
		
		tornarModeradorusuariosComMaisDeXPontos(usuarios, 100);
		
		System.out.println("-------Obtendo lista do stream--------");
		obtendoListDoStream(usuarios, 100);
		
		map(usuarios);
		
	}
	
	private static void map(List<Usuario> usuarios) {
		
		/**
		 * Para evitar o boxing causado abaixo, pelo fato de o map retornar um int, da pra utilizar o mapToInt.
		 * Isso por que a interface Function, no seu método apply, retorna um Integer, neste caso, em função de generics não aceitar tipo primitivo.
		 * Para resolver isso, o mapToint utiliza ToIntFunction, onde seu método retorna um int primitivo
		 * Veficiar item 7.7 IntStream e a família de Streams
		 */
		usuarios.stream()
			.map(u -> u.getPontos())
//			.collect(Collectors.toList());
			.forEach(p -> System.out.println("Pontos: "+ p));
		
		/**
		 * Desta forma não causa o boxing.
		 */
		
		double average = usuarios.stream().mapToInt(Usuario::getPontos).average().getAsDouble();
		
		System.out.println("Média de pontos: "+average);
		
		/**
		 * Caso a lista de usuários for vazia, o método getAsDouble(), que retornaria um NoSuchElementException. 
		 * Isso por que o average retorna um OptionalDouble, que é uma interface que permite trabalhar com algumas possibilidades em caso de nulos ou outros casos.
		 * O exemplo desse caso, onde usuários for vazio, teriamos que retornar média zero. Ao invés de utilizar um if para verificar isso, basta utilizar o método orElse(0.0).
		 * Caso a lista seja null, ocorrerá nullpointer ao invocar o método stream()
		 * Existem ainda outros métodos, como orElseThrow, para retornar uma exception, ifPresent, onde pode se executar alguma coisa ex: .ifPresent(valor -> janela.atualiza(valor));
		 **/
		
		List<Usuario> usuarios2 = new ArrayList<>();
		
		double average2 = usuarios2.stream()
				.mapToInt(Usuario::getPontos)
				.average()
				.orElse(0.0);
		
		System.out.println("Média zerada ao passar lista vazia: "+ average2);
		
		
		/**
		 * Retorna usuário com maior número de pontos
		 */
		
		Optional<String> maxNome = usuarios2
				.stream()
				.max(Comparator.comparingInt(Usuario::getPontos))
				.map(u -> u.getNome());
		
		System.out.println("Usuário com maior pontuação: "+maxNome);
		
	}

	private static void obtendoListDoStream(List<Usuario> usuarios, int i) {

		// Desta forma até funciona, mas não é a forma mais elegante, digamos assim.
		List<Usuario> maisQue100 = new ArrayList<>();
		
		usuarios.stream()
			.filter(u -> u.getPontos() > 100)
			.forEach(u -> maisQue100.add(u));
		
		usuarios.stream()
			.filter(u -> u.getPontos() > 100)
			.forEach(maisQue100::add);
		
		maisQue100.forEach(System.out::println);
		
		/**
		 * Podemos usar o método collect para resgatar esses elementos do nosso
		 * Stream<Usuario> para uma List.
		 */
		
		List<Usuario> collect = usuarios.stream()
		.filter(u -> u.getPontos() > 100)
		.collect(Collectors.toList());
		
	}

	/**
	 * Verificar item 7.2 do Livro:  Streams: tornando moderadores os usuários com mais de 100 pontos.
	 * @param usuarios
	 * @param pontos
	 */

	private static void tornarModeradorusuariosComMaisDeXPontos(List<Usuario> usuarios, int pontos) {
		
		Stream<Usuario> stream = usuarios.stream();
		// O Filter espera uma interface Predicate, então poderia passar esse predicate abaixo para o filter
		Predicate<Usuario> filtrar = u -> u.getPontos() > 100;
		
		stream.filter(u -> u.getPontos() > 100);
		
		// Ou ainda mais resumido, sem precisar declarar o stream
		
		/**
		 * MUITO MUITO IMPORTANTE.
		 * Os métodos do Stream não alteram a lista original, sempre geram um novo Stream com o resultado,
		 * então ao fazer forEach na lista de usuários, nada terá mudado, apenas na Stream resultado terá as alterações
		 */
		System.out.println(("Lista original: "));
		usuarios.forEach(System.out::println);
		System.out.println(("----------------"));
		Stream<Usuario> resultado = usuarios.stream().filter(u -> u.getPontos() > pontos);
		
		System.out.println(("Lista filtrada: "));
		
		resultado.forEach(System.out::println);
		
		System.out.println("---------outra forma de exibir----------");
		// Encadeando o forEach no stream de retorno do filter
		usuarios.stream()
			.filter(u -> u.getPontos() > 100)
			.forEach(System.out::println);
	}

	/**
	 * Para filtrar os x usuários com mais pontos e torná-losmoderadores, podemos
	 * agora fazer o seguinte código:
	 * 
	 * Torna moderador a quantidade de usuários conforme o @param qtd que tiver mais pontos
	 * 
	 * @param usuarios
	 */
	private static void tonarModeradorXUsuariosComMaisPontos(List<Usuario> usuarios, Integer qtd) {
		usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());
		
		usuarios.forEach(System.out::println);
		
		usuarios.subList(0, qtd)
			.forEach(Usuario::tornaModerador);
		
		System.out.println("--------------------");
		usuarios.forEach(System.out::println);
	}
}
