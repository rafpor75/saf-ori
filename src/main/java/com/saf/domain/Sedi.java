package com.saf.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sedi.
 */
@Entity
@Table(name = "sedi")
public class Sedi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sede", nullable = false)
    private String sede;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSede() {
        return sede;
    }

    public Sedi sede(String sede) {
        this.sede = sede;
        return this;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sedi sedi = (Sedi) o;
        if (sedi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sedi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sedi{" +
            "id=" + getId() +
            ", sede='" + getSede() + "'" +
            "}";
    }
}
