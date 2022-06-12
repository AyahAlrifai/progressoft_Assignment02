package com.progressoft.fun;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progressoft.model.FX_Deal;

public class DealHTTPControler {
    public static String apiUrl = "";
    private ProgressLogger logger;

    public static void initDealHTTPControler(String url) {
        apiUrl = url;
    }

    public DealHTTPControler() {
        this.logger = new ProgressLogger(this.getClass().getPackageName().toString());
    }

    public void addNewDeal(FX_Deal deal) {
        try {
            String method = "POST";
            String path = "/deal?local=" + Locale.getDefault().toString();

            HttpURLConnection conn = createConnection(method, path);

            String jsonInputString = "{\"fromISO\": \""+deal.getFromISO()+"\", \"toISO\": \""+deal.getToISO()+"\", \"amount\":\""+deal.getAmount()+"\"}";
            setRequestBody(conn, jsonInputString);

            conn.connect();
            int responsecode = conn.getResponseCode();
            logger.log(Level.INFO, "response code " + responsecode);
            BufferedReader br;
            if (conn.getResponseCode() == 200) {
                System.out.println("Deal added successfully");
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String message = getResponseMessage(br);
                logger.log(Level.WARNING, message);
                System.out.println(message);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private String getResponseMessage(BufferedReader br) throws IOException, JsonProcessingException, JsonMappingException {
        String strCurrentLine;
        String json= "";
        while ((strCurrentLine = br.readLine()) != null) {
            json += strCurrentLine;
                break;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = mapper.readValue(json, Map.class);
        String message = map.get("message");
        return message;
    }

    private HttpURLConnection createConnection(String method, String path)
            throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL(apiUrl + path);
        logger.log(Level.INFO, url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Acceptcharset", "en-us");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setRequestProperty("charset", "EN-US");    
        conn.setRequestProperty("Accept", "application/json, text/javascript, */*");       
        return conn;
    }

    private void setRequestBody(HttpURLConnection conn, String jsonInputString)
            throws UnsupportedEncodingException, IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    public ArrayList<FX_Deal> getAllDeals() {
        ArrayList<FX_Deal> deals = new ArrayList<>();
        try {
            String method = "GET";
            String path = "/deal?local=" + Locale.getDefault().toString();

            HttpURLConnection conn = createConnection(method, path);

            conn.connect();
            int responsecode = conn.getResponseCode();
            logger.log(Level.INFO, "response code " + responsecode);

            BufferedReader br;
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                String encoding = conn.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                String body = IOUtils.toString(in, encoding);

                Object obj=JSONValue.parse(body.toString());
                JSONArray finalResult=(JSONArray)obj;

                for(int i=0 ; i<finalResult.size() ; i++) {
                    JSONObject dealObject = (JSONObject) (finalResult.get(i));
                    FX_Deal deal = new FX_Deal();
                    deal.setFromISO((String) dealObject.get("fromISO"));
                    deal.setToISO((String)dealObject.get("toISO"));
                    double amount = (double) dealObject.get("amount");
                    deal.setAmount((float)amount);
                    /** deal.setTimeStamp(map.get("timeStamp")); **/
                    deals.add(deal);
                }
                return deals;
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String message = getResponseMessage(br);
                logger.log(Level.WARNING, message);
                System.out.println(message);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return deals;
    }
}
