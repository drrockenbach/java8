package br.com.drrockenbach.java8.livrocasacodigo.capitulo8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class ReducoesMaxSumAverage {

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

		Optional<Usuario> max = usuarios.stream().max(Comparator.comparing(Usuario::getPontos));
		Usuario maximaPontuacao = max.get();

		System.out.println(maximaPontuacao);

		soma(usuarios);

		multiplicar(usuarios);

		iteratorComStream(usuarios);

		usuarios.stream().findFirst().ifPresent(Usuario::tornaModerador);
		
		anyMatch(usuarios);

	}

	private static void anyMatch(List<Usuario> usuarios) {
		boolean hasModerator = usuarios.stream()

				.anyMatch(Usuario::isModerador);
		
		System.out.println("Algum moderador: "+hasModerator);
		
		/**
		 * se quisermos saber se há algum elemento daquela lista de usuários que é
		 * moderador:
		 */

		
		
	}

	private static void iteratorComStream(List<Usuario> usuarios) {

		usuarios.stream().iterator().forEachRemaining(System.out::println);

		/**
		 * Mas quando devemos utilizar um iterator de um Stream, se há um forEach tanto
		 * em quanto no próprio Stream? Collection Um motivo para usar um é quando
		 * queremos modificar os objetos Iterator de um Stream. Quando utilizarmos
		 * streams paralelos, veremos que não devemos mudar o estado dos objetos que
		 * estão nele, correndo o risco de ter resultados não de terminísticos. Outro
		 * motivo é a compatibilidade de APIs. Pode ser que você precise invocar um
		 * método que recebe Iterator.
		 */

	}

	private static void multiplicar(List<Usuario> usuarios) {
		int multiplicacao = usuarios.stream().mapToInt(Usuario::getPontos).reduce(1, (a, b) -> a * b);

		System.out.println(multiplicacao);
	}

	private static void soma(List<Usuario> usuarios) {

		/**
		 * método sum utiliza reduce, conforme forma mais detalhada abaixo.
		 */
		int total = usuarios.stream().mapToInt(Usuario::getPontos).sum();

		System.out.println(total);

		/**
		 * Forma detalhada do reduce do método sum. Mesmo estilo do JS
		 */

		int valorInicial = 0;
		IntBinaryOperator operacao = (acumulador, b) -> acumulador + b;
		int total2 = usuarios.stream().mapToInt(Usuario::getPontos).reduce(valorInicial, operacao);

		System.out.println(total2);

		/**
		 * Daria pra fazer a soma direta sem utilizar o mapToInt.
		 */

		BiFunction<Integer, Usuario, Integer> op1 = new BiFunction<Integer, Usuario, Integer>() {

			@Override
			public Integer apply(Integer atual, Usuario u) {
				return atual + u.getPontos();
			}
		};

		// Não entendi pra que serve isso, não chega a entrar no apply
		BinaryOperator<Integer> intBinaryOperator = new BinaryOperator<Integer>() {

			@Override
			public Integer apply(Integer left, Integer right) {
				return left + right;
			}
		};

		int total3 = usuarios.stream().reduce(0, op1, intBinaryOperator);

		System.out.println(total3);
	}

}
