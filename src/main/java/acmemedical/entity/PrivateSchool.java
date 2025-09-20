/*******************************************************************************************************
 * File:  PrivateSchool.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and annotated by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// For JSON annotation for subtypes, Member 2 will add Jackson config if needed.

/**
 * Entity for private medical schools (inherits from MedicalSchool).
 */
@Entity
@DiscriminatorValue("0") // Value 0 = private, as per project convention
public class PrivateSchool extends MedicalSchool implements Serializable {
    private static final long serialVersionUID = 1L;

    public PrivateSchool() {
        super(false);
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
