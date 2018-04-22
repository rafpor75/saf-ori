package com.saf.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A NoteEsame.
 */
@Entity
@Table(name = "note_esame")
public class NoteEsame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appunti")
    private String appunti;

    @Column(name = "data_dispensa")
    private LocalDate dataDispensa;

    @Column(name = "data_corsi")
    private LocalDate dataCorsi;

    @Column(name = "data_abi")
    private LocalDate dataABI;

    @Column(name = "data_riepilogo")
    private LocalDate dataRiepilogo;

    @Column(name = "ora_esame")
    private ZonedDateTime oraEsame;

    @Column(name = "costo_esame")
    private Integer costoEsame;

    @Column(name = "fasce")
    private String fasce;

    @Column(name = "piva")
    private String piva;

    @Column(name = "fattura")
    private Boolean fattura;

    @Column(name = "note_fattura")
    private String noteFattura;

    @Column(name = "email_inviata")
    private Boolean emailInviata;

    @ManyToOne
    private Studenti relNoteStud;

    @ManyToOne
    private Esami relNoteEsami;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppunti() {
        return appunti;
    }

    public NoteEsame appunti(String appunti) {
        this.appunti = appunti;
        return this;
    }

    public void setAppunti(String appunti) {
        this.appunti = appunti;
    }

    public LocalDate getDataDispensa() {
        return dataDispensa;
    }

    public NoteEsame dataDispensa(LocalDate dataDispensa) {
        this.dataDispensa = dataDispensa;
        return this;
    }

    public void setDataDispensa(LocalDate dataDispensa) {
        this.dataDispensa = dataDispensa;
    }

    public LocalDate getDataCorsi() {
        return dataCorsi;
    }

    public NoteEsame dataCorsi(LocalDate dataCorsi) {
        this.dataCorsi = dataCorsi;
        return this;
    }

    public void setDataCorsi(LocalDate dataCorsi) {
        this.dataCorsi = dataCorsi;
    }

    public LocalDate getDataABI() {
        return dataABI;
    }

    public NoteEsame dataABI(LocalDate dataABI) {
        this.dataABI = dataABI;
        return this;
    }

    public void setDataABI(LocalDate dataABI) {
        this.dataABI = dataABI;
    }

    public LocalDate getDataRiepilogo() {
        return dataRiepilogo;
    }

    public NoteEsame dataRiepilogo(LocalDate dataRiepilogo) {
        this.dataRiepilogo = dataRiepilogo;
        return this;
    }

    public void setDataRiepilogo(LocalDate dataRiepilogo) {
        this.dataRiepilogo = dataRiepilogo;
    }

    public ZonedDateTime getOraEsame() {
        return oraEsame;
    }

    public NoteEsame oraEsame(ZonedDateTime oraEsame) {
        this.oraEsame = oraEsame;
        return this;
    }

    public void setOraEsame(ZonedDateTime oraEsame) {
        this.oraEsame = oraEsame;
    }

    public Integer getCostoEsame() {
        return costoEsame;
    }

    public NoteEsame costoEsame(Integer costoEsame) {
        this.costoEsame = costoEsame;
        return this;
    }

    public void setCostoEsame(Integer costoEsame) {
        this.costoEsame = costoEsame;
    }

    public String getFasce() {
        return fasce;
    }

    public NoteEsame fasce(String fasce) {
        this.fasce = fasce;
        return this;
    }

    public void setFasce(String fasce) {
        this.fasce = fasce;
    }

    public String getPiva() {
        return piva;
    }

    public NoteEsame piva(String piva) {
        this.piva = piva;
        return this;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public Boolean isFattura() {
        return fattura;
    }

    public NoteEsame fattura(Boolean fattura) {
        this.fattura = fattura;
        return this;
    }

    public void setFattura(Boolean fattura) {
        this.fattura = fattura;
    }

    public String getNoteFattura() {
        return noteFattura;
    }

    public NoteEsame noteFattura(String noteFattura) {
        this.noteFattura = noteFattura;
        return this;
    }

    public void setNoteFattura(String noteFattura) {
        this.noteFattura = noteFattura;
    }

    public Boolean isEmailInviata() {
        return emailInviata;
    }

    public NoteEsame emailInviata(Boolean emailInviata) {
        this.emailInviata = emailInviata;
        return this;
    }

    public void setEmailInviata(Boolean emailInviata) {
        this.emailInviata = emailInviata;
    }

    public Studenti getRelNoteStud() {
        return relNoteStud;
    }

    public NoteEsame relNoteStud(Studenti studenti) {
        this.relNoteStud = studenti;
        return this;
    }

    public void setRelNoteStud(Studenti studenti) {
        this.relNoteStud = studenti;
    }

    public Esami getRelNoteEsami() {
        return relNoteEsami;
    }

    public NoteEsame relNoteEsami(Esami esami) {
        this.relNoteEsami = esami;
        return this;
    }

    public void setRelNoteEsami(Esami esami) {
        this.relNoteEsami = esami;
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
        NoteEsame noteEsame = (NoteEsame) o;
        if (noteEsame.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noteEsame.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoteEsame{" +
            "id=" + getId() +
            ", appunti='" + getAppunti() + "'" +
            ", dataDispensa='" + getDataDispensa() + "'" +
            ", dataCorsi='" + getDataCorsi() + "'" +
            ", dataABI='" + getDataABI() + "'" +
            ", dataRiepilogo='" + getDataRiepilogo() + "'" +
            ", oraEsame='" + getOraEsame() + "'" +
            ", costoEsame=" + getCostoEsame() +
            ", fasce='" + getFasce() + "'" +
            ", piva='" + getPiva() + "'" +
            ", fattura='" + isFattura() + "'" +
            ", noteFattura='" + getNoteFattura() + "'" +
            ", emailInviata='" + isEmailInviata() + "'" +
            "}";
    }
}
