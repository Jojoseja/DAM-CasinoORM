package com.jotomo.casino;

import com.jotomo.casino.dao.CasinoManager;
import com.jotomo.casino.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Pruebas {
    public static void main(String[] args) {
        CasinoManager cm = new CasinoManager();
        try {
            Servicio ser1 = new Servicio(TipoServicio.BAR, "Bar Prueba");
            Servicio ser2 = new Servicio(TipoServicio.MESAPOKER, "Mesa Poker Prueba");
            Cliente cli = new Cliente("12345678Z", "Carlos", "Carles");

            LocalDate fecha = LocalDate.parse("2000-10-01");
            LocalTime hora = LocalTime.parse("16:44:01");

            Log log1 = new Log(cli, ser1, fecha, hora, TipoConcepto.COMPRACOMIDA, 20);
            Log log2 = new Log(cli, ser1, fecha, hora, TipoConcepto.COMPRABEBIDA, 30);
            Log log3 = new Log(cli, ser1, fecha, hora, TipoConcepto.COMPRACOMIDA, 40);


            cm.addServicio(ser1);
            cm.addServicio(ser2);
            cm.addCliente(cli);

            System.out.println("Debug! 1");
            cm.addLog(log1);
            cm.addLog(log2);
            cm.addLog(log3);

            System.out.println("Debug! 2");

            BigDecimal total = cm.gananciasAlimentos(cli.getDni());

            System.out.println(total);

            cm.borrarServicio(ser1);
            cm.borrarServicio(ser2);


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
