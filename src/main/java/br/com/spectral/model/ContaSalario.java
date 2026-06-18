package br.com.spectral.model;

public class ContaSalario extends Conta {
    private static Integer proximoNumero = 1;

    public ContaSalario() {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
    }

    public static void setProximoNumero(Integer proximoNumero) {
        ContaSalario.proximoNumero = proximoNumero;
    }

    @Override
    public void debitar(Double valor) {
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        lancamentos.add(new LancamentoDebito(valor));
        this.saldo -= valor;
    }
}