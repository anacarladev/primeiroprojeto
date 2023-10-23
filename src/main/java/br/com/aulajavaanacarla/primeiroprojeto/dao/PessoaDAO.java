package br.com.aulajavaanacarla.primeiroprojeto.dao;

import br.com.aulajavaanacarla.primeiroprojeto.vo.Pessoa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PessoaDAO extends CrudRepository<Pessoa, Integer> {
    public List<Pessoa> findByRg(String rg);
    public List<Pessoa> findByCpf(String cpf);
}

