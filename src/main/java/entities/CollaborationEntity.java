package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "COLLABORATION", schema = "FP24MJO", catalog = "")
public class CollaborationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "IdProject")
    private int idProject;
    @Basic
    @Column(name = "IdUser")
    private int idUser;
    @Basic
    @Column(name = "IdFamily")
    private int idFamily;
    @Basic
    @Column(name = "IsManager")
    private boolean isManager;

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollaborationEntity that = (CollaborationEntity) o;

        if (id != that.id) return false;
        if (idProject != that.idProject) return false;
        if (idUser != that.idUser) return false;
        if (idFamily != that.idFamily) return false;
        if (isManager != that.isManager) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idProject;
        result = 31 * result + idUser;
        result = 31 * result + idFamily;
        result = 31 * result + (isManager ? 1 : 0);
        return result;
    }
}
