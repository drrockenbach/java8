package br.com.drrockenbach.java8.livrocasacodigo.capitulo5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

import java.text.Collator;

public class Ordenacao {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);
		Usuario user4 = new Usuario(null, 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);

		/**
		 * Forma antiga de ordenar
		 */

		Comparator<Usuario> comparator = new Comparator<Usuario>() {
			public int compare(Usuario u1, Usuario u2) {
				return u1.getNome().compareTo(u2.getNome());
			}
		};
		Collections.sort(usuarios, comparator);

		/**
		 * Forma um pouco mais resumida, com lambd e criando instancia de comparator
		 */

		Comparator<Usuario> comparatorLambda = (u1, u2) -> u1.getNome().compareTo(u2.getNome());
		Collections.sort(usuarios, comparatorLambda);

		/****
		 * Forma mais resumida, apenas com lambda
		 */

		Collections.sort(usuarios, (u1, u2) -> u1.getNome().compareTo(u2.getNome()));

		/**
		 * Comparador da classe string que ignora case sensitive
		 */
		Collections.sort(usuarios, (u1, u2) -> String.CASE_INSENSITIVE_ORDER.compare(u1.getNome(), u2.getNome()));

		/**
		 * Comparador da Classe Collator, que tem opções de decomposição
		 */

		Collator c = Collator.getInstance(Locale.getDefault());
		c.setDecomposition(Collator.FULL_DECOMPOSITION);

		Collections.sort(usuarios, (u1, u2) -> c.compare(u1.getNome(), u2.getNome()));

		/**
		 * Ignorando valores nullos
		 */
		usuarios.add(user4);
		Collections.sort(usuarios,
				(u1, u2) -> Comparator.nullsLast(String::compareTo).compare(u1.getNome(), u2.getNome()));

		/**
		 * Forma mais curta ainda, sem precisar utilizar o Collections.sort
		 */

		usuarios.sort((u1, u2) -> Comparator.nullsLast(String::compareTo).compare(u1.getNome(), u2.getNome()));

		usuarios.forEach(u -> System.out.println(u.getNome()));

		/**
		 * Da pra deixar ainda mais curto. Agora nas interfaces é possível definir
		 * métodos estáticos.
		 */
		
		// Removido o valor com null pois estava dando erro com comparing. Ver depois uma solução.
		usuarios.removeIf(u -> u.getNome() == null);
		
		Comparator<Usuario> comparatorStatic = Comparator.nullsFirst( Comparator.comparing(u -> u.getNome()));
		usuarios.sort(comparatorStatic);
		
		/**
		 * Ou ainda mais curto
		 */
		
		usuarios.sort(Comparator.nullsFirst(Comparator.comparing(Usuario::getNome)));

	}

}
