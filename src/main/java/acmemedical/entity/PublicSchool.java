/*******************************************************************************************************
 * File:  PublicSchool.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
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
 * Entity for public medical schools (inherits from MedicalSchool).
 */
@Entity
@DiscriminatorValue("1") // Value 1 = public, as per project convention
public class PublicSchool extends MedicalSchool implements Serializable {
    private static final long serialVersionUID = 1L;

    public PublicSchool() {
        super(true);
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
