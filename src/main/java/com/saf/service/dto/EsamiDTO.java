package com.saf.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Esami entity.
 */
public class EsamiDTO implements Serializable {

    private Long id;

    private LocalDate data;

    private Long relEsamiSediId;

    private String relEsamiSediSede;

    private Long relMatEsamiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getRelEsamiSediId() {
        return relEsamiSediId;
    }

    public void setRelEsamiSediId(Long sediId) {
        this.relEsamiSediId = sediId;
    }

    public String getRelEsamiSediSede() {
        return relEsamiSediSede;
    }

    public void setRelEsamiSediSede(String sediSede) {
        this.relEsamiSediSede = sediSede;
    }

    public Long getRelMatEsamiId() {
        return relMatEsamiId;
    }

    public void setRelMatEsamiId(Long materieId) {
        this.relMatEsamiId = materieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EsamiDTO esamiDTO = (EsamiDTO) o;
        if(esamiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), esamiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EsamiDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
