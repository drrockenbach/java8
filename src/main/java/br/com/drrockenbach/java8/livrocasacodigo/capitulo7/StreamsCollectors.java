package br.com.drrockenbach.java8.livrocasacodigo.capitulo7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
