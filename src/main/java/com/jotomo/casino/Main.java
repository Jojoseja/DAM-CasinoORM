package com.jotomo.casino;

import com.jotomo.casino.dao.CasinoManager;
import com.jotomo.casino.exceptions.*;
import com.jotomo.casino.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CasinoManager cm = new CasinoManager();
        Cliente cliente1 = new Cliente("12345678Z", "Juan", "Pérez García");
        Cliente cliente2 = new Cliente("06690442H", "María", "López Martín");
        Cliente cliente3 = new Cliente("41684416K", "Paco", "Sánchez Martín");
        Servicio mesaPoker = new Servicio( TipoServicio.MESAPOKER,
                "Mesa Poker VIP");
        Servicio bar = new Servicio(TipoServicio.BAR,
                "Bar Principal");
        Servicio servicio = new Servicio(TipoServicio.RESTAURANTE,
                "Restaurante Principal");
        Log log1 = new Log(cliente1, mesaPoker, LocalDate.now(), LocalTime.now(),
                TipoConcepto.APOSTAR, 50.0);
        Log log2 = new Log(cliente1, mesaPoker, LocalDate.now(), LocalTime.now(),
                TipoConcepto.APUESTACLIENTEGANA, 25.0);
        Log log3 = new Log(cliente1, bar, LocalDate.now(), LocalTime.now(),
                TipoConcepto.COMPRABEBIDA, 5.5);
        Log log4 = new Log(cliente1, bar, LocalDate.now(), LocalTime.now(),
                TipoConcepto.COMPRACOMIDA, 5.5);

        LocalDate dateFecha = LocalDate.parse("2026-02-17");

        try {
            // CRUD

            //AÑADIR ELEMENTOS A LA BBDD
            cm.addCliente(cliente1);
            cm.addCliente(cliente2);
            cm.addServicio(mesaPoker);
            cm.addServicio(bar);
            cm.addServicio(servicio);
            cm.addLog(log1);
            cm.addLog(log2);
            cm.addLog(log3);
            cm.addLog(log4);

            //CONSULTA DE UN SERVICIO
            System.out.println(cm.consultaServicio(bar.getCodigo()));

            //LEER LISTA DE SERVICIOS
            List<Servicio> servicios = cm.leerListaServicios();
            for (Servicio s : servicios){
                System.out.println(s);
            }

            //CONSULTA CLIENTE
            System.out.println(cm.consultaCliente(cliente1.getDni()));

            //LEER LISTA CLIENTES
            List<Cliente> clientes = cm.leerListaClientes();
            for (Cliente c : clientes){
                System.out.println(c);
            }


            //CONSULTA LOGS

            List<Log> logs = cm.consultaLog(bar.getCodigo(), cliente1.getDni(),dateFecha);
            for (Log l : logs){
                System.out.println(l);
            }

            //ACTUALIZAR SERVICIO
            bar.setNombre("Bareto el leto");
            if (cm.actualizarServicio(bar.getCodigo(),bar)){
                System.out.println("Cliente con DNI " + cliente2.getDni() + " actualizado con éxito");
            } else {
                System.out.println("Error actualizando el cliente");
            }

            //ACTUALIZAR CLIENTE
            cliente2.setApellidos("Lodi Martín");
            if (cm.actualizarCliente("06690442H",cliente2)){
                System.out.println("Cliente con DNI " + cliente2.getDni() + " actualizado con éxito");
            } else {
                System.out.println("Error actualizando el cliente");
            }

            //BORRAR SERVICIO
            if (cm.borrarServicio(servicio)){
                System.out.println("Servicio con código " + servicio.getCodigo() + " borrado de la BBDD");
            } else {
                System.out.println("Error borrando el Servicio de la BBDD");
            }

            //BORRAR CLIENTE
            if (cm.borrarCliente(cliente2)){
                System.out.println("Cliente con dni " + cliente2.getDni() + " borrado de la BBDD");
            } else {
                System.out.println("Error borrando el cliente de la BBDD");
            }

            // NO - CRUD

            //TOTAL DE GANANCIAS EN ALIMENTOS
            System.out.println("Total de ganancias en alimentos: " + cm.gananciasAlimentos(cliente1.getDni()));

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

            //CANTIDAD GANADA CLIENTE DIA
            BigDecimal total = cm.dineroGanadoClienteEnDia("12345678Z", LocalDate.now());
            System.out.println(total);


        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (AccesoDenegadoException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (ClientAlreadyExistsException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (ServiceAlreadyExistsException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (ClientNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (ServiceNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (LogNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
