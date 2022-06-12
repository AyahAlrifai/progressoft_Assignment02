package com.ayah.progress.model;

import java.io.Serializable;

/**
 * store configuration in database by create dictionary for each category 
 * <p>
 * and each category has dictionary entries
 * <p>
 * @author Ayah Alrefai
 */
public class DIC_Dictionary implements Serializable {
    /**
     * unique id for each dictionary
     */
    private int dictionary_Id;
    /**
     * unique code for each dictionary
     */
    private String code;
    /**
     * name of dictionary
     */
    private String name;
    /**
     * if dictionary active or not
     * <p>
     * 1 is active
     * <p>
     * 0 is not active
     */
    private boolean isActive;

    public DIC_Dictionary() {
    }

    public int getDictionary_Id() {
        return dictionary_Id;
    }

    public void setDictionary_Id(int dictionary_Id) {
        this.dictionary_Id = dictionary_Id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
