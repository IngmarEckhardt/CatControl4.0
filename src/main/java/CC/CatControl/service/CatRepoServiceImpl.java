package CC.CatControl.service;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class CatRepoServiceImpl extends CatRepoService {
    private final CatRepositoryLocalBackup catRepoLocalBackup;
    private final CatRepositoryMariaDB catRepoMariaDB;
    private ArrayList<Cat> catArray;
    public CatRepoServiceImpl(CatRepositoryLocalBackup catRepoLocalBackup, CatRepositoryMariaDB catRepoMariaDB) {

        this.catRepoLocalBackup = catRepoLocalBackup;
        this.catRepoMariaDB = catRepoMariaDB;
    }
    @Override
    public ArrayList<Cat> readCats() {
            catArray = readCatsFromSQL();

            if (catArray == null) {
                System.err.println("primäres Repository ausgefallen, lade Backup-Repository");
                catArray = readBackUp();
            }
            if (catArray == null){
                System.err.println("Primäres Repository und BackUp ausgefallen, Datenbank wurde nicht geladen.");
                return null;
            }
            saveBackUp(catArray);

        return catArray;
    }

    private ArrayList<Cat> readBackUp() {
        return catRepoLocalBackup.readCats();
    }

    private ArrayList<Cat> readCatsFromSQL() {
        return catRepoMariaDB.readCats();
    }
    private void saveBackUp(ArrayList<Cat> backUpCats) {
        catRepoLocalBackup.writeCats(backUpCats);
    }

    @Override
    boolean addNewCat(Cat catToAdd) {
        return catRepoMariaDB.addNewCat(catToAdd);
    }
    @Override
    boolean editCat(Cat catToEdit) {
        return catRepoMariaDB.editCat(catToEdit);
    }
    @Override
    boolean deleteCat(Cat cat) {
        return catRepoMariaDB.deleteCat(cat);
    }
}
