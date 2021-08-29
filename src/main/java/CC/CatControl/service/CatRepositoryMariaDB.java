package CC.CatControl.service;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

@Component
public class CatRepositoryMariaDB extends CatRepository {
    private final CatRepositoryLocalBackup catRepositoryLocalBackup;
    private ArrayList<Cat> catArray = new ArrayList<>();

    public CatRepositoryMariaDB(CatRepositoryLocalBackup catRepositoryLocalBackup) {
        this.catRepositoryLocalBackup = catRepositoryLocalBackup;
    }

    ArrayList<Cat> readCats() {

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = getAllCatsStatement(conn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("NAME");
                if (name == null || name.length() < 1 || name.length() > 50) continue;

                int id = rs.getInt("ID");
                int age = rs.getInt("AGE");
                float weight = rs.getFloat("WEIGHT");
                boolean chubby = rs.getString("CHUBBY").equals("true");
                boolean sweet = rs.getString("SWEET").equals("true");

                Date date = rs.getDate("VACCINEDATE");
                LocalDate vaccineDate = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                catArray.add(Cat.getCat(id, name, age, vaccineDate, weight, chubby, sweet));
            }
        } catch (SQLException e) {
            System.err.println("SQL-Verbindung zur MariaDB fehlgeschlagen");
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Erstellung der ArrayList<Cat> aus SQL-Daten fehlgeschlagen");
            e.printStackTrace();
        }
        return catArray;
    }

    @Override
    boolean writeCats(ArrayList<Cat> cats) {
        return false;
    }

    boolean addNewCat(Cat cat) {
        if (cat.getName() == null || cat.getName().length() < 1 || cat.getName().length() > 50) return false;
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = insertCatStatement(conn, cat);
            stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL-Verbindung fehlgeschlagen. Die Katze wurde nicht gespeichert.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    boolean deleteCat(Cat cat) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = deleteCatStatement(conn, cat);
            stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL-Verbindung fehlgeschlagen. Die Katze wurde nicht gelöscht.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    boolean editCat(Cat cat) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = editCatStatement(conn, cat);
            stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL-Verbindung fehlgeschlagen. Die Katze wurde nicht gelöscht.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/", "max1", "password");
    }

    private PreparedStatement getAllCatsStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("select ID, NAME, AGE, VACCINEDATE, WEIGHT, CHUBBY, SWEET from CatControl.Cats");
    }

    private PreparedStatement insertCatStatement(Connection conn, Cat catToInsert) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO CatControl.Cats (NAME, AGE, VACCINEDATE, WEIGHT, CHUBBY, SWEET) VALUES (?,?,?,?,?,?)");

        stmt.setString(1, catToInsert.getName());
        stmt.setInt(2, catToInsert.getAge());
        stmt.setDate(3, Date.valueOf(catToInsert.getVaccineDate()));
        stmt.setFloat(4, catToInsert.getWeight());
        stmt.setString(5, catToInsert.isChubby() ? "true" : "false");
        stmt.setString(6, catToInsert.isSweet() ? "true" : "false");

        return stmt;
    }

    private PreparedStatement deleteCatStatement(Connection conn, Cat cat) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM CatControl.Cats WHERE ID = ?");
        stmt.setInt(1, cat.getId());

        return stmt;
    }

    private PreparedStatement editCatStatement(Connection conn, Cat catToEdit) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
                "REPLACE INTO CatControl.Cats (ID, NAME, AGE, VACCINEDATE, WEIGHT, CHUBBY, SWEET) VALUES (?,?,?,?,?,?,?)");

        stmt.setInt(1, catToEdit.getId());
        stmt.setString(2, catToEdit.getName());
        stmt.setInt(3, catToEdit.getAge());
        stmt.setDate(4, Date.valueOf(catToEdit.getVaccineDate()));
        stmt.setFloat(5, catToEdit.getWeight());
        stmt.setString(6, catToEdit.isChubby() ? "true" : "false");
        stmt.setString(7, catToEdit.isSweet() ? "true" : "false");

        return stmt;
    }
}
