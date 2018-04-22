package com.saf.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Facolta entity.
 */
public class FacoltaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private LocalDate dataModifica;

    private Boolean abilitato;

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

    public LocalDate getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
    }

    public Boolean isAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacoltaDTO facoltaDTO = (FacoltaDTO) o;
        if(facoltaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facoltaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacoltaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataModifica='" + getDataModifica() + "'" +
            ", abilitato='" + isAbilitato() + "'" +
            "}";
    }
}
