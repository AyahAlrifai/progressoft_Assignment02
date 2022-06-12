package com.progressoft;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;

import com.progressoft.fun.DealHTTPControler;
import com.progressoft.fun.ProgressLogger;
import com.progressoft.model.FX_Deal;

public final class App {
    private static ProgressLogger logger;

    public static void main(String[] args) {
        App app = new App();
        app.initApplication();
        startApp();
        System.gc();
    }

    private static void startApp() {
        Scanner input = new Scanner(System.in);

        ResourceBundle bundle = ResourceBundle.getBundle("localization.MessageBundle");
        Locale.setDefault(new Locale("en"));
        DealHTTPControler httpController = new DealHTTPControler();

        while (true) {
            System.out.println(bundle.getString("App.0"));
            System.out.println(bundle.getString("App.1"));
            System.out.println(bundle.getString("App.2"));
            System.out.println(bundle.getString("App.3"));
            System.out.println(bundle.getString("App.4"));

            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    addNewDeal(input, httpController);
                    break;
                case "2":
                    System.out.println("no implementation");
                    break;
                case "3":
                    ArrayList<FX_Deal> deals = httpController.getAllDeals();
                    for(FX_Deal deal : deals) {
                        System.out.println(deal);
                    }
                    break;
                case "4":
                    if (Locale.getDefault().toString().equalsIgnoreCase("en")) {
                        Locale.setDefault(new Locale("ar"));
                    } else {
                        Locale.setDefault(new Locale("en"));
                    }
                    bundle = ResourceBundle.getBundle("localization.MessageBundle");
                    break;
                default:
                    System.out.println(bundle.getString("App.5"));
                    logger.log(Level.WARNING, "invalid value please select one of these values 1,2,3");
                    break;
            }

        }
    }

    private static void addNewDeal(Scanner input, DealHTTPControler httpController) {
        FX_Deal deal = new FX_Deal();

        System.out.println("From ISO: ");
        String from = input.nextLine();
        deal.setFromISO(from); // TODO should check if value from dic_iso or not
        System.out.println("To ISO: ");
        String to = input.nextLine();
        deal.setToISO(to); // TODO should check if value from dic_iso or not
        System.out.println("Amount: ");
        String amount = input.nextLine();
        deal.setAmount(Float.valueOf(amount)); // TODO validation

        httpController.addNewDeal(deal);
    }

    /**
     * initialize application by read properties files
     * and initialize main classes such as connection class , logger class and so on
     */
    private void initApplication() {
        ResourceBundle props = ResourceBundle.getBundle("properties.Progressoft");
        String logPath = props.getString("log-path");
        ProgressLogger.initProgressLogger(logPath);
        logger = new ProgressLogger("App");

        String apiURL = props.getString("api-url");
        DealHTTPControler.initDealHTTPControler(apiURL);

    }
}
