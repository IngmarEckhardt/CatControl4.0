package CC.CatControl.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CatServiceImpl implements CatService {
    private static final Logger LOGGER = Logger.getLogger(CatServiceImpl.class.getName());
    private final CatRepoServiceImpl catRepoServiceImpl;
    private ArrayList<Cat> catArray;

    public CatServiceImpl(CatRepoServiceImpl catRepoServiceImpl) {
        this.catRepoServiceImpl = catRepoServiceImpl;
    }

    public ArrayList<Cat> readCats() {
        return catArray = catRepoServiceImpl.readCats();
    }

    public ArrayList<Cat> getCatlist() {
        return catArray;
    }


    public ArrayList<Cat> saveCat(Cat cat) {
        if (cat == null) {
            LOGGER.log(Level.SEVERE,
                    "The form has no contact to the application");
            return null;
        }
        else if (cat.getId() == null) {
            catRepoServiceImpl.addNewCat(cat);
        }

        else {
            catArray = (ArrayList<Cat>) catArray
                    .stream()
                    .filter(myCat -> !(myCat.getId() == cat.getId()))
                    .collect(Collectors.toList());
            catArray.add(cat);
            catRepoServiceImpl.editCat(cat);
        }
        return catArray;
    }

    public ArrayList<Cat> deleteCat(Cat cat) {
        catRepoServiceImpl.deleteCat(cat);
        catArray = (ArrayList<Cat>) catArray
                .stream()
                .filter(myCat -> !(myCat.getId() == cat.getId()))
                .collect(Collectors.toList());
        return catArray;
    }

    public ArrayList<Cat> findAll(String stringFilter) {
        ArrayList<Cat> catList = new ArrayList<>();
        for (Cat cat : catArray) {
            boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                    || cat.getName().toLowerCase().contains(stringFilter.toLowerCase());
            if (passesFilter) {
                catList.add(cat);
                System.out.println("Die catList enhält" + catList);
            }
        }
        catList.sort(new SortName());
        return catList;
    }

}