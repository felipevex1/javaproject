package br.com.spectral.model;

public class Cliente {
    private Integer id;
    private String nome;
    private String cpf;
    private static Integer proximoId = 1;

    public Cliente() {
        this.id = proximoId;
        proximoId++;
    }

    public Cliente(String nome, String cpf) {
        this.id = proximoId;
        proximoId++;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static void setProximoId(Integer proximoId) {
        Cliente.proximoId = proximoId;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }
}
