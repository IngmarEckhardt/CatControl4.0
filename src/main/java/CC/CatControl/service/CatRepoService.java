package CC.CatControl.service;

import java.util.ArrayList;

abstract class CatRepoService {
    /** readCats()-Method should use non-static readCats()-method from the primary Repository to get and return a ArrayList<Cat>
     * or ask a non-static method readCats() to get a ArrayList from the BackUpRepository.
     * If the primary Repository is working it should use save the list into the BackUpRepository with its non-static-method writeCats()
     *
     * @return ArrayList<Cat>
     */
    abstract ArrayList<Cat> readCats();

    /** addNewCat() should save a new cat to the primary Repository
     *
     * @param catToAdd - a newly created cat
     */
    abstract boolean addNewCat(Cat catToAdd);

    abstract boolean editCat(Cat catToEdit);

    abstract boolean deleteCat(Cat cat);
}
