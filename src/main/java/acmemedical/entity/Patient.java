/*******************************************************************************************************
 * File:  Patient.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and annotated by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity class mapping for the patient table.
 */
@Entity
@Table(name = "patient")
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :param1")
})
@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
@EntityListeners(PojoListener.class) // Remove/comment if no PojoListener
public class Patient extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "year_of_birth", nullable = false)
    private int year;

    @Basic(optional = false)
    @Column(name = "home_address", nullable = false, length = 100)
    private String address;

    @Basic(optional = false)
    @Column(name = "height_cm", nullable = false)
    private int height;

    @Basic(optional = false)
    @Column(name = "weight_kg", nullable = false)
    private int weight;

    @Basic(optional = false)
    @Column(name = "smoker", nullable = false)
    private byte smoker;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<Prescription> prescriptions = new HashSet<>();

    // --- Constructors ---
    public Patient() {
        super();
    }

    public Patient(String firstName, String lastName, int year, String address, int height, int weight, byte smoker) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.smoker = smoker;
    }

    public Patient setPatient(String firstName, String lastName, int year, String address, int height, int weight, byte smoker) {
        setFirstName(firstName);
        setLastName(lastName);
        setYear(year);
        setAddress(address);
        setHeight(height);
        setWeight(weight);
        setSmoker(smoker);
        return this;
    }

    // --- Getters and Setters ---
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public byte getSmoker() {
        return smoker;
    }
    public void setSmoker(byte smoker) {
        this.smoker = smoker;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }
    public void setPrescription(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    // hashCode and equals inherited from PojoBase

}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
