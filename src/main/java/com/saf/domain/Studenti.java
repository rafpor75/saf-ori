package com.saf.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Studenti.
 */
@Entity
@Table(name = "studenti")
public class Studenti implements Serializable {

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

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "citta")
    private String citta;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "stato")
    private String stato;

    @Column(name = "cap")
    private String cap;

    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;

    @Column(name = "luogo_di_nascita")
    private String luogoDiNascita;

    @NotNull
    @Column(name = "matricola", nullable = false)
    private String matricola;

    @Column(name = "tempo_parziale")
    private Boolean tempoParziale;

    @Column(name = "abilitato")
    private Boolean abilitato;

    @Column(name = "data_modifica")
    private LocalDate dataModifica;

    @ManyToOne
    private Cdl relStuCdl;

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

    public Studenti nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Studenti cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public Studenti email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Studenti telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Studenti indirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
        return this;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public Studenti citta(String citta) {
        this.citta = citta;
        return this;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public Studenti provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getStato() {
        return stato;
    }

    public Studenti stato(String stato) {
        this.stato = stato;
        return this;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCap() {
        return cap;
    }

    public Studenti cap(String cap) {
        this.cap = cap;
        return this;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public Studenti dataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
        return this;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }

    public Studenti luogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
        return this;
    }

    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }

    public String getMatricola() {
        return matricola;
    }

    public Studenti matricola(String matricola) {
        this.matricola = matricola;
        return this;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public Boolean isTempoParziale() {
        return tempoParziale;
    }

    public Studenti tempoParziale(Boolean tempoParziale) {
        this.tempoParziale = tempoParziale;
        return this;
    }

    public void setTempoParziale(Boolean tempoParziale) {
        this.tempoParziale = tempoParziale;
    }

    public Boolean isAbilitato() {
        return abilitato;
    }

    public Studenti abilitato(Boolean abilitato) {
        this.abilitato = abilitato;
        return this;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public LocalDate getDataModifica() {
        return dataModifica;
    }

    public Studenti dataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
        return this;
    }

    public void setDataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
    }

    public Cdl getRelStuCdl() {
        return relStuCdl;
    }

    public Studenti relStuCdl(Cdl cdl) {
        this.relStuCdl = cdl;
        return this;
    }

    public void setRelStuCdl(Cdl cdl) {
        this.relStuCdl = cdl;
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
        Studenti studenti = (Studenti) o;
        if (studenti.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studenti.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Studenti{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", indirizzo='" + getIndirizzo() + "'" +
            ", citta='" + getCitta() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", stato='" + getStato() + "'" +
            ", cap='" + getCap() + "'" +
            ", dataDiNascita='" + getDataDiNascita() + "'" +
            ", luogoDiNascita='" + getLuogoDiNascita() + "'" +
            ", matricola='" + getMatricola() + "'" +
            ", tempoParziale='" + isTempoParziale() + "'" +
            ", abilitato='" + isAbilitato() + "'" +
            ", dataModifica='" + getDataModifica() + "'" +
            "}";
    }
}
