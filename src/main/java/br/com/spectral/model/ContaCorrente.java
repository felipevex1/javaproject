package br.com.spectral.model;

public class ContaCorrente extends Conta {
    private Double limite;
    private static Integer proximoNumero = 1;

    public ContaCorrente() {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
        this.limite = 0.0;
    }

    public ContaCorrente(Double limite) {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
        this.limite = limite;
    }

    public ContaCorrente(Double limite, Cliente cliente) {
        super();
        this.numero = proximoNumero;
        proximoNumero++;
        this.limite = limite;
        if (cliente != null) {
            this.idCliente = cliente.getId();
        }
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

    @Override
    public void debitar(Double valor) {
        if ((this.saldo + this.limite) < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        lancamentos.add(new LancamentoDebito(valor));
        this.saldo -= valor;
    }
}
