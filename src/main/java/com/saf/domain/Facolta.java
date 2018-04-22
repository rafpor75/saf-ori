package com.saf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Facolta.
 */
@Entity
@Table(name = "facolta")
public class Facolta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_modifica")
    private LocalDate dataModifica;

    @Column(name = "abilitato")
    private Boolean abilitato;

    @OneToMany(mappedBy = "relCdlsFac")
    @JsonIgnore
    private Set<Cdl> relFacCdls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Facolta nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataModifica() {
        return dataModifica;
    }

    public Facolta dataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
        return this;
    }

    public void setDataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
    }

    public Boolean isAbilitato() {
        return abilitato;
    }

    public Facolta abilitato(Boolean abilitato) {
        this.abilitato = abilitato;
        return this;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public Set<Cdl> getRelFacCdls() {
        return relFacCdls;
    }

    public Facolta relFacCdls(Set<Cdl> cdls) {
        this.relFacCdls = cdls;
        return this;
    }

    public Facolta addRelFacCdls(Cdl cdl) {
        this.relFacCdls.add(cdl);
        cdl.setRelCdlsFac(this);
        return this;
    }

    public Facolta removeRelFacCdls(Cdl cdl) {
        this.relFacCdls.remove(cdl);
        cdl.setRelCdlsFac(null);
        return this;
    }

    public void setRelFacCdls(Set<Cdl> cdls) {
        this.relFacCdls = cdls;
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
        Facolta facolta = (Facolta) o;
        if (facolta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facolta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Facolta{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataModifica='" + getDataModifica() + "'" +
            ", abilitato='" + isAbilitato() + "'" +
            "}";
    }
}
