package com.jotomo.casino.model;

import com.jotomo.casino.exceptions.ValidacionException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

@Entity
public class Cliente {
    @Id
    private String dni;

    @Column(columnDefinition = "varchar(255) not null", nullable = false)
    private String nombre;

    @Column(columnDefinition = "varchar(255) not null", nullable = false)
    private String apellidos;

    //Constructor vacío obligatorio para JAXB
    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellidos) throws ValidacionException{
        setDni(dni);
        setNombre(nombre);
        setApellidos(apellidos);
    }
    @XmlElement
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

    @XmlElement
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws ValidacionException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("ERROR: Nombre no puede estar vacío o ser nulo");
        }
        this.nombre = nombre.trim();
    }

    @XmlElement
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) throws ValidacionException {
        if (apellidos == null || apellidos.isBlank()) {
            throw new ValidacionException("ERROR: Apellidos no puede estar vacío o ser nulo");
        }
        this.apellidos = apellidos.trim();
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
                ", apellidos='" + apellidos + '\'' +
                '}';
    }
}