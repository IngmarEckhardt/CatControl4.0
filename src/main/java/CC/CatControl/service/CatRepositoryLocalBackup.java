package CC.CatControl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CatRepositoryLocalBackup extends CatRepository {
    private final ObjectMapper objectMapper;

    public CatRepositoryLocalBackup(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }

    @Override
    ArrayList<Cat> readCats() {
        ArrayList<Cat> catArray = new ArrayList<>();
        File datei = new File(System.getProperty("user.home") + File.separator + "Cats.json");
        try {
            catArray.addAll(Arrays.asList(objectMapper.readValue(datei, Cat[].class)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return catArray;
    }

    @Override
    boolean writeCats(ArrayList<Cat> catList) {
        try {
            File datei = new File(System.getProperty("user.home") + File.separator + "Cats.json");
            objectMapper.writeValue(datei, catList);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    boolean addNewCat(Cat cat) {
        return false;
    }

    @Override
    boolean deleteCat(Cat cat) {
        return false;
    }
    @Override
    boolean editCat(Cat cat) {
        return false;
    }
}