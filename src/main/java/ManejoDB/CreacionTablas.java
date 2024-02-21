package ManejoDB;

import conexiones.ConexionMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreacionTablas {

    public static void crear() {

        //Creamos las tablas
        try (Connection con = ConexionMySQL.conectar("FP24MJO")) {
            try (Statement stmt = con.createStatement()) {
                try {
                    //Borramos la tabla si existe
                    stmt.execute("DROP TABLE IF EXISTS FAMILY");
                    //Creamos la tabla FAMILY
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS FAMILY ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "FamilyCode VARCHAR(20) UNIQUE, "
                            + "FamilyName VARCHAR(70) NOT NULL)");
                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla FAMILY");
                }

                try {

                    stmt.execute("DROP TABLE IF EXISTS PROJECT");
                    //Creamos la tabla PROJECT
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PROJECT ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "Title VARCHAR(50) NOT NULL UNIQUE, "
                            + "Logo BLOB, Web VARCHAR(100), "
                            + "ProjectDescription TEXT, "
                            + "State VARCHAR(20) NOT NULL CHECK(STATE IN('Pendiente','Completado','En Curso')), "
                            + "InitDate DATE, EndDate DATE)");

                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla PROJECT");
                }

                try {

                    stmt.execute("DROP TABLE IF EXISTS ENTITY");
                    //Creamos la tabla ENTITY
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ENTITY ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "EntityName VARCHAR(60) NOT NULL, "
                            + "EntityCode VARCHAR(20) UNIQUE, "
                            + "Web VARCHAR(100), "
                            + "Email VARCHAR(100))");
                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla ENTITY");
                }

                try {

                    stmt.execute("DROP TABLE IF EXISTS USERS");
                    //Creamos la tabla USERS
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERS ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                            + "IdEntity INT UNSIGNED NOT NULL,"
                            + "Login VARCHAR(50) UNIQUE NOT NULL,"
                            + "UserName VARCHAR(20),"
                            + "Surname VARCHAR(100),"
                            + "Email VARCHAR(70),"
                            + "LinkedIn VARCHAR(30),"
                            + "CONSTRAINT fvEntiCol FOREIGN KEY (IdEntity) REFERENCES ENTITY (Id))");

                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla USERS");
                }

                try {
                    stmt.execute("DROP TABLE IF EXISTS TECHNOLOGY");
                    //creamos la tabla TECHNOLOGY
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TECHNOLOGY ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "Tag VARCHAR(50) UNIQUE NOT NULL, "
                            + "TechName VARCHAR(70))");
                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla TECHNOLOGY");
                }

                try {

                    stmt.execute("DROP TABLE IF EXISTS FAVOURITE");
                    //Creamos la tabla FAVOURITE
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS FAVOURITE ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "IdProject INT UNSIGNED NOT NULL, "
                            + "IdUser INT UNSIGNED NOT NULL, "
                            + "FOREIGN KEY (IdProject) REFERENCES PROJECT (Id), "
                            + "FOREIGN KEY (IdUser) REFERENCES USERS (Id))");

                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla FAVOURITE");
                }

                try {

                    stmt.execute("DROP TABLE IF EXISTS IMPLEMENT");
                    //Creamos la tabla IMPLEMENT
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS IMPLEMENT ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "IdProject INT UNSIGNED NOT NULL, "
                            + "IdTechnology INT UNSIGNED NOT NULL, "
                            + "CONSTRAINT fvProjImp FOREIGN KEY (IdProject) REFERENCES PROJECT (Id), "
                            + "CONSTRAINT fvTechImp FOREIGN KEY (IdTechnology) REFERENCES TECHNOLOGY (Id))");
                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla IMPLEMENT");
                }

                try {
                    stmt.execute("DROP TABLE IF EXISTS COLLABORATION");
                    //Creamos los constraints de las tablas que lo necesitan (FOREIGN KEY)
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS COLLABORATION ("
                            + "Id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "IdProject INT UNSIGNED NOT NULL, "
                            + "IdUser INT UNSIGNED NOT NULL, "
                            + "IdFamily INT UNSIGNED NOT NULL, "
                            + "IsManager BOOLEAN, "
                            + "CONSTRAINT fvProjCol FOREIGN KEY (IdProject) REFERENCES PROJECT (Id), "
                            + "CONSTRAINT fvUserCol FOREIGN KEY (IdUser) REFERENCES USERS (Id), "
                            + "CONSTRAINT fvFamCol FOREIGN KEY (IdFamily) REFERENCES FAMILY (Id))");
                } catch (SQLException e) {
                    System.out.println("Error al crear la tabla COLLABORATION");
                }
            } catch (SQLException e) {
                System.out.println("Error al crear las tablas");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion");
        }
    }
}
