package entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Arrays;

@Entity
@Table(name = "PROJECT", schema = "FP24MJO", catalog = "")
public class ProjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private int id;
    @Basic
    @Column(name = "Title")
    private String title;
    @Basic
    @Column(name = "Logo")
    private int logo;
    @Basic
    @Column(name = "Web")
    private String web;
    @Basic
    @Column(name = "ProjectDescription")
    private String projectDescription;
    @Basic
    @Column(name = "State")
    private String state;
    @Basic
    @Column(name = "InitDate")
    private Date initDate;
    @Basic
    @Column(name = "EndDate")
    private Date endDate;

    public ProjectEntity( String title, int logo, String web, String projectDescription, String state, Date initDate, Date endDate) {
        this.title = title;
        this.logo = logo;
        this.web = web;
        this.projectDescription = projectDescription;
        this.state = state;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public ProjectEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectEntity that = (ProjectEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (web != null ? !web.equals(that.web) : that.web != null) return false;
        if (projectDescription != null ? !projectDescription.equals(that.projectDescription) : that.projectDescription != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (initDate != null ? !initDate.equals(that.initDate) : that.initDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (web != null ? web.hashCode() : 0);
        result = 31 * result + (projectDescription != null ? projectDescription.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (initDate != null ? initDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
