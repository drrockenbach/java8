package br.com.drrockenbach.java8.livrocasacodigo.capitulo9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class GroupingByPartitioningBy {

	public static void main(String[] args) {
		Usuario user1 = new Usuario("Paulo Silveira", 150, true);
		Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
		Usuario user3 = new Usuario("Guilherme Silveira", 90);
		Usuario user4 = new Usuario("Sergio Lopes", 120);
		Usuario user5 = new Usuario("Adriano Almeida", 100);
		Usuario user6 = new Usuario("Diomar", 150);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5, user6);

		/**
		 * Queremos um mapa em que a chave seja a pontuação do usuário e o valor seja
		 * uma lista de usuários que possuem aquela pontuação. Isto é, um Map<Integer,
		 * List<Usuario>>.
		 */
		usersToMapByPontos(usuarios);

		userToMapOfNames(usuarios);
		
		userToMapOfSumPoints(usuarios);
		
		concatenarTodosNomesDeUsuariosSeparadosPorAlgumCaracter(usuarios);
				

	}

	private static void concatenarTodosNomesDeUsuariosSeparadosPorAlgumCaracter(List<Usuario> usuarios) {

		String nomes = usuarios
				.stream()
				.map(Usuario::getNome)
				.collect(Collectors.joining(", "));
		
		System.out.println(nomes);
		
	}

	/**
	 * Queremos particionar por moderação, mas ter como valor não os usuários, mas
	 * sim a soma de seus pontos. Ta mbé m existe um coletor para realizar essas
	 * somatórias, que pode ser usado em conjunto com o partitioningBy e groupingBy:
	 * 
	 * @param usuarios
	 */
	private static void userToMapOfSumPoints(List<Usuario> usuarios) {

		Map<Boolean, Integer> pontuacaoPorTipo = usuarios
				.stream()
				.collect( Collectors.partitioningBy(
							Usuario::isModerador,
							Collectors.summingInt(Usuario::getPontos))
						);
		
		System.out.println("Pontuação por Moderador ou não: "+ pontuacaoPorTipo);
		
	}

	/**
	 * E se quiséssemos uma lista com os nomes dos usuários? Se fizermos
	 * stream().map(Usuario::getNome) não poderemos particionar por
	 * Usuario::isModerador, pois o map nos retornaria um Stream<String>. Tant o o
	 * partitioningBy quanto o groupingBy possuem uma sobrecarga que permite passar
	 * um Collector como argumento. Há um Collector que sabe coletar os objetos ao
	 * mesmo tempo que realiza uma transformação de map: Em vez de guardar os
	 * objetos dos usuários, poderíamos guardar uma lista com apenas o nome de cada
	 * usuário, usando o mapping para coletar esses nomes em uma lista:
	 * 
	 * @param usuarios
	 */
	private static void userToMapOfNames(List<Usuario> usuarios) {

		Map<Boolean, List<String>> nomesPorTipo = usuarios
				.stream()
				.collect(
						Collectors.partitioningBy(
								Usuario::isModerador,
								Collectors.mapping(Usuario::getNome,Collectors.toList())
								)
						);
		
		System.out.println("Lista de Nomes moderador Sim ou Não: ");
		System.out.println(nomesPorTipo);
		
	}

	/**
	 * Queremos um mapa em que a chave seja a pontuação do usuário e o valor seja
	 * uma lista de usuários que possuem aquela pontuação. Isto é, um Map<Integer,
	 * List<Usuario>>.
	 * 
	 * @param usuarios
	 */
	private static void usersToMapByPontos(List<Usuario> usuarios) {

		/**
		 * Possível solução sem utilizar stream
		 */

		Map<Integer, List<Usuario>> pontuacao = new HashMap<>();

		for (Usuario u : usuarios) {
			/**
			 * O método computeIfAbsent vai chamar a Function do lambda no caso de não
			 * encontrarumvalor para a chave u.getPontos() e associar o resultado (a nova
			 * ArrayList) a essamesma chave.
			 */
			pontuacao.computeIfAbsent(u.getPontos(), user -> new ArrayList<>()).add(u);
		}

		/**
		 * Existe um método pronto para fazer esse trabalho, que basicamente é um
		 * agrupamento, um groupBy
		 */

		Map<Integer, List<Usuario>> pontuacao2 = usuarios.stream().collect(Collectors.groupingBy(Usuario::getPontos));

		System.out.println("Por pontuação: ");
		System.out.println(pontuacao2);

		/**
		 * A saída é a mesma! O segredo é o Collectors.groupingBy, que é uma factory de
		 * Collectors que fazem agrupamentos.
		 */

		/**
		 * Podemos fazer mais ** Podemos particionar todos os usuários entre moderadores
		 * e não moderadores, usando o partitionBy:
		 */

		Map<Boolean, List<Usuario>> moderadores = usuarios.stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador));

		System.out.println("Moderadores: ");
		System.out.println(moderadores);
	}

}
