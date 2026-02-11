package com.jotomo.casino.model;

import com.jotomo.casino.exceptions.ValidacionException;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @OneToMany(mappedBy = "cliente")
    private Set<Log> logs = new LinkedHashSet<>();

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellidos) throws ValidacionException{
        setDni(dni);
        setNombre(nombre);
        setApellidos(apellidos);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) throws ValidacionException {
        if (dni == null) {
            throw new ValidacionException("ERROR: El DNI no puede estar vacio");
        }

        dni = dni.trim().toUpperCase();

        if(!validarDni(dni)){
            throw new ValidacionException("ERROR: DNI no valido");
        }
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws ValidacionException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("ERROR: Nombre no puede estar vacío o ser nulo");
        }
        this.nombre = nombre.trim();
    }

    public String getApellidos() {
        return apellido;
    }

    public void setApellidos(String apellidos) throws ValidacionException {
        if (apellidos == null || apellidos.isBlank()) {
            throw new ValidacionException("ERROR: Apellidos no puede estar vacío o ser nulo");
        }
        this.apellido = apellidos.trim();
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }


    public static boolean validarDni(String dni){
        String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X","B",
                "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        if (!dni.matches("^[0-9]{8}[A-Z]$")){
            return false;
        }

        String let = dni.substring(dni.length() -1);
        String numeros = dni.replaceAll("[^0-9]", "");
        int resto = Integer.parseInt(numeros) % 23;

        return let.equals(letras[resto]);
    }

    @Override
    public boolean equals(Object obj){

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Cliente cliente = (Cliente) obj;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellido + '\'' +
                '}';
    }

}