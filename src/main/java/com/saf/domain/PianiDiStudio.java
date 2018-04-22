package com.saf.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PianiDiStudio.
 */
@Entity
@Table(name = "piani_di_studio")
public class PianiDiStudio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "abilitato")
    private Boolean abilitato;

    @Column(name = "data_modifica")
    private LocalDate dataModifica;

    @ManyToOne
    private AnnoAccademico relAnnoAccademico;

    @ManyToOne
    private Cdl relPdsCdl;

    @ManyToMany
    @JoinTable(name = "piani_di_studio_rel_pds_mat",
               joinColumns = @JoinColumn(name="piani_di_studios_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="rel_pds_mats_id", referencedColumnName="id"))
    private Set<Materie> relPdsMats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAbilitato() {
        return abilitato;
    }

    public PianiDiStudio abilitato(Boolean abilitato) {
        this.abilitato = abilitato;
        return this;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public LocalDate getDataModifica() {
        return dataModifica;
    }

    public PianiDiStudio dataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
        return this;
    }

    public void setDataModifica(LocalDate dataModifica) {
        this.dataModifica = dataModifica;
    }

    public AnnoAccademico getRelAnnoAccademico() {
        return relAnnoAccademico;
    }

    public PianiDiStudio relAnnoAccademico(AnnoAccademico annoAccademico) {
        this.relAnnoAccademico = annoAccademico;
        return this;
    }

    public void setRelAnnoAccademico(AnnoAccademico annoAccademico) {
        this.relAnnoAccademico = annoAccademico;
    }

    public Cdl getRelPdsCdl() {
        return relPdsCdl;
    }

    public PianiDiStudio relPdsCdl(Cdl cdl) {
        this.relPdsCdl = cdl;
        return this;
    }

    public void setRelPdsCdl(Cdl cdl) {
        this.relPdsCdl = cdl;
    }

    public Set<Materie> getRelPdsMats() {
        return relPdsMats;
    }

    public PianiDiStudio relPdsMats(Set<Materie> materies) {
        this.relPdsMats = materies;
        return this;
    }

    public PianiDiStudio addRelPdsMat(Materie materie) {
        this.relPdsMats.add(materie);
        return this;
    }

    public PianiDiStudio removeRelPdsMat(Materie materie) {
        this.relPdsMats.remove(materie);
        return this;
    }

    public void setRelPdsMats(Set<Materie> materies) {
        this.relPdsMats = materies;
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
        PianiDiStudio pianiDiStudio = (PianiDiStudio) o;
        if (pianiDiStudio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pianiDiStudio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PianiDiStudio{" +
            "id=" + getId() +
            ", abilitato='" + isAbilitato() + "'" +
            ", dataModifica='" + getDataModifica() + "'" +
            "}";
    }
}
