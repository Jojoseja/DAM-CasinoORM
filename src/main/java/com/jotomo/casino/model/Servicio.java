package com.jotomo.casino.model;

import com.jotomo.casino.exceptions.ValidacionException;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @Column(name = "codigo", nullable = false, length = 5)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 50)
    private TipoServicio tipo;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @OneToMany(mappedBy = "codigo")
    private Set<Log> logs = new LinkedHashSet<>();

    public Servicio() {}

    //Constructor para crear Servicio
    public Servicio(TipoServicio tipo, String nombreServicio) {
        this.codigo=generarCodigo();
        setTipo(tipo);
        setNombre(nombreServicio);
        this.capacidad = tipo.getCapacidadMaxima();
    }

    //TODO: Revisar este constructor, no entiendo por qué capacidad no hereda del tipo
    public Servicio(String codigo, TipoServicio tipo, String nombreServicio, List<Cliente> listaClientes, int capacidadMaxima) {
        setCodigo(codigo);
        setTipo(tipo);
        setNombre(nombreServicio);
        this.capacidad = capacidadMaxima;
    }

    private String generarCodigo(){
        return UUID.randomUUID().toString().substring(0,5).toUpperCase();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == "" | codigo.isEmpty()){
            throw new ValidacionException("El código no puede estar vacío");
        }
        this.codigo = codigo;
    }
    public TipoServicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoServicio tipo) {
        this.tipo = Objects.requireNonNull(tipo, "TipoServicio no puede ser nulo.");
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreServicio) {
        Objects.requireNonNull(nombreServicio, "Nombre no puede ser nulo.");
        if (nombreServicio.trim().isEmpty()) {
            throw new ValidacionException("Nombre no puede estar vacío.");
        }

        this.nombre = nombreServicio.trim();
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidadMaxima) {
        this.capacidad = capacidadMaxima;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }


    @Override
    public String toString() {
        return "Servicio{" +
                "codigo='" + codigo + '\'' +
                ", tipo=" + tipo +
                ", nombre del Servicio='" + nombre + '\'' +
                ", capacidadMaxima=" + capacidad +
                '}';
    }

    @Override
    public boolean equals(Object obj){

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Servicio servicio = (Servicio) obj;
        return Objects.equals(codigo, servicio.codigo);
    }

}