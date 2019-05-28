package br.com.drrockenbach.java8.livrocasacodigo.capitulo6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.Comparator.comparing;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class MethodReference {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		usuarios.add(user2);
		usuarios.add(user3);

		/**
		 * Para tornar todos os usuário moderadores. Da pra fazer um foreach, conforme
		 * abaixo
		 */

		usuarios.forEach(u -> u.tornaModerador());

		/**
		 * Ou utilizar MethodReferente
		 */

		usuarios.forEach(Usuario::tornaModerador);

		/**
		 * O method reference retorna uma instância de Consumer, conforme forma verbosa
		 * abaixo
		 */

		// Isso gera o mesmo que Consumer<Usuario> tornaModerador = u ->
		// u.tornaModerador()
		Consumer<Usuario> tornaModerador = Usuario::tornaModerador;
		usuarios.forEach(tornaModerador);

		/**
		 * Da pra utilizar também com outros métodos, como o comparing
		 */
		usuarios.sort(Comparator.comparing(Usuario::getNome));

		/**
		 * Pensando na fluência do nosso código, poderíamos também fazer um import
		 * estático do método comparing, e extrair nossa expressão para uma variável com
		 * nome bemsignificativo, algo como:
		 */

		Function<Usuario, String> byName = Usuario::getNome;
		usuarios.sort(comparing(byName));
	}

}

