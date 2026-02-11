package com.jotomo.casino;

import com.jotomo.casino.dao.CasinoManager;
import com.jotomo.casino.exceptions.*;
import com.jotomo.casino.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        CasinoManager cm = new CasinoManager();
        Cliente cliente1 = new Cliente("12345678Z", "Juan", "Pérez García");
        Cliente cliente2 = new Cliente("06690442H", "María", "López Martín");
        Servicio mesaPoker = new Servicio( TipoServicio.MESAPOKER,
                "Mesa Poker VIP");
        Servicio bar = new Servicio(TipoServicio.BAR,
                "Bar Principal");
        Log log1 = new Log(cliente1, mesaPoker, LocalDate.now(), LocalTime.now(),
                TipoConcepto.APOSTAR, 50.0);
        Log log2 = new Log(cliente1, mesaPoker, LocalDate.now(), LocalTime.now(),
                TipoConcepto.APUESTACLIENTEGANA, 25.0);
        Log log3 = new Log(cliente1, bar, LocalDate.now(), LocalTime.now(),
                TipoConcepto.COMPRABEBIDA, 5.5);
        Log log4 = new Log(cliente1, bar, LocalDate.now(), LocalTime.now(),
                TipoConcepto.COMPRACOMIDA, 5.5);
        try {
            //AÑADIR ELEMENTOS A LA BBDD
            cm.addCliente(cliente1);
            cm.addCliente(cliente2);
            cm.addServicio(mesaPoker);
            cm.addServicio(bar);
            cm.addLog(log1);
            cm.addLog(log2);
            cm.addLog(log3);
            cm.addLog(log4);

            //TOTAL DE DINERO QUE EL CLIENTE HA INVERTIDO EN UN DÍA
            System.out.println("El cliente con DNI " + cliente1.getDni() + " ha gastado en un día: " + cm.dineroInvertidoClienteEnDia(cliente1.getDni(),LocalDate.now()) + "€");

            //VECES QUE UN CLIENTE JUEGA EN UNA MESA
            System.out.println("Nº de veces que el cliente con DNI " + cliente1.getDni()+ " ha jugado en la mesa " +
                    mesaPoker.getCodigo() + ": " + cm.vecesClienteJuegaMesa(cliente1.getDni(),mesaPoker.getCodigo()));

            //GANADO EN MESAS
            System.out.println("Ganado en apuestas: " + cm.ganadoMesas() + "€");

            //GANANCIAS EN ALIMENTOS
            System.out.println("Ganado en establecimientos " + cm.ganadoEstablecimientos() + "€");

            //LISTA DE SERVICIOS
            System.out.println(cm.devolverServiciosTipo(TipoServicio.BAR));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AccesoDenegadoException e) {
            throw new RuntimeException(e);
        } catch (ClientAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (ServiceAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (ClientNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ServiceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (LogNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
