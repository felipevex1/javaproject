package br.com.spectral.model;
import java.util.ArrayList;
import java.util.List;

public class ContaCorrente {
    private Integer numero;
    private Double saldo;
    private Double limite;
    private static Integer proximoNumero = 1;

    private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

    public ContaCorrente () {
        this.numero = proximoNumero;
        proximoNumero++;

        this.saldo = 0.0;
        this.limite = 0.0;
    }

    public ContaCorrente (Double limite) {
        this.numero = proximoNumero;
        proximoNumero++;

        this.saldo = 0.0;
        this.limite = limite;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Double getLimite() {
        return this.limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public static void setProximoNumero(Integer proximoNumero) {
        ContaCorrente.proximoNumero = proximoNumero;
    }

    public void creditar(Double valor) {
        lancamentos.add(new LancamentoCredito(valor));
        this.saldo += valor;
    }

    public void debitar(Double valor) {
        if ((this.saldo + this.limite) < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        lancamentos.add(new LancamentoDebito(valor));
        this.saldo -= valor;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

}
