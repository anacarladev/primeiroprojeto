package br.com.aulajavaanacarla.primeiroprojeto.controller;

import br.com.aulajavaanacarla.primeiroprojeto.business.PessoaService;
import br.com.aulajavaanacarla.primeiroprojeto.business.exception.InvalidRequestException;
import br.com.aulajavaanacarla.primeiroprojeto.controller.vo.PessoaVO;
import br.com.aulajavaanacarla.primeiroprojeto.kafka.Producer;
import br.com.aulajavaanacarla.primeiroprojeto.vo.Pessoa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PessoaController.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private Producer producer;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("")
    public ResponseEntity<Object> listDePessoas(){
        List<Pessoa> pessoas = pessoaService.listPessoas();
        List<PessoaVO> pessoaVOS = new ArrayList<>();
        pessoas.stream().forEach(pessoa -> {
            PessoaVO vo = new PessoaVO();
            vo.setId(pessoa.getId());
            vo.setCpf(pessoa.getCpf());
            vo.setRg(pessoa.getRg());
            vo.setNome(pessoa.getNome());
            vo.setData_nascimento(pessoa.getDataNascimento());
            vo.setData_registro(pessoa.getDataRegistro());
            pessoaVOS.add(vo);
        });
//        pessoas.stream().filter()
        return new ResponseEntity<>(pessoaVOS,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteById(@PathVariable Integer id){
        try {
            Pessoa pessoas = new Pessoa();
            PessoaVO vo = new PessoaVO();
            vo.setId(pessoas.getId());
            vo.setCpf(pessoas.getCpf());
            vo.setRg(pessoas.getRg());
            vo.setNome(pessoas.getNome());
            vo.setData_nascimento(pessoas.getDataNascimento());
            vo.setData_registro(pessoas.getDataRegistro());
            pessoaService.delete(id);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Id Excluido!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> retornaEntidade(@PathVariable Integer id){
        LOGGER.info("Vams procurar a pessoa por ID: " + id);
        Optional<Pessoa> optionalPessoaVO = pessoaService.getByID(id);
        Pessoa pessoas = new Pessoa();
        PessoaVO vo = new PessoaVO();
        vo.setId(pessoas.getId());
        vo.setCpf(pessoas.getCpf());
        vo.setRg(pessoas.getRg());
        vo.setNome(pessoas.getNome());
        vo.setData_nascimento(pessoas.getDataNascimento());
        vo.setData_registro(pessoas.getDataRegistro());
        if(optionalPessoaVO.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            Pessoa p = optionalPessoaVO.get();
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> retornaEntidade(@PathVariable String cpf){
       LOGGER.info("Vamos procurar a pessoa por CPF: " + cpf);
        try {
            List<Pessoa> pessoas = pessoaService.getByCpf(cpf);
            List<PessoaVO> pessoaVOS = new ArrayList<>();
            pessoas.stream().forEach(pessoa -> {
                PessoaVO vo = new PessoaVO();
                vo.setId(pessoa.getId());
                vo.setCpf(pessoa.getCpf());
                vo.setRg(pessoa.getRg());
                vo.setNome(pessoa.getNome());
                vo.setData_nascimento(pessoa.getDataNascimento());
                vo.setData_registro(pessoa.getDataRegistro());
                pessoaVOS.add(vo);
            });
            if(pessoaVOS == null || pessoaVOS.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(pessoaVOS, HttpStatus.OK);
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rg/{rg}")
    public ResponseEntity<Object> retornaEntidadeRg(@PathVariable String rg){
        LOGGER.info("Vamos procurar o RG: " + rg);
        List<Pessoa> pessoaRg = null;
        try {
            pessoaRg = pessoaService.getByRg(rg);
            if(pessoaRg == null){
                return new ResponseEntity<>(pessoaRg, HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(pessoaRg, HttpStatus.OK);
            }
        } catch (InvalidRequestException e) {
//            e.printStackTrace();
            LOGGER.error("Cadastro não encontrado ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Object> insereEntidade(@RequestBody PessoaVO vo) throws InvalidRequestException {
        LOGGER.info("ID: " + vo.getId());
        LOGGER.info("Nome: " + vo.getNome());
        LOGGER.info("Data de nascimento: " + vo.getData_nascimento());
        LOGGER.info("CPF: " + vo.getCpf());
        LOGGER.info("RG: " + vo.getRg());
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String json = mapper.writeValueAsString(vo);
            LOGGER.info("PessoaVO JSON: " + json);
            producer.sendToTopic(json);
//            pessoaVO.getCpf().replace(".", " ");
//            if (pessoaVO.getCpf().isEmpty()){
//                throw new InvalidRequestException("vazio ou nulo");
//            } else if (pessoaVO.getCpf().length() != 11) {
//                throw new InvalidRequestException("Campo deverá conter 11 caracteres");
//            }
//            if(pessoaVO.getCpf().toString().equals("")){
//                 throw new InvalidRequestException("CAMPO OBRIGATORIO");
//            }
            Pessoa pessoa = new Pessoa();
            pessoa.setCpf(vo.getCpf());
            pessoa.setNome(vo.getNome());
            pessoa.setRg(vo.getRg());
            pessoa.setDataNascimento(vo.getData_nascimento());
            pessoa.setDataRegistro(vo.getData_registro());
            pessoaService.savePessoa(pessoa);
            return new ResponseEntity<>("Cadastro efetuado com sucesso", HttpStatus.CREATED);
        } catch (InvalidRequestException | JsonProcessingException e) {
            LOGGER.error("CPF invalida - deverá conter 11 caracteres ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public String atualizaEntidade(){
        return "Entidade Atualizada";
    }
}
