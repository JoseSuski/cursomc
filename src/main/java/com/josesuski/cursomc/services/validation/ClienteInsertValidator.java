package com.josesuski.cursomc.services.validation;

import com.josesuski.cursomc.domain.enums.TipoCliente;
import com.josesuski.cursomc.dto.ClienteNewDTO;
import com.josesuski.cursomc.resources.exceptions.FieldMessage;
import com.josesuski.cursomc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
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

        // inclua os testes aqui, inserindo erros na lista

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}