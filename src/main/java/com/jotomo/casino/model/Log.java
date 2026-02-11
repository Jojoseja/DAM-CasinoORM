package com.jotomo.casino.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generador_log")
    @SequenceGenerator(
            name = "generador_log",
            sequenceName = "generador_log",
            allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dni")
    private Cliente dni;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "codigo")
    private Servicio codigo;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "concepto", nullable = false, length = 50)
    private TipoConcepto concepto;

    @Column(name = "cantidad_concepto", nullable = false, precision = 10, scale = 2)
    private double cantidadConcepto;

    @Column(name = "lista_clientes", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> listaClientes;

    public Log() {
    }

    //Constructor sin fecha y hora para crear log
    public Log(Cliente dni, Servicio codigo, TipoConcepto concepto, double cantidadConcepto) {
        setDni(dni);
        setCodigo(codigo);
        setConcepto(concepto);
        setCantidadConcepto(cantidadConcepto);
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    //TODO: Revisar este constructor por si no se usa
    public Log(Cliente dni, Servicio codigo, LocalDate fecha, LocalTime hora, TipoConcepto concepto, double cantidadConcepto) {
        setDni(dni);
        setCodigo(codigo);
        setConcepto(concepto);
        setCantidadConcepto(cantidadConcepto);
        this.fecha = fecha;
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getDni() {
        return dni;
    }

    public void setDni(Cliente cliente) {
        if (cliente == null){
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        this.dni = cliente;
    }

    public Servicio getCodigo() {
        return codigo;
    }

    public void setCodigo(Servicio servicio) {
        if (servicio == null){
            throw new IllegalArgumentException("Servicio no puede ser null");
        }
        this.codigo = servicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getFechaStr(){
        return (fecha !=null) ? fecha.toString(): "";
    }

    public void setFecha(LocalDate fecha) {
        if (fecha.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        this.fecha = fecha;
    }

    //Setter para convertir de String a LocalDate
    public void setFechaStr(String fechaStr){
        if (fechaStr != null && !fechaStr.isBlank()){
            this.fecha = LocalDate.parse(fechaStr);
        }
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getHoraStr(){
        if(hora != null){
            DateTimeFormatter formateo = DateTimeFormatter.ofPattern("HH:mm:ss");
            return hora.format(formateo);
        }
        return "";
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    //Setter para convertir de String a LocalTime
    public void setHoraStr(String horaStr){
        if (horaStr != null && !horaStr.isBlank()){
            this.hora = LocalTime.parse(horaStr);
        }
    }

    public TipoConcepto getConcepto() {
        return concepto;
    }

    public void setConcepto(TipoConcepto concepto) {
        if (concepto == null){
            throw new IllegalArgumentException("Concepto no puede ser null");
        }
        this.concepto = concepto;
    }

    public double getCantidadConcepto() {
        return cantidadConcepto;
    }

    public void setCantidadConcepto(double cantidadConcepto) {
        if (cantidadConcepto <= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidadConcepto = cantidadConcepto;
    }

    public Map<String, Object> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(Map<String, Object> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @Override
    public String toString() {
        return "Log{" +
                "cliente=" + dni +
                ", servicio=" + codigo +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", concepto=" + concepto +
                ", cantidadConcepto=" + cantidadConcepto +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Log log = (Log) obj;

        return Double.compare(log.cantidadConcepto, cantidadConcepto) == 0 &&
                Objects.equals(dni, log.dni) &&
                Objects.equals(codigo, log.codigo) &&
                Objects.equals(fecha, log.fecha) &&
                Objects.equals(hora, log.hora) &&
                concepto == log.concepto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, codigo, fecha, hora, concepto, cantidadConcepto);
    }

}