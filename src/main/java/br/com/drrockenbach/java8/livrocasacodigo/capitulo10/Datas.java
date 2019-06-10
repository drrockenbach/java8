package br.com.drrockenbach.java8.livrocasacodigo.capitulo10;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Datas {

	public static void main(String[] args) {

		comparingDatesWithDifferentTimeZones();

		/**
		 * Note como é simples consultar o primeiro dia do trimestre de determinadomês,
		 * ou então incrementar/decrementar meses:
		 */
		
		/**
		 * Retorna o número do dia referente ao ano do primeiro dia do mês
		 * Nesse caso, para dezembro, por exemplo, caso o ano não for bisexto, o dia primeiro representa dentro do ano o dia 335. Quando bisexto, 336
		 */
		System.out.println(Month.DECEMBER.firstDayOfYear(false)); // false = ano não bisexto
		System.out.println(Month.DECEMBER.firstDayOfYear(true)); // true = ano bisexto
		
		System.out.println(Month.DECEMBER.plus(2));
		System.out.println(Month.DECEMBER.minus(1));
		
	}

	private static void comparingDatesWithDifferentTimeZones() {

		/**
		 * Há ainda os casos em que queremos comparar datas iguais, porém em timezones
		 * diferentes. Utilizar ométodo equals, nesse caso, não causaria o efeito
		 * esperado—claro, afinal a sobrescrita desse método na classe ZonedDateTime
		 * espera que o offset entre as datas seja o mesmo:
		 */

		/**
		 * Para estes casos podemos e devemos utilizar o método isEqual exatamente como
		 * fizemos com o LocalDate. Repare:
		 */
		
		ZonedDateTime tokyo = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("Asia/Tokyo"));

		ZonedDateTime saoPaulo = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));

		/**
		 * O método isEqual considera os diferentes timezones, porém a hora ainda assim é diferente, então vai retornar false
		 * Caso utilizar o método equals, mesmo as horas sendo iguais, se o timezone for diferente, retornaria false.
		 */
		
		System.out.println(tokyo.isEqual(saoPaulo));
		
		/**
		 * Ao adicionarmos as 12 horas de diferença, ai sim, mesmo com o timezone diferente, o retorno é true.
		 */
		
		tokyo = tokyo.plusHours(12);
		System.out.println(tokyo.isEqual(saoPaulo));
	}

}
