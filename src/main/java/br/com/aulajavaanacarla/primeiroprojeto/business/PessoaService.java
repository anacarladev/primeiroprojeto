package br.com.aulajavaanacarla.primeiroprojeto.business;

import br.com.aulajavaanacarla.primeiroprojeto.business.exception.InvalidRequestException;
import br.com.aulajavaanacarla.primeiroprojeto.dao.PessoaDAO;
import br.com.aulajavaanacarla.primeiroprojeto.vo.Pessoa;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PessoaService.class);

    @Autowired
    private PessoaDAO pessoaDAO;

    public Optional<Pessoa> getByID(Integer id) {
        Optional<Pessoa> byId = pessoaDAO.findById(id);
        LOGGER.info("ID não encontrado " + id);
        return byId;
    }

    public void delete(Integer p) throws InvalidRequestException {
        try {
            pessoaDAO.deleteById(p);
        } catch (EmptyResultDataAccessException e){
            LOGGER.warn("ID não encontrado para exclusão " + p);
            throw new InvalidRequestException("ID não encontrado para exclusão " + p);
        }

    }

    public List<Pessoa> getByCpf(String cpf) throws InvalidRequestException {
        List<Pessoa> byCpf = pessoaDAO.findByCpf(cpf);
        LOGGER.info("Cadastro não encontrado - CPF " + cpf);
        return byCpf;
    }

    public List<Pessoa> getByRg(String rg) throws InvalidRequestException {
        List<Pessoa> byRg = pessoaDAO.findByRg(rg);
        LOGGER.info("Cadastro não encontrado - RG " + rg);
        return byRg;
    }

    public Pessoa savePessoa(Pessoa p) throws InvalidRequestException {
//        p.setDataRegistro(LocalDate.now());
        LocalDate hoje = LocalDate.now();

        LocalDate compare = LocalDate.now();
        compare = compare.minusMonths(1);
        compare = compare.withDayOfMonth(compare.lengthOfMonth());

        LOGGER.info("antes: " + p.getCpf());
        p.setCpf(p.getCpf().replaceAll("\\.|-", ""));
        LOGGER.info("depois do depois: " + p.getCpf());
        LOGGER.info("LocalDate: " + hoje);

        if(p.getCpf() == null || p.getCpf().isEmpty()) {
            LOGGER.info("CPF não pode ser nulo ou Vazio " + p.getCpf());
            throw  new InvalidRequestException("CPF não pode ser nulo ou Vazio");
        }

        if(p.getCpf().length() != 11){
            LOGGER.info("CPF invalida - deverá conter 11 caracteres ");
            throw  new InvalidRequestException("CPF invalida - deverá conter 11 caracteres");
        }

        if (p.getDataNascimento().isAfter(hoje)){
            LOGGER.info("Data de nascimento não poderá ser data futura -> data informada "
                    + p.getDataNascimento() + " data atual " + hoje);
            throw  new InvalidRequestException("Data de nascimento não poderá ser data futura -> data informada "
                    + p.getDataNascimento() + " data atual " + hoje);
        }

        if(p.getDataRegistro().isAfter(compare)){
            LOGGER.info("Data de registro - invalida fora do período - data informada " +
                    p.getDataRegistro() + " período valido até " + compare);
            throw new InvalidRequestException("Data de registro - invalida fora do período - data informada " +
                    p.getDataRegistro() + " período valido até " + compare);
        }

        Pessoa pessoaSalva = pessoaDAO.save(p);

        return pessoaSalva;
    }

    public List<Pessoa> listPessoas(){
        Iterable<Pessoa> all = pessoaDAO.findAll();
        return IterableUtils.toList(all);
    }


}
