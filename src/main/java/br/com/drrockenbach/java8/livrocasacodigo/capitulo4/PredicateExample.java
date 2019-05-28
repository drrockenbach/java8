package br.com.drrockenbach.java8.livrocasacodigo.capitulo4;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class PredicateExample {

	public static void main(String[] args) {

		Predicate<Usuario> predicado = new Predicate<Usuario>() {
			public boolean test(Usuario u) {
				return u.getPontos() > 160;
			}
		};
		
		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);
		
//		usuarios.removeIf(predicado);
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
		/**
		 * Forma rezumida
		 * 
		 */
		
		usuarios.removeIf(u -> u.getPontos() > 160);
		usuarios.forEach(u -> System.out.println(u.getNome()));
	}

}
