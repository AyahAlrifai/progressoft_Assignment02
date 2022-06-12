package com.ayah.progress.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayah.progress.Exceptions.*;
import com.ayah.progress.fun.DataBaseConnection;
import com.ayah.progress.fun.ProgressLogger;
import com.ayah.progress.model.DIC_Dictionary;
import com.ayah.progress.model.DIC_DictionaryEntry;
import com.ayah.progress.model.FX_Deal;

/**
 * API REST controller for progressoft assignment
 * @author Ayah Al-refai
 */
@RestController
@RequestMapping("/")
public class APIRestController {
    @CrossOrigin
	@PostMapping("/deal")
    public boolean addNewDeal(@RequestBody FX_Deal deal,@RequestParam String local,HttpServletResponse response) throws ProgressExceptions, IOException, SQLException {
        if(local == null || local.isEmpty()) {
            Locale.setDefault(new Locale("en"));
        } else {
            Locale.setDefault(new Locale(local));
        }
        ProgressLogger logger = new ProgressLogger("Add new Deal");
        ResourceBundle bundle = ResourceBundle.getBundle("localization.MessageBundle");

        logger.log(Level.INFO, "Start add new Deal");

        if(deal.getFromISO() == null ) {
            logger.log(Level.WARNING, "the value of From ISO can't be null");
            throw new FromISOException(bundle.getString("APIRestController.0"));
        } else if(deal.getToISO() == null) {
            logger.log(Level.WARNING, "the value of To ISO can't be null");
            throw new ToISOException(bundle.getString("APIRestController.1"));
        } else if(deal.getAmount() <= 0) {
            logger.log(Level.WARNING, "the value of Amount should be greater than zero");
            throw new AmountException(bundle.getString("APIRestController.2"));
        }

        return addNewDeal(deal, logger);
    }

    /**
     * add new Deal
     * @param deal
     * @param logger
     * @return true if deal added successfully
     * @throws SQLException
     */
    private boolean addNewDeal(FX_Deal deal, ProgressLogger logger) throws SQLException {
        DataBaseConnection db = new DataBaseConnection(this.getClass().getPackage().toString()+".addNewDeal");
        Connection con = db.getConnection();
        PreparedStatement pstmt;
        String sqlStatm = "insert into fx_deal (fromiso,toiso,amount,timestamp) values (?,?,?,?)";
        pstmt = con.prepareStatement(sqlStatm);
        pstmt.setString(1, deal.getFromISO());
        pstmt.setString(2, deal.getToISO());
        pstmt.setFloat( 3, deal.getAmount());
        pstmt.setString(4, LocalDateTime.now().toString());
        logger.log(Level.INFO, pstmt.toString());
        pstmt.executeUpdate();
        logger.log(Level.INFO,"new deal added successfully");
        return true;
    }

    @CrossOrigin
	@GetMapping("/dictionary")
    public ArrayList<DIC_DictionaryEntry> getDictionaryEntries(@RequestParam String dictionary_code,@RequestParam String local) throws ProgressExceptions {       
        if(local == null || local.isEmpty()) {
            Locale.setDefault(new Locale("en"));
        } else {
            Locale.setDefault(new Locale(local));
        }

        ProgressLogger logger = new ProgressLogger("get dictionary entries for "+dictionary_code);
        ResourceBundle bundle = ResourceBundle.getBundle("localization.MessageBundle");

        if(dictionary_code.isEmpty()) {
            throw new DictionaryCodeException(bundle.getString("APIRestController.3"));
        }
        logger.log(Level.INFO, "get dictionary entries for "+dictionary_code);

        return getDictionaryEntries(dictionary_code, logger);
    }

    /**
     * get dictionary entries from data base where dictionaryCode equal dictionary_code
     * @param dictionary_code
     * @param logger
     * @return ArrayList of DIC_DictionaryEntry Entities
     */
    private ArrayList<DIC_DictionaryEntry> getDictionaryEntries(String dictionary_code, ProgressLogger logger) {
        ArrayList<DIC_DictionaryEntry> dicEntries = new ArrayList<DIC_DictionaryEntry>();
        DataBaseConnection db = new DataBaseConnection(this.getClass().getPackage().toString()+".getDictionaryEntries");
        Connection con = db.getConnection();
        PreparedStatement pstmt;
        String sqlStatm = "select dic_dictionary.*,dic_dictionaryentry.* from dic_dictionary inner join dic_dictionaryentry on dic_dictionary.dictionary_id = dic_dictionaryentry.dictionary_id where dic_dictionary.code =? ;";
		try {
			pstmt = con.prepareStatement(sqlStatm);
			pstmt.setString(1, dictionary_code);
            logger.log(Level.INFO, pstmt.toString());
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
				DIC_DictionaryEntry dicEntry = new DIC_DictionaryEntry();
				DIC_Dictionary dic = new DIC_Dictionary();

                dic.setDictionary_Id(result.getInt("dic_dictionary.dictionary_Id"));
                dic.setCode(result.getString("dic_dictionary.code"));
                dic.setName(result.getString("dic_dictionary.name"));
                dic.setActive(result.getBoolean("dic_dictionary.isActive"));

                dicEntry.setDictionary(dic);
                dicEntry.setDictionaryEntry_Id(result.getInt("dic_dictionaryentry.dictionaryEntry_Id"));
                dicEntry.setCode(result.getString("dic_dictionaryentry.code"));
                dicEntry.setLocal(result.getString("dic_dictionaryentry.local"));
                dicEntry.setName(result.getString("dic_dictionaryentry.name"));
				dicEntries.add(dicEntry);
			}
		} catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
		} 
        return dicEntries;
    }

    @CrossOrigin
	@GetMapping("/deal")
    public ArrayList<FX_Deal> getAllDeals(@RequestParam String local) throws ProgressExceptions {       
        if(local == null || local.isEmpty()) {
            Locale.setDefault(new Locale("en"));
        } else {
            Locale.setDefault(new Locale(local));
        }

        ProgressLogger logger = new ProgressLogger("get all deals");
        return getDealsEntries(logger,local);
    }

    private ArrayList<FX_Deal> getDealsEntries(ProgressLogger logger,String local) {
        ArrayList<FX_Deal> deals = new ArrayList<FX_Deal>();
        DataBaseConnection db = new DataBaseConnection(this.getClass().getPackage().toString()+".getDictionaryEntries");
        Connection con = db.getConnection();
        PreparedStatement pstmt;
        String sqlStatm = "select distinct fx_deal.*,fromISO.NAME,toISO.NAME from fx_deal inner join dic_dictionaryentry fromISO on fx_deal.fromiso = fromISO.code inner join dic_dictionaryentry toISO on fx_deal.toiso = toISO.code inner join dic_dictionary on dic_dictionary.dictionary_id = fromISO.dictionary_id and dic_dictionary.dictionary_id = toISO.dictionary_id  where dic_dictionary.code = 'DIC_ISO' AND fromISO.LOCAL = ? AND toISO.local = ?;";
		try {
			pstmt = con.prepareStatement(sqlStatm);
			pstmt.setString(1, local);
			pstmt.setString(2, local);
            logger.log(Level.INFO, pstmt.toString());
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
				FX_Deal deal = new FX_Deal();
                deal.setFromISO(result.getString("fromISO.NAME"));
                deal.setToISO(result.getString("toISO.NAME"));
                deal.setAmount(result.getFloat("fx_deal.amount"));
                /*
                 * result.getDate("fx_deal.timestamp")
                 * deal.setTimeStamp()
                 */
				deals.add(deal);
			}
		} catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
		} 
        return deals;
    }

}
