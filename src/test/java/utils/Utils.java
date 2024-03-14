package utils;

import config.UsersModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static void doScroll(WebDriver driver, int stratPos, int endPos){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy("+stratPos+","+endPos+")");
    }

    public static void saveUsers(UsersModel usersModel) throws IOException, ParseException {
        String fileLocation = "./src/test/resources/users.json";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(fileLocation));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName",usersModel.getFirstName());
        jsonObject.put("lastName",usersModel.getLastName());
        jsonObject.put("empID",usersModel.getEmpID());
        jsonObject.put("username",usersModel.getUsername());
        jsonObject.put("password",usersModel.getPassword());

        jsonArray.add(jsonObject);

        FileWriter writer = new FileWriter(fileLocation);
        writer.write(jsonArray.toJSONString());
        writer.flush();
        writer.close();
    }


    public static String generateRandomPassword(){
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()-_+=";
        // Combine all character sets
        String allCharacters = uppercaseLetters + lowercaseLetters + digits + symbols;
        // Generate a password with at least one lowercase letter
        StringBuilder password = new StringBuilder();
        password.append(getRandomCharacter(uppercaseLetters)); // At least one uppercase letter
        password.append(getRandomCharacter(lowercaseLetters)); // At least one lowercase letter
        password.append(getRandomCharacter(digits));
        password.append(getRandomCharacter(symbols));

        // Add more random characters if needed
        int remainingLength = 10 - password.length(); // Adjust the length as per your requirement
        for (int i = 0; i < remainingLength; i++) {
            password.append(getRandomCharacter(allCharacters));
        }
        // Shuffle the characters to make the password more random
        List<Character> chars = password.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(chars);
        return chars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
    private static char getRandomCharacter(String characterSet) {
        int randomIndex = (int) (Math.random() * characterSet.length());
        return characterSet.charAt(randomIndex);
    }

    public static JSONObject getUser() throws IOException, ParseException {
        String fileLocation = "./src/test/resources/users.json";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(fileLocation));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size()-1);
        return jsonObject;
    }

    public static void waitForElement(WebDriver driver, WebElement element){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
