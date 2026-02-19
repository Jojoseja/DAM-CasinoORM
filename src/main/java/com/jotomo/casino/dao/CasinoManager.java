package com.jotomo.casino.dao;

import com.jotomo.casino.exceptions.*;
import com.jotomo.casino.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CasinoManager implements CasinoDAO{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("casinoorm");

    @Override
    public void addCliente(Cliente cliente) throws ValidacionException, ClientAlreadyExistsException, AccesoDenegadoException {
        try {

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            if (cliente == null){
                throw new ValidacionException("El cliente no puede ser null");
            }

            try {
                em.persist(cliente);
            } catch (PersistenceException e){
                tx.rollback();
                throw new ClientAlreadyExistsException("El cliente ya existe en la base de datos", e);
            }

            em.flush();
            em.refresh(cliente);

            tx.commit();
            em.close();

        } catch (Exception e) {
            throw new AccesoDenegadoException("No se ha podido acceder a la base de datos", e);
        }
    }

    @Override
    public void addServicio(Servicio servicio) throws ValidacionException, ServiceAlreadyExistsException, AccesoDenegadoException {

        try {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();

            if (servicio == null){
                throw new ValidacionException("El cliente no puede ser null");
            }

            tx.begin();

            try {
                em.persist(servicio);
            } catch (PersistenceException e) {
                tx.rollback();
                throw new ServiceAlreadyExistsException("El servicio ya existe");
            }

            em.flush();
            em.refresh(servicio);

            tx.commit();
            em.close();
        } catch (Exception e) {
        throw new AccesoDenegadoException("No se ha podido acceder a la base de datos", e);
        }
    }

    @Override
    public void addLog(Log log) throws ValidacionException, AccesoDenegadoException {

        if (log == null){
            throw new ValidacionException("El log no puede ser null");
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.persist(log);

            tx.commit();

        } catch (PersistenceException e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw new AccesoDenegadoException(
                    "No se ha podido acceder a la base de datos", e);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public String consultaServicio(String codigo) throws ValidacionException, ServiceNotFoundException, AccesoDenegadoException {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ValidacionException("El código no puede ser nulo");
        }


        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            Servicio servicio = em.find(Servicio.class, codigo);

            if (servicio == null) {
                throw new ServiceNotFoundException("Servicio no encontrado");
            }

            return servicio.toString();

        } catch (PersistenceException e) {
            throw new AccesoDenegadoException("Error accediendo a la base de datos", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Servicio> leerListaServicios() throws AccesoDenegadoException {
        List<Servicio> listaServicio;

        try {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Query consulta = em.createQuery("SELECT s from Servicio s");
            listaServicio = consulta.getResultList();

            tx.commit();
            em.close();
        } catch (Exception e){
            throw new AccesoDenegadoException("No se ha podido acceder a la base de datos", e);
        }

        return listaServicio;
    }

    @Override
    public String consultaCliente(String dni) throws ValidacionException, ClientNotFoundException, AccesoDenegadoException {
        Cliente cliente;

        if (dni == null || dni.isBlank()){
            throw new ValidacionException("El dni no puede ser nulo");
        }

        try {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            cliente = em.find(Cliente.class, dni);

            tx.commit();
            em.close();

            if (cliente == null) throw new ClientNotFoundException("Cliente no encontrado");
        } catch (Exception e){
            throw new AccesoDenegadoException("No se ha podido acceder a la base de datos", e);
        }


        return cliente.toString();
    }

    @Override
    public List<Cliente> leerListaClientes() throws AccesoDenegadoException {
        List<Cliente> listaCliente;
        try{
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Query consulta = em.createQuery("SELECT c from Cliente c");
            listaCliente = consulta.getResultList();

            tx.commit();
            em.close();
        } catch (Exception e){
            throw new AccesoDenegadoException("No se ha podido acceder a la base de datos", e);
        }

        return listaCliente;
    }

    @Override
    public List<Log> consultaLog(String codigoServicio, String dni, LocalDate fecha) throws ValidacionException, LogNotFoundException, AccesoDenegadoException, ClientNotFoundException, ServiceNotFoundException {
        List<Log> listaLog;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Uso de JOIN FETCH para cargar los datos de cliente y servicio

        Query consulta = em.createQuery("SELECT l from Log l " +
                "JOIN FETCH l.cliente c " +
                "JOIN FETCH l.servicio s " +
                "WHERE c.dni LIKE ?1 AND l.fecha = ?2 AND s.codigo LIKE ?3");


        /*
        Query consulta = em.createQuery(
                "SELECT l FROM Log l " +
                "JOIN FETCH l.cliente c " +
                "JOIN FETCH l.servicio s " +
                "WHERE c.dni = :dni " +
                "AND l.fecha = :fecha " +
                "AND s.codigo = :codigo"
        );



        consulta.setParameter("dni", dni);
        consulta.setParameter("fecha", fecha);
        consulta.setParameter("codigo", codigoServicio);
        */

        consulta.setParameter(1, dni);
        consulta.setParameter(2, fecha);
        consulta.setParameter(3, codigoServicio);
        listaLog = consulta.getResultList();
        tx.commit();
        em.close();

        if (listaLog.isEmpty()) throw new LogNotFoundException("No se ha encontrado el Log");

        return listaLog;
    }

    @Override
    public boolean actualizarServicio(String codigo, Servicio servicioActualizado) throws ValidacionException, ServiceNotFoundException, AccesoDenegadoException {
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
    public boolean actualizarCliente(String dni, Cliente clienteActualizado) throws ValidacionException, ClientNotFoundException, AccesoDenegadoException {
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
    public boolean borrarServicio(Servicio servicio) throws ValidacionException, ServiceNotFoundException, AccesoDenegadoException {
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
    public boolean borrarCliente(Cliente cliente) throws ValidacionException, ClientNotFoundException, AccesoDenegadoException, ServiceNotFoundException {
        Cliente clienteAux;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        clienteAux = em.find(Cliente.class, cliente.getDni());

        if (clienteAux == null) throw new ClientNotFoundException("Cliente no encontrado");

        em.remove(clienteAux);

        tx.commit();
        em.close();

        return true;
    }

    @Override
    public BigDecimal gananciasAlimentos(String dni) throws ValidacionException, AccesoDenegadoException, ClientNotFoundException {

        List<TipoConcepto> listaConcepto = List.of(TipoConcepto.COMPRABEBIDA, TipoConcepto.COMPRACOMIDA);

        String consulta = "SELECT SUM(l.cantidadConcepto) from Log l " +
                "WHERE l.concepto IN :listaConcepto AND l.cliente.dni LIKE :dni ";

        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery(consulta);

        query.setParameter("listaConcepto", listaConcepto);
        query.setParameter("dni", dni);


        BigDecimal total = (BigDecimal) query.getSingleResult();

        em.close();
        return total;
    }

    @Override
    public BigDecimal dineroInvertidoClienteEnDia(String dni, LocalDate fecha) throws ValidacionException, LogNotFoundException, AccesoDenegadoException, ClientNotFoundException {

        try {
            EntityManager em = emf.createEntityManager();

            TypedQuery<BigDecimal> queryUsuarioGastado = em.createQuery("SELECT SUM(l.cantidadConcepto) FROM Log l WHERE l.cliente.dni = (:c1) AND l.fecha = (:c2) AND l.concepto IN (:lista)", BigDecimal.class);

            queryUsuarioGastado.setParameter("c1",dni);
            queryUsuarioGastado.setParameter("c2",fecha);
            queryUsuarioGastado.setParameter("lista",List.of(TipoConcepto.APOSTAR,TipoConcepto.COMPRABEBIDA,TipoConcepto.COMPRACOMIDA));

            BigDecimal gastado = queryUsuarioGastado.getSingleResult();

            TypedQuery<BigDecimal> queryUsuarioGanado = em.createQuery("SELECT SUM(l.cantidadConcepto) FROM Log l WHERE l.cliente.dni = (:c1) AND l.fecha = (:c2) AND l.concepto = 'APUESTACLIENTEGANA'", BigDecimal.class);
            queryUsuarioGanado.setParameter("c1",dni);
            queryUsuarioGanado.setParameter("c2",fecha);

            BigDecimal ganado = queryUsuarioGanado.getSingleResult();

            BigDecimal total = gastado.subtract(ganado);
            return total;
        } catch (Exception e){
            throw new LogNotFoundException("No se han encontrado logs");
        }
    }

    @Override
    public int vecesClienteJuegaMesa(String dni, String codigo) throws ValidacionException, AccesoDenegadoException, ClientNotFoundException, ServiceNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Log> query = em.createQuery("SELECT l from Log l WHERE l.cliente.dni = (:c1) AND l.servicio.codigo = (:c2)", Log.class);

        query.setParameter("c1",dni);
        query.setParameter("c2",codigo);

        List<Log> listaLogs = query.getResultList();
        return listaLogs.size();
    }

    @Override
    public BigDecimal ganadoMesas() throws AccesoDenegadoException {

        try {
            EntityManager em = emf.createEntityManager();

            //Consulta para las ganancias en apuestas
            TypedQuery<BigDecimal> queryApuestas = em.createQuery("SELECT SUM (l.cantidadConcepto) FROM Log l WHERE l.concepto in (:c1)",BigDecimal.class);
            queryApuestas.setParameter("c1",TipoConcepto.APOSTAR);
            BigDecimal ganancias = queryApuestas.getSingleResult();


            //Consulta para las pérdidas en apuestas
            TypedQuery<BigDecimal> queryPerdidas = em.createQuery("SELECT SUM (l.cantidadConcepto) FROM Log l WHERE l.concepto in (:c1)",BigDecimal.class);
            queryPerdidas.setParameter("c1",TipoConcepto.APUESTACLIENTEGANA);
            BigDecimal perdidas = queryPerdidas.getSingleResult();

            BigDecimal total = ganancias.subtract(perdidas);

            return total;
        } catch (PersistenceException e){
            throw new AccesoDenegadoException("Error accediendo a la base de datos", e);
        }

    }

    @Override
    public BigDecimal ganadoEstablecimientos() throws AccesoDenegadoException {
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<BigDecimal> query = em.createQuery("SELECT SUM (l.cantidadConcepto) FROM Log l WHERE l.concepto IN (:c1,:c2)", BigDecimal.class);

            query.setParameter("c1", TipoConcepto.COMPRABEBIDA);
            query.setParameter("c2", TipoConcepto.COMPRACOMIDA);

            BigDecimal total = query.getSingleResult();

            return total != null ? total : BigDecimal.ZERO;

        } catch (PersistenceException e){
            throw new AccesoDenegadoException("Error accediendo a la base de datos", e);
        }
    }

    @Override
    public List<Servicio> devolverServiciosTipo(TipoServicio tipoServicio) throws ValidacionException, AccesoDenegadoException {

        if (tipoServicio == null){
            throw new ValidacionException("EL tipo de servicio es invalido");
        }

        EntityManager em = emf.createEntityManager();
        TypedQuery<Servicio> query = em.createQuery("SELECT s FROM Servicio s WHERE tipo = (:c1)",Servicio.class);
        query.setParameter("c1",tipoServicio);
        List<Servicio> listaServicios = query.getResultList();
        return listaServicios;
    }

    // Metodo NO DAO

    /**
     * Devuelve lo ganado por un cliente en un dia en concreto
     * @param dni Dni del cliente
     * @param fecha fecha del dia a buscar
     * @return Lo ganado apostado menos lo perdido apostando
     * @throws ClientNotFoundException Lanza la excepción si no se encuentra el cliente
     */
    public BigDecimal dineroGanadoClienteEnDia(String dni, LocalDate fecha) throws ClientNotFoundException {
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Log> cq = cb.createQuery(Log.class);
        Root<Log> log = cq.from(Log.class);

        Predicate cliente = cb.equal(log.get("cliente").get("dni"), dni);

        if (cliente == null) throw new ClientNotFoundException("CLiente no encontrado.");

        Predicate pFecha = cb.equal(log.get("fecha"), fecha);

        cq.select(log).where(cb.and(cliente,pFecha));

        List<Log> logs = em.createQuery(cq).getResultList();

        em.close();

        BigDecimal total = BigDecimal.valueOf(0);
        for (Log lo : logs) {
            if (lo.getConcepto().equals(TipoConcepto.APUESTACLIENTEGANA)) {
                total = total.add(lo.getCantidadConcepto());
            } else if (lo.getConcepto().equals(TipoConcepto.APOSTAR)){
                total = total.subtract(lo.getCantidadConcepto());
            }

        }

        return total;
    }
}
