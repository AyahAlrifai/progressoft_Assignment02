package com.progressoft.model;

import java.io.Serializable;

/**
 * store dictionary entities for each dictionary in database
 * <p>
 * allow more than one language
 * <p>
 * @author Ayah Alrefai
 */
public class DIC_DictionaryEntry implements Serializable {
    /**
     * unique id for each dictionary entry
     * <p>
     * NOT NULL
     */
    private int dictionaryEntry_Id;
    /**
     * which dictionary this dictionary entry follow up
     */
    private DIC_Dictionary dictionary;
    /**
     * name of dictionary
     * <p>
     * user can see it
     */
    private String name;
    /**
     * code of dictionary entry
     * <p>
     * unique per dictionary
     */
    private String code;
    /**
     * the language of entry
     * <p>
     * such as 'en' if english 'ar' if arabic and so on
     */
    private String local;

    public DIC_DictionaryEntry() {
    }

    public int getDictionaryEntry_Id() {
        return dictionaryEntry_Id;
    }

    public void setDictionaryEntry_Id(int dictionaryEntry_Id) {
        this.dictionaryEntry_Id = dictionaryEntry_Id;
    }

    public DIC_Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(DIC_Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
