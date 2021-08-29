package CC.CatControl.service;

import java.util.ArrayList;

/**
 * An abstract class for Repository Implementations
 */

abstract class CatRepository {

    /**
     * readCats(): Read Object-Value from the repository and use Cat.newCat(...) to create the Cat-Objects and return them in a ArrayList
     *
     * @Return: ArrayList<Cat>
     */
    abstract ArrayList<Cat> readCats();

    /**
     * writeCats() write the complete List of Cats from Memory into Persistence
     *
     * @param cats -an ArrayList<Cat>
     * @return boolean true if the List was sucessfully saved.
     */
    abstract boolean writeCats(ArrayList<Cat> cats);

    /**
     * addNewCat() write a new Cat.class-Object into the Persistence. The BackUpRepository won't need to implement this method
     *
     * @param cat - a Cat.class-Object
     * @return boolean true if the Cat was sucessfully saved in the Persistence
     */
    abstract boolean addNewCat(Cat cat);

    /**
     * deleteCat() delete a specific Cat.class-Object from the Persistence. The BackUpRepository won't need to implement this method
     *
     * @param cat - a Cat.class-Object
     * @return boolean true if the Cat was sucessfully deleted from Persistence
     */
    abstract boolean deleteCat(Cat cat);

    /**
     * editCat() replace a edited specific Cat.class-Object in Persistence. The BackUpRepository won't need to implement this method
     *
     * @param cat - a Cat.class-Object
     * @return boolean true if the Cat was sucessfully edited in the Persistence
     */
    abstract boolean editCat(Cat cat);
}
