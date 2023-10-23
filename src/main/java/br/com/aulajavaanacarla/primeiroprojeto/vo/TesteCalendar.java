package br.com.aulajavaanacarla.primeiroprojeto.vo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class TesteCalendar {
    public static void main(String[] args) {
        LocalDate hoje = LocalDate.now();

        LocalDate dataReferencia = LocalDate.now();
        dataReferencia = dataReferencia.minusMonths(1);
        dataReferencia = dataReferencia.withDayOfMonth(dataReferencia.lengthOfMonth());

        LocalDate outraData = LocalDate.now().minusMonths(1);
        outraData = outraData.withDayOfMonth(outraData.lengthOfMonth());

        LocalDate dataOutra = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());

        System.out.println("Data de hoje: " + hoje);

        System.out.println(hoje.minusMonths(1));

        System.out.println(hoje.minus(1, ChronoUnit.MONTHS));

        System.out.println(hoje.plus(1, ChronoUnit.MONTHS));

        System.out.println(hoje.withDayOfMonth(1));

        System.out.println(hoje.withDayOfMonth(hoje.lengthOfMonth()));

        System.out.println(dataReferencia);

        System.out.println("Outra data: " + outraData);

        System.out.println("data outra : " + dataOutra);
    }
}
