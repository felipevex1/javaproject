package br.com.spectral.model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Lancamento {
    private LocalDate dataOcorrencia;
    private LocalTime horaOcorrencia;
    protected Double valor;

    public Lancamento(Double valor) {
        this.dataOcorrencia = LocalDate.now();
        this.horaOcorrencia = LocalTime.now();
        this.valor = valor;
    }

    public LocalDate getDataOcorrencia() {
        return this.dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public LocalTime getHoraOcorrencia() {
        return this.horaOcorrencia;
    }

    public void setHoraOcorrencia(LocalTime horaOcorrencia) {
        this.horaOcorrencia = horaOcorrencia;
    }

    public Double getValor() {
        return valor;
    }
}
