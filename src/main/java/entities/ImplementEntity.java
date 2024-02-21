package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "IMPLEMENT", schema = "FP24MJO", catalog = "")
public class ImplementEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "IdProject")
    private int idProject;
    @Basic
    @Column(name = "IdTechnology")
    private int idTechnology;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdTechnology() {
        return idTechnology;
    }

    public void setIdTechnology(int idTechnology) {
        this.idTechnology = idTechnology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImplementEntity that = (ImplementEntity) o;

        if (id != that.id) return false;
        if (idProject != that.idProject) return false;
        if (idTechnology != that.idTechnology) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idProject;
        result = 31 * result + idTechnology;
        return result;
    }
}
