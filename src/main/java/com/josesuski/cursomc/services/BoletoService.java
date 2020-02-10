package com.josesuski.cursomc.services;

import com.josesuski.cursomc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public  void  preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instateDoPedido){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(cal.getTime());
    }

}
