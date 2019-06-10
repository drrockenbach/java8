package br.com.drrockenbach.java8.livrocasacodigo.capitulo10;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class DiferencaEntreDatas {

	public static void main(String[] args) {
		
		/**
		 * Calculando diferença entre datas
		 */
		
		calculadoComEnumChronoUnit();

		/**
		 * Existe a classe Period, que calcula a diferença entre duas datas e possui métodos para saber a diferença total. Ex: 2 anos, 1 mês e 20 dias
		 */
		calculandoComClassePeriod();
		
		/**
		 * Utilizando a classe Duration para calculos de tempo
		 */
		
		calculandoComClasseDuration();
		
		
	}

	private static void calculandoComClasseDuration() {

		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime daquiAUmaHora = LocalDateTime.now().plusHours(1);
		
		Duration duration = Duration.between(agora, daquiAUmaHora);
		
		if (duration.isNegative()) {
			duration = duration.negated();
		}
		
		System.out.println();
		
		/**
		 * Importante. Os métodos getHours, minutes e seconds não são complementares. O hours retorna a diferença em horas, o minutes em minutos e o seconds em segundos.
		 * No exemplo abaixo aparecerá assim: 1 horas, 60 minutos e 3600 segundos , ou seja, a representação da diferença de uma hora em cada unidade.
		 */
		
		System.out.printf("%s horas, %s minutos e %s segundos", duration.toHours(), duration.toMinutes(), duration.getSeconds());
	}

	private static void calculandoComClassePeriod() {
		LocalDate agora = LocalDate.now();
		LocalDate outraData = LocalDate.of(2018, Month.JANUARY, 1);
		
		Period periodo = Period.between(outraData, agora);
		System.out.println();
		System.out.printf("%s dias, %s meses e %s anos",
		periodo.getDays(), periodo.getMonths(), periodo.getYears());
		
		
		/**
		 * Em caso de valores negativos com Period, é possível verificar se os valores são negativos, e caso isso não seja o desejado, 
		 * da pra mandar negar os valores, e será invertido, para passar a ser positivo, conforme exemplos abaixo.
		 */
		
		LocalDate agora2 = LocalDate.now();
		LocalDate outraData2 = LocalDate.of(2015, Month.JANUARY, 25);
		Period periodo2 = Period.between(agora2, outraData2);
		System.out.println();
		System.out.printf("%s dias, %s meses e %s anos", periodo2.getDays(), periodo2.getMonths(), periodo2.getYears());
		
		System.out.println();
		
		Period periodo3 = Period.between(agora2, outraData2);
		if (periodo3.isNegative()) {
			periodo3 = periodo3.negated();
		}
		System.out.printf("%s dias, %s meses e %s anos", periodo3.getDays(), periodo3.getMonths(), periodo3.getYears());
	}

	private static void calculadoComEnumChronoUnit() {
		LocalDate agora = LocalDate.now();
		LocalDate outraData = LocalDate.of(2018, Month.JANUARY, 1);
		
		long dias = ChronoUnit.DAYS.between(outraData, agora);
		long meses = ChronoUnit.MONTHS.between(outraData, agora);
		long anos = ChronoUnit.YEARS.between(outraData, agora);
		System.out.printf("%s dias, %s meses e %s anos", dias, meses, anos);
	}
	
}
