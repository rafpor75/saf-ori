package com.saf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Docenti.
 */
@Entity
@Table(name = "docenti")
public class Docenti implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email")
    private String email;

    @Column(name = "abilitato")
    private Boolean abilitato;

    @OneToMany(mappedBy = "relMatsDoc")
    @JsonIgnore
    private Set<Materie> relDocMats = new HashSet<>();

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

    public Docenti nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Docenti cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public Docenti email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isAbilitato() {
        return abilitato;
    }

    public Docenti abilitato(Boolean abilitato) {
        this.abilitato = abilitato;
        return this;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public Set<Materie> getRelDocMats() {
        return relDocMats;
    }

    public Docenti relDocMats(Set<Materie> materies) {
        this.relDocMats = materies;
        return this;
    }

    public Docenti addRelDocMats(Materie materie) {
        this.relDocMats.add(materie);
        materie.setRelMatsDoc(this);
        return this;
    }

    public Docenti removeRelDocMats(Materie materie) {
        this.relDocMats.remove(materie);
        materie.setRelMatsDoc(null);
        return this;
    }

    public void setRelDocMats(Set<Materie> materies) {
        this.relDocMats = materies;
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
        Docenti docenti = (Docenti) o;
        if (docenti.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), docenti.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Docenti{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", email='" + getEmail() + "'" +
            ", abilitato='" + isAbilitato() + "'" +
            "}";
    }
}
