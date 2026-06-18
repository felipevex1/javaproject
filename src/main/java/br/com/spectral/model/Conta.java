package br.com.spectral.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    protected Integer numero;
    protected Double saldo;
    protected Integer idCliente;
    protected List<Lancamento> lancamentos = new ArrayList<Lancamento>();

    public Conta() {
        this.saldo = 0.0;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
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
