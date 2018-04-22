package com.saf.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Esami.
 */
@Entity
@Table(name = "esami")
public class Esami implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @OneToOne
    @JoinColumn(unique = true)
    private Sedi relEsamiSedi;

    @ManyToOne
    private Materie relMatEsami;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Esami data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Sedi getRelEsamiSedi() {
        return relEsamiSedi;
    }

    public Esami relEsamiSedi(Sedi sedi) {
        this.relEsamiSedi = sedi;
        return this;
    }

    public void setRelEsamiSedi(Sedi sedi) {
        this.relEsamiSedi = sedi;
    }

    public Materie getRelMatEsami() {
        return relMatEsami;
    }

    public Esami relMatEsami(Materie materie) {
        this.relMatEsami = materie;
        return this;
    }

    public void setRelMatEsami(Materie materie) {
        this.relMatEsami = materie;
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
        Esami esami = (Esami) o;
        if (esami.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), esami.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Esami{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
