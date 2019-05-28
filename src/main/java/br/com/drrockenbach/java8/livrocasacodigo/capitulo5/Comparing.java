package br.com.drrockenbach.java8.livrocasacodigo.capitulo5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class Comparing {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);

		/**
		 * Apenas para ficar mais clara a declaração abaixo com o lambda. O primeiro parâmetro do generics, no Caso o usuário é o tipo do parâmetro recebido
		 * pelo apply e o segundo é o tipo do retorno, no caso String.
		 */
		Function<Usuario, String> extraiNomeExtenso = new Function<Usuario, String>() {

			@Override
			public String apply(Usuario t) {
				return t.getNome();
			}
		};

		Function<Usuario, String> extraiNome = u -> u.getNome();

		Comparator<Usuario> comparator = Comparator.comparing(extraiNome);

		usuarios.sort(comparator);

		usuarios.forEach(u -> System.out.println(u.getNome()));
	}

}
