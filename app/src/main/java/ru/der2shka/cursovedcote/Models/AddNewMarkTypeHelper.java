package ru.der2shka.cursovedcote.Models;

import java.util.Optional;

public class AddNewMarkTypeHelper {
    private static AddNewMarkTypeHelper instance = null;

    // Name.
    private Optional<String> name = Optional.ofNullable("");

    // Multiplier.
    private Optional<Integer> multiplier = Optional.ofNullable(1);

    /**
     * Return singleton object. If not exist then create them.
     * @return AddNewMarkTypeHelper instance.
     * **/
    public static AddNewMarkTypeHelper getInstance() {
        if (instance == null) {
            instance = new AddNewMarkTypeHelper();
        }

        return instance;
    }

    /**
     *  Set new name value.
     * @param newName Optional new name string value.
     * @return Return true if name was updated, else false.
     */
    public Boolean setNameValue(Optional<String> newName) {
        if (newName.isPresent()) {
            name = newName;
            return true;
        }

        return false;
    }

    /**
     *  Get name string value.
     * @return if name is present, return name, else &quot;None value&quot;
     * **/
    public String getNameValue() {
        return name.orElse("None value");
    }

    /**
     *  Set new multiplier value.
     * @param newMultiplier Optional new multiplier int value.
     * @return Return true if multiplier was updated, else false.
     */
    public Boolean setMultiplierValue(Optional<Integer> newMultiplier) {
        if (newMultiplier.isPresent()) {
            multiplier = newMultiplier;
            return true;
        }

        return false;
    }

    /**
     *  Get multiplier int value.
     * @return if multiplier is present, return multiplier, else 0
     * **/
    public Integer getMultiplierValue() {
        return multiplier.orElse(0);
    }
}
