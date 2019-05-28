package br.com.drrockenbach.java8.livrocasacodigo.capitulo3;

public class ExemploValidator {

	public static void main(String[] args) {

		// Forma antiga de declaração
		Validador<String> validadorCEP = new Validador<String>() {
			public boolean valida(String valor) {
				return valor.matches("[0-9]{5}-[0-9]{3}");
			}
		};

		System.out.println(validadorCEP.valida("93530-150"));

		// Forma nova de declaração
		Validador<String> validadorCEPNew = valor -> {
			return valor.matches("[0-9]{5}-[0-9]{3}");
		};

		System.out.println(validadorCEPNew.valida("93530-150"));
		
		/**
		 * Forma nova ainda mais resumida
		 */
		
		Validador<String> validadorCEPOk =
				valor -> valor.matches("[0-9]{5}-[0-9]{3}");
				
		System.out.println(validadorCEPOk.valida("93530-150"));
		
	}

}
