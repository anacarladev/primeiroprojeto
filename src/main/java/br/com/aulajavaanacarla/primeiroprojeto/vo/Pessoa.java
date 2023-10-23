package br.com.aulajavaanacarla.primeiroprojeto.vo;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private LocalDate dataRegistro;
    @NotEmpty(message = "não aceitamos vazio")
    @NotNull
    @Length(min = 11,message = "tem menos de 11")
    private String cpf;
    private String rg;

    public Pessoa() {
    }

    public Pessoa(Integer id, String nome, LocalDate dataNascimento, LocalDate dataRegistro, String cpf, String rg) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataRegistro = dataRegistro;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
