package br.com.aulajavaanacarla.primeiroprojeto.controller.vo;

import java.time.LocalDate;

public class PessoaVO {
    private Integer id;
    private String nome;
    private LocalDate data_nascimento;
    private LocalDate data_registro;
    private String cpf;
    private String rg;

    public PessoaVO() {
    }

    public PessoaVO(Integer id, String nome, LocalDate data_nascimento, LocalDate data_registro, String cpf, String rg) {
        this.id = id;
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.data_registro = data_registro;
        this.cpf = cpf;
        this.rg = rg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public LocalDate getData_registro() {
        return data_registro;
    }

    public void setData_registro(LocalDate data_registro) {
        this.data_registro = data_registro;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
}
