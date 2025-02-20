package ru.der2shka.cursovedcote.Models;

import android.graphics.Path;

import java.time.LocalDate;
import java.util.Optional;

public class GeneralPageContentHelper {
    // Singleton object.
    private static Optional<GeneralPageContentHelper> generalPageContentHelper = Optional.ofNullable(null);

    // Current LocalDate.
    private static Optional<LocalDate> currentLocalDate = Optional.ofNullable(LocalDate.now());

    /**
     *   If  instance is not exist, create it and return instance.
     *   @return GeneralPageContentHelper instance
     * **/
    public static GeneralPageContentHelper getInstance() {
        if (!generalPageContentHelper.isPresent()) {
            generalPageContentHelper = Optional.ofNullable(new GeneralPageContentHelper());
        }

        return generalPageContentHelper.get();
    }

    /**
     * If newLocalDate is present then update currentLocalDate.
     * @param newLocalDate new Optional LocalDate
     * @return if currentLocalDate was updated then return true else false
     * **/
    public Boolean setNewCurrentLocalDate(Optional<LocalDate> newLocalDate) {
        if (newLocalDate.isPresent()) {
            currentLocalDate = newLocalDate;
            return true;
        }

        return false;
    }

    /**
     * Return LocalDate.
     * @return if currentLocalDate is present return current local date else LocalDate.MIN
     * **/
    public LocalDate getCurrentLocalDate() {
        return currentLocalDate.orElse(LocalDate.MIN);
    }
}
