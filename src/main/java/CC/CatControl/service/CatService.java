package CC.CatControl.service;

import java.util.ArrayList;

/** The interface that describe the implementation of the CatService-Class, as a part of the service package for Catcontrol
 *
 * the implementation should contain a private ArrayList<Cat> declared as field-varioble*/


public interface CatService {
    /** The method readCats() loads a ArrayList<Cat> from a Repository into the field ArrayList,
     * for example at start of the application*/
    ArrayList<Cat> readCats();

    /** getCatlist() is a getter for this field - ArrayList<Cat>.
     * @return ArrayList<Cat>*/
    ArrayList<Cat> getCatlist();

    /** saveCat(Cat cat) adds a nonNull - Cat.class-Object to field - ArrayList<Cat> and use writeCats() from a CatRepository-
     * Implementations to save the list into the repository
     *
     * @return the updatet field-ArrayList<Cat> or null if no Cat was added to ArrayList and repository*/
    ArrayList<Cat> saveCat(Cat cat);






}
