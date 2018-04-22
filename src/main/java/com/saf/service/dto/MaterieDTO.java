package com.saf.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Materie entity.
 */
public class MaterieDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Integer cfu;

    private Boolean abilitato;

    private LocalDate dataModifica;

    private Long relMatsCdlId;

    private String relMatsCdlNome;

    private Long relMatsTutId;

    private String relMatsTutCognome;

    private Long relMatsDocId;

    private String relMatsDocCognome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCfu() {
        return cfu;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    public Boolean isAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public LocalDate getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
    }

    public Long getRelMatsCdlId() {
        return relMatsCdlId;
    }

    public void setRelMatsCdlId(Long cdlId) {
        this.relMatsCdlId = cdlId;
    }

    public String getRelMatsCdlNome() {
        return relMatsCdlNome;
    }

    public void setRelMatsCdlNome(String cdlNome) {
        this.relMatsCdlNome = cdlNome;
    }

    public Long getRelMatsTutId() {
        return relMatsTutId;
    }

    public void setRelMatsTutId(Long tutorId) {
        this.relMatsTutId = tutorId;
    }

    public String getRelMatsTutCognome() {
        return relMatsTutCognome;
    }

    public void setRelMatsTutCognome(String tutorCognome) {
        this.relMatsTutCognome = tutorCognome;
    }

    public Long getRelMatsDocId() {
        return relMatsDocId;
    }

    public void setRelMatsDocId(Long docentiId) {
        this.relMatsDocId = docentiId;
    }

    public String getRelMatsDocCognome() {
        return relMatsDocCognome;
    }

    public void setRelMatsDocCognome(String docentiCognome) {
        this.relMatsDocCognome = docentiCognome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterieDTO materieDTO = (MaterieDTO) o;
        if(materieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterieDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cfu=" + getCfu() +
            ", abilitato='" + isAbilitato() + "'" +
            ", dataModifica='" + getDataModifica() + "'" +
            "}";
    }
}
