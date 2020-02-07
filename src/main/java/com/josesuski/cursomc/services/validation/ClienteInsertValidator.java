package com.josesuski.cursomc.services.validation;

import com.josesuski.cursomc.domain.Cliente;
import com.josesuski.cursomc.domain.enums.TipoCliente;
import com.josesuski.cursomc.dto.ClienteNewDTO;
import com.josesuski.cursomc.repositories.ClienteRepository;
import com.josesuski.cursomc.resources.exceptions.FieldMessage;
import com.josesuski.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

    if(objDto.getTipo().equals(TipoCliente.PessoaFisica.getCod())&& !BR.isValidCPF(objDto.getCpfOuCnpj())){
        list.add(new FieldMessage("cpfOuCnpj","CPF inválido"));
    }
        if(objDto.getTipo().equals(TipoCliente.PessoaJuridica.getCod())&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CNPJ inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null){
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}