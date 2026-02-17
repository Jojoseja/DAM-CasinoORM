package com.jotomo.casino.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @JoinColumn(name = "dni_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "codigo_servicio")
    private Servicio servicio;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "concepto", nullable = false, length = 50)
    private TipoConcepto concepto;

    @Column(name = "cantidad_concepto", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadConcepto;

    public Log() {
    }

    //Constructor sin fecha y hora para crear log
    public Log(Cliente dni, Servicio codigo, TipoConcepto concepto, double cantidadConcepto) {
        setCliente(dni);
        setServicio(codigo);
        setConcepto(concepto);
        setCantidadConcepto(cantidadConcepto);
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    //TODO: Revisar este constructor por si no se usa
    public Log(Cliente dni, Servicio codigo, LocalDate fecha, LocalTime hora, TipoConcepto concepto, double cantidadConcepto) {
        setCliente(dni);
        setServicio(codigo);
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null){
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        if (servicio == null){
            throw new IllegalArgumentException("Servicio no puede ser null");
        }
        this.servicio = servicio;
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

    public BigDecimal getCantidadConcepto() {
        return cantidadConcepto;
    }

    public void setCantidadConcepto(double cantidadConcepto) {
        if (cantidadConcepto<= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidadConcepto = BigDecimal.valueOf(cantidadConcepto);
    }

    @Override
    public String toString() {
        return "Log{" +
                "cliente=" + cliente.getDni() +
                ", servicio=" + servicio.getCodigo() +
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

        return cantidadConcepto.compareTo(log.cantidadConcepto) == 0 &&
                Objects.equals(cliente, log.cliente) &&
                Objects.equals(servicio, log.servicio) &&
                Objects.equals(fecha, log.fecha) &&
                Objects.equals(hora, log.hora) &&
                concepto == log.concepto;
    }

    @Override
    public int hashCode() {
        // stripTrailingZeros() garantiza que 10.0 y 10.00 generen el mismo hash
        return Objects.hash(cliente, servicio, fecha, hora, concepto, cantidadConcepto.stripTrailingZeros());
    }
}