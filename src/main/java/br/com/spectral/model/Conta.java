package br.com.spectral.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    protected Integer numero;
    protected Double saldo;
    protected List<Lancamento> lancamentos = new ArrayList<Lancamento>();

    public Conta() {
        this.saldo = 0.0;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void creditar(Double valor) {
        lancamentos.add(new LancamentoCredito(valor));
        this.saldo += valor;
    }

    public abstract void debitar(Double valor);

    @Override
    public String toString() {
        return this.numero.toString();
    }
}