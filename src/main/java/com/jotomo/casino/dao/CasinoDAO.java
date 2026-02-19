package com.jotomo.casino.dao;

import com.jotomo.casino.exceptions.*;
import com.jotomo.casino.model.Log;
import com.jotomo.casino.model.Cliente;
import com.jotomo.casino.model.Servicio;
import com.jotomo.casino.model.TipoServicio;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CasinoDAO {
    //Metodos CRUD

    /**
     * Añade un objeto cliente al almacén
     * @param cliente objeto que recibe como parámetro para agregar al almacén
     * @throws ValidacionException Si el cliente o alguno de sus parámetros es nulo o está mal
     * @throws ClientAlreadyExistsException Si el cliente ya existe
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public void addCliente(Cliente cliente) throws ValidacionException, ClientAlreadyExistsException, AccesoDenegadoException;

    /**
     * Añade un objeto Servicio al almacén
     * @param servicio objeto que recibe como parámetro para agregar al almacén
     * @throws ValidacionException si el servicio o alguno de sus parámetros es nulo o está mal
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ServiceAlreadyExistsException Si el servicio que se quiere agregar ya existe en la base de datos

     */
    public void addServicio(Servicio servicio) throws ValidacionException, ServiceAlreadyExistsException, AccesoDenegadoException;

    /**
     * Añade un objeto Log al almacén
     * @param log objeto que recibe como parámetro para agregar al almacén
     * @throws ValidacionException si el log o alguno de sus parámetros es nulo o está mal
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public void addLog(Log log) throws ValidacionException, AccesoDenegadoException;

    /**
     * Consulta toda la información del Servicio
     * @param codigo único del servicio del que se quiere consultar la información
     * @return String con la información del servicio solicitado
     * @throws ValidacionException si el código que se pasa por parámetro es nulo o está mal
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ServiceNotFoundException Si no se ha encontrado el servicio que se consulta
     */
    public String consultaServicio(String codigo) throws ValidacionException, ServiceNotFoundException, AccesoDenegadoException;

    /**
     * Consulta todos los servicios almacenados
     * @return List con todos los servicios que tenemos
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public List<Servicio> leerListaServicios() throws AccesoDenegadoException;

    /**
     * Consulta la información de un cliente
     * @param dni String único de un cliente
     * @return String con toda la información del cliente solicitado
     * @throws ValidacionException Si el dni que se pasa por parámetro es nulo o está mal
     * @throws ClientNotFoundException Si no se ha encontrado el cliente
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public String consultaCliente(String dni) throws ValidacionException, ClientNotFoundException, AccesoDenegadoException;

    /**
     * Consulta todos los clientes registrados
     * @return List con todos los clientes registrados
     * @throws AccesoDenegadoException Si ha ocurrido un error a la hora de acceder a la base de datos
     */
    public List<Cliente> leerListaClientes() throws AccesoDenegadoException;

    /**
     * Consulta unos Logs a través de los parámetros recibidos
     * @param codigoServicio del servicio a buscar
     * @param dni del cliente a buscar en el log
     * @param fecha del log
     * @return String con la informacion
     * @throws ValidacionException Si el código del servicio, el dni o la fecha pasadas por parámetro son nulo o están mal
     * @throws LogNotFoundException Si no se ha encontrado el Log querido
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ClientNotFoundException Si no se ha encontrado el cliente que se consulta
     * @throws ServiceNotFoundException Si no se ha encontrado el servicio que se consulta
     */
    public List<Log> consultaLog(String codigoServicio, String dni, LocalDate fecha) throws ValidacionException, LogNotFoundException, AccesoDenegadoException, ClientNotFoundException, ServiceNotFoundException;

    /**
     * Actualiza la información de un Servicio
     * @param codigo único de un Servicio
     * @param servicioActualizado objeto servicio por el que se actualiza
     * @return True si se ha podido actualizar los datos de la mesa
     * @throws IllegalArgumentException Si el código, el servicio o algún parámetro del servicio son nulos o están mal
     * @throws ServiceNotFoundException Si no se ha podido encontrar el servicio querido
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public boolean actualizarServicio(String codigo, Servicio servicioActualizado) throws ValidacionException, ServiceNotFoundException, AccesoDenegadoException;

    /**
     * Actualiza la información de un Cliente
     * @param dni único que permite identificar al Cliente
     * @param clienteActualizado objeto del cliente actualizado
     * @return True si se ha podido actualizar los datos del Cliente
     * @throws IllegalArgumentException Si el dni, el cliente actualizado o alguno de sus parámetros son nulos o están mal
     * @throws ClientNotFoundException Si no se ha podido encontrar el cliente a actualizar
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public boolean actualizarCliente(String dni, Cliente clienteActualizado) throws ValidacionException, ClientNotFoundException,  AccesoDenegadoException;

    /**
     * Borra una mesa
     * @param servicio recibe el objeto Mesa
     * @return True si se ha podido eliminar el objeto
     * @throws ValidacionException Si el objeto servicio es nulo o están mal alguno de sus parámetros
     * @throws ServiceNotFoundException Si no se ha podido encontrar el servicio a eliminar
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public boolean borrarServicio(Servicio servicio) throws ValidacionException, ServiceNotFoundException,  AccesoDenegadoException;

    /**
     * Borra un cliente
     * @param cliente, recibe el objeto Cliente
     * @return True si se ha podido eliminar la mesa
     * @throws ValidacionException Si el objeto cliente es nulo o están mal alguno de sus parámetros
     * @throws ClientNotFoundException Si no se ha podido encontrar el cliente a borrar
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ServiceNotFoundException Si no se ha encontrado el servicio que se consulta
     */
    public boolean borrarCliente(Cliente cliente) throws ValidacionException, ClientNotFoundException, AccesoDenegadoException, ServiceNotFoundException;



    //Metodos NO CRUD BÁSICOS
    /**
     * Devuelve el valor del dinero invertido en comida/bebida de un cliente
     * @param dni Del cliente del que queremos saber la información
     * @return Variable con lo gastado en alimentos por el cliente
     * @throws ValidacionException Si el dni pasado es nulo o está mal
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ClientNotFoundException Si no se ha encontrado el cliente que se consulta
     */
    public BigDecimal gananciasAlimentos(String dni) throws ValidacionException, AccesoDenegadoException, ClientNotFoundException;

    /**
     * Devuelve el valor del dinero invertido por un cliente en el casino
     * @param dni del cliente del que se quiere consultar la información
     * @param fecha del dia que se quiera consultar la información
     * @return Lo gastado en el casino por cliente
     * @throws ValidacionException Si el dni o la fecha son nulos o están mal
     * @throws LogNotFoundException Si no se ha podido encontrar el log querido
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ClientNotFoundException Si no se ha encontrado el cliente que se consulta
     */
    public BigDecimal dineroInvertidoClienteEnDia(String dni, LocalDate fecha) throws ValidacionException, LogNotFoundException, AccesoDenegadoException, ClientNotFoundException;

    /**
     * Devuelve la cantidad de veces que un cliente ha jugado en una mesa
     * @param dni Del cliente del que se quiere consultar la información
     * @param codigo De la mesa en la que ha jugado el cliente
     * @return La cantidad de veces que ha jugado cliente en una mesa
     * @throws ValidacionException Si el dni o el código son nulos o están mal
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     * @throws ClientNotFoundException Si no se ha encontrado el cliente que se consulta
     * @throws ServiceNotFoundException Si no se ha encontrado el servicio que se consulta
     */
    public int vecesClienteJuegaMesa(String dni, String codigo) throws ValidacionException, AccesoDenegadoException, ClientNotFoundException, ServiceNotFoundException;

    /**
     * Devuelve el total ganado en mesas para el casino
     * @return double con el total ganado en mesas
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public BigDecimal ganadoMesas() throws  AccesoDenegadoException;

    /**
     * Devuelve el total de lo ganado en establecimientos
     * @return Variable con el total ganado en establecimientos
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public BigDecimal ganadoEstablecimientos() throws AccesoDenegadoException;

    /**
     * Devuelve una lista con las mesas que sean de tipoJuego
     * @param tipoServicio a buscar en el archivo
     * @return Lista con las mesas de tipoJuego
     * @throws ValidacionException Si el tipoServicio es nulo o está mal
     * @throws AccesoDenegadoException Si se ha denegado el acceso a la base de datos
     */
    public List<Servicio> devolverServiciosTipo (TipoServicio tipoServicio) throws ValidacionException, AccesoDenegadoException;
}
