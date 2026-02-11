package com.jotomo.casino.dao;

import com.jotomo.casino.exceptions.*;
import com.jotomo.casino.model.Cliente;
import com.jotomo.casino.model.Log;
import com.jotomo.casino.model.Servicio;
import com.jotomo.casino.model.TipoServicio;
import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CasinoManager implements CasinoDAO{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("casinoorm");


    @Override
    public void addCliente(Cliente cliente) throws ValidacionException, ClientAlreadyExistsException, IOException, AccesoDenegadoException {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(cliente);
        em.flush();
        em.refresh(cliente);

        tx.commit();
        em.close();
    }

    @Override
    public void addServicio(Servicio servicio) throws ValidacionException, ServiceAlreadyExistsException, IOException, AccesoDenegadoException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(servicio);
        em.flush();
        em.refresh(servicio);

        tx.commit();
        em.close();
    }

    @Override
    public void addLog(Log log) throws ValidacionException, IOException, AccesoDenegadoException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(log);
        em.flush();
        em.refresh(log);

        tx.commit();
        em.close();
    }

    @Override
    public String consultaServicio(String codigo) throws ValidacionException, ServiceNotFoundException, IOException, AccesoDenegadoException {
        Servicio servicio;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        servicio = em.find(Servicio.class, codigo);

        tx.commit();
        em.close();

        if (servicio == null) throw new ServiceNotFoundException("Servicio no encontrado");

        return servicio.toString();
    }

    @Override
    public List<Servicio> leerListaServicios() throws IOException, AccesoDenegadoException {
        return List.of();
    }

    @Override
    public String consultaCliente(String dni) throws ValidacionException, ClientNotFoundException, IOException, AccesoDenegadoException {
        Cliente cliente;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        cliente = em.find(Cliente.class, dni);

        tx.commit();
        em.close();

        if (cliente == null) throw new ClientNotFoundException("Cliente no encontrado");

        return cliente.toString();
    }

    @Override
    public List<Cliente> leerListaClientes() throws IOException, AccesoDenegadoException {
        return List.of();
    }

    @Override
    public List<Log> consultaLog(String codigoServicio, String dni, LocalDate fecha) throws ValidacionException, LogNotFoundException, IOException, AccesoDenegadoException, ClientNotFoundException, ServiceNotFoundException {
        return List.of();
    }

    @Override
    public boolean actualizarServicio(String codigo, Servicio servicioActualizado) throws ValidacionException, ServiceNotFoundException, IOException, AccesoDenegadoException {
        Servicio servicio;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        servicio = em.find(Servicio.class, codigo);

        if (servicio == null) throw new ServiceNotFoundException("Servicio not found");

        servicio = servicioActualizado;

        em.merge(servicio);


        tx.commit();
        em.close();

        return true;
    }

    @Override
    public boolean actualizarCliente(String dni, Cliente clienteActualizado) throws ValidacionException, ClientNotFoundException, IOException, AccesoDenegadoException {
        Cliente cliente;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        cliente = em.find(Cliente.class, dni);

        if (cliente == null) throw new ClientNotFoundException("Servicio not found");

        cliente = clienteActualizado;

        em.merge(cliente);


        tx.commit();
        em.close();
        return true;
    }

    @Override
    public boolean borrarServicio(Servicio servicio) throws ValidacionException, ServiceNotFoundException, IOException, AccesoDenegadoException {
        Servicio servicioAux;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        servicioAux = em.find(Servicio.class, servicio.getCodigo());

        if (servicioAux == null) throw new ServiceNotFoundException("Servicio not found");

        em.remove(servicioAux);


        tx.commit();
        em.close();

        return true;
    }

    @Override
    public boolean borrarCliente(Cliente cliente) throws ValidacionException, ClientNotFoundException, IOException, AccesoDenegadoException, ServiceNotFoundException {
        Cliente clienteAux;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        clienteAux = em.find(Cliente.class, cliente.getDni());

        if (clienteAux == null) throw new ServiceNotFoundException("Servicio not found");

        em.remove(clienteAux);


        tx.commit();
        em.close();

        return true;
    }

    @Override
    public double gananciasAlimentos(String dni) throws ValidacionException, IOException, AccesoDenegadoException, ClientNotFoundException {
        double totalGanado = 0;

        String consulta = "SELECT cantidad_concepto FROM Log l WHERE l.dni like ?1";

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Query query = em.createQuery(consulta);

        query.setParameter(1, dni);



        tx.commit();
        em.close();
        return 0;
    }

    @Override
    public double dineroInvertidoClienteEnDia(String dni, LocalDate fecha) throws ValidacionException, LogNotFoundException, IOException, AccesoDenegadoException, ClientNotFoundException {
        return 0;
    }

    @Override
    public int vecesClienteJuegaMesa(String dni, String codigo) throws ValidacionException, IOException, AccesoDenegadoException, ClientNotFoundException, ServiceNotFoundException {
        return 0;
    }

    @Override
    public double ganadoMesas() throws IOException, AccesoDenegadoException {
        return 0;
    }

    @Override
    public double ganadoEstablecimientos() throws IOException, AccesoDenegadoException {
        return 0;
    }

    @Override
    public List<Servicio> devolverServiciosTipo(TipoServicio tipoServicio) throws ValidacionException, IOException, AccesoDenegadoException {
        return List.of();
    }
}
