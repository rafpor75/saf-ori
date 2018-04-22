package com.saf.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sedi entity.
 */
public class SediDTO implements Serializable {

    private Long id;

    @NotNull
    private String sede;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SediDTO sediDTO = (SediDTO) o;
        if(sediDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sediDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SediDTO{" +
            "id=" + getId() +
            ", sede='" + getSede() + "'" +
            "}";
    }
}
