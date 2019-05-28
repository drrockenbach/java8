package br.com.drrockenbach.java8.livrocasacodigo.capitulo2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import br.com.drrockenbach.java8.livrocasacodigo.Usuario;

public class Capitulo2 {
	public static void main(String... args) {
		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		for (Usuario u : usuarios) {
			System.out.println("Forma até java 7: " + u.getNome());
		}

		usuarios.forEach(usu -> System.out.println(
				"Forma reduzida. No final das contas, o java se encarrega de transformar isso para um Consumer: "
						+ usu.getNome()));

		Mostrador mostrador = new Mostrador();
		usuarios.forEach(mostrador);

		/** Exemplos com Thread */
		new ExemploThread();
	}

}

/**
 * O novo forEach, recebe um consumer, que possui um método abstrado, o accept.
 * Este recebe por parâmetro cada item da lista, e a implementação será a arrow
 * function passada. Isso para a forma reduzida, conforme exemplo 1.
 * 
 * Exemplo 1: usuarios.forEach(usu -> System.out.println(usu.getNome()));
 * 
 * Esse exemplo acima, no final das contas é a forma reduzida de fazer o mesmo
 * que essa classe mostrador abaixo.
 * 
 * Exemplo 2 - Utilização da classe abaixo:
 * 
 * Mostrador mostrador = new Mostrador(); usuarios.forEach(mostrador);
 * 
 * @author diomar.rockenbach
 *
 */
class Mostrador implements Consumer<Usuario> {
	public void accept(Usuario u) {
		System.out.println("Forma mais extensa de fazer, colocado apenas para saber como funciona por baixo dos panos: "
				+ u.getNome());
	}
}

class ExemploThread {
	public ExemploThread() {
		formaAntiga();
		formaNova1();
		formaNova2();
	}

	private void formaAntiga() {
		Runnable r = new Runnable() {
			public void run() {
				for (int i = 0; i <= 2; i++) {
					System.out.println("Thread antiga: "+i);
				}
			}
		};
		new Thread(r).start();
	}

	private void formaNova1() {
		Runnable r = () -> {
			for (int i = 0; i <= 2; i++) {
				System.out.println("Thread nova 1: "+i);
			}
		};
		new Thread(r).start();
	}

	private void formaNova2() {

		new Thread(() -> {
			for (int i = 0; i <= 2; i++) {
				System.out.println("Thread nova 2: "+i);
			}
		}).start();
	}
}
