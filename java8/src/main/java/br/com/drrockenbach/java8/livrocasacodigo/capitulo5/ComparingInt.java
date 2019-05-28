package br.com.drrockenbach.java8.livrocasacodigo.capitulo5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class ComparingInt {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);

		/**
		 * Essa função,ToIntFunction, é para evitar o autoboxing, que pode gerar
		 * problemas de desempenho ao instanciar novos objetos sem necessidade, fazendo
		 * o garbage collector trabalhar mais.
		 * 
		 * Isso por que o getPontos retorna um int, e o método apply gerado pela
		 * Function extrai pontos retorna um Integer, e não é possivel passar tipos
		 * primitivos em generics.
		 */

		// AutoBoxing
		Function<Usuario, Integer> extraiPontos = u -> u.getPontos();
		Comparator<Usuario> comparator = Comparator.comparing(extraiPontos);

		usuarios.sort(comparator);

		// Sem AutoBoxing
		ToIntFunction<Usuario> extraiPontosSemAutoBoxing = u -> u.getPontos();
		Comparator<Usuario> comparatorSemAutoBoxing = Comparator.comparingInt(extraiPontosSemAutoBoxing);

		usuarios.sort(comparatorSemAutoBoxing);

		
		/**
		 * Forma mais curta
		 */
		
		usuarios.sort(Comparator.comparingInt(u -> u.getPontos()));
		
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
	}

}
