package br.com.aulajavaanacarla.primeiroprojeto.dao;

import javax.xml.bind.ValidationException;

public interface Validator<T> {
    void valida(T objeto) throws ValidationException;
}
