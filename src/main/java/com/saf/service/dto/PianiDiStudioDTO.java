package com.saf.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PianiDiStudio entity.
 */
public class PianiDiStudioDTO implements Serializable {

    private Long id;

    private Boolean abilitato;

    private LocalDate dataModifica;

    private Long relAnnoAccademicoId;

    private Long relPdsCdlId;

    private Set<MaterieDTO> relPdsMats = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getRelAnnoAccademicoId() {
        return relAnnoAccademicoId;
    }

    public void setRelAnnoAccademicoId(Long annoAccademicoId) {
        this.relAnnoAccademicoId = annoAccademicoId;
    }

    public Long getRelPdsCdlId() {
        return relPdsCdlId;
    }

    public void setRelPdsCdlId(Long cdlId) {
        this.relPdsCdlId = cdlId;
    }

    public Set<MaterieDTO> getRelPdsMats() {
        return relPdsMats;
    }

    public void setRelPdsMats(Set<MaterieDTO> materies) {
        this.relPdsMats = materies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PianiDiStudioDTO pianiDiStudioDTO = (PianiDiStudioDTO) o;
        if(pianiDiStudioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pianiDiStudioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PianiDiStudioDTO{" +
            "id=" + getId() +
            ", abilitato='" + isAbilitato() + "'" +
            ", dataModifica='" + getDataModifica() + "'" +
            "}";
    }
}
