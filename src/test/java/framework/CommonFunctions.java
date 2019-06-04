package framework;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import webdriver.Browser;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common Functions
 */
public final class CommonFunctions {

    private static final PropertiesResourceManager global = new PropertiesResourceManager("global.properties");
    private static final String PATHTEMPLATE = global.getProperty("screenshotsFolder").concat("/%1$s.png");
    private static Random random = new Random();

    private static final Integer RANDOM_POW = 10;
    private static final Integer SSN_DIGITS = 9;
    private static final Integer DAYS_IN_WEEK = 7;

    private static final String BASE_FORMAT = "MM/dd/yyyy";
    private static final String DB_FORMAT = "yyyy-MM-dd";
    private static final String SQL_FORMAT = "yyyyMMdd";

    /**
     * Constructor
     */
    private CommonFunctions() {
    }

    /**
     * Save file, if it not exists
     * @param fileName - name of the saved file
     */
    public static synchronized void saveFile(String fileName) {
        PropertiesResourceManager global = new PropertiesResourceManager("global.properties");
        try {
            URL uploadFilePath = new URL(global.getProperty("uploadPath"));
            File f = new File(fileName);
            System.out.println(f.getAbsolutePath());
            if (!f.exists()) {
                FileUtils.copyURLToFile(uploadFilePath, f);
            }
        } catch (Exception e) {
            BaseEntity.getLogger().error(e);
        }
    }

    public static String returnNothingIfNull(String result) {
        if (result == null) {
            result = "";
        }

        return result;
    }

    public static String getQueryFromFile(Path filePath) {
        File file = filePath.toFile();
        String result = null;

        try {
            List<String> list = Files.readAllLines(file.toPath());
            StringBuilder queryTemplateBuilder = new StringBuilder();

            for (Object aList : list) {
                queryTemplateBuilder.append(aList).append(" ");
            }

            String query = queryTemplateBuilder.toString().trim();
            result = query.substring(1, query.length());
        } catch (Exception var8) {
            Logger.getInstance().warn(var8.getMessage());
        }

        return returnNothingIfNull(result);
    }

    private static Pattern regexGetPattern(String regex, boolean matchCase) {
        int flags;
        if (matchCase) {
            flags = 0;
        } else {
            flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
        }
        return Pattern.compile(regex, flags);
    }

    /**
     * Compares given text with regular expression and return matching string
     * @param text - text to compare
     * @param regex - regular expression
     * @return - corresponded string
     */
    public static String regexGetMatch(String text, String regex) {
        return regexGetMatch(text, regex, false);
    }

    /**
     * Compares given text with regular expression and return matching group
     * @param text - text to compare
     * @param regex - regular expression
     * @param matchCase - match case
     * @return - matching group.
     */
    public static String regexGetMatch(String text, String regex, boolean matchCase) {
        return regexGetMatchGroup(text, regex, 0, matchCase);
    }

    /**
     * Compares given text with regular expression and return true/false depends on comparing.
     * @param text - text to compare
     * @param regex - regular expression
     * @return - true/false
     */
    public static boolean regexIsMatch(String text, String regex) {
        return regexIsMatch(text, regex, false);
    }

    /**
     * Compares given text with regular expression and return boolean match case
     * @param text - text to compare
     * @param regex - regular expression
     * @param matchCase - match case
     * @return - true/false
     */
    public static boolean regexIsMatch(String text, String regex, boolean matchCase) {
        Pattern p = regexGetPattern(regex, matchCase);
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * Compares given text with regular expression and return matching group.
     * @param text - text to compare
     * @param regex - regular expression
     * @return - matching group
     */
    public static String regexGetMatchGroup(String text, String regex) {
        return regexGetMatchGroup(text, regex, 1, false);
    }

    /**
     * Compares given text with regular expression and return matching group index, if corresponds
     * @param text - text to compare
     * @param regex - regular expression
     * @param groupIndex - index of the group
     * @return - group index or null
     */
    public static String regexGetMatchGroup(String text, String regex, int groupIndex) {
        return regexGetMatchGroup(text, regex, groupIndex, false);
    }

    /**
     * Compares given text with regular expression and return matching group index, if corresponds
     * @param text - text to compare
     * @param regex - regular expression
     * @param groupIndex - index of the group
     * @param matchCase - match case.
     * @return - matching group.
     */
    public static String regexGetMatchGroup(String text, String regex, int groupIndex, boolean matchCase) {
        Pattern p = regexGetPattern(regex, matchCase);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group(groupIndex);
        } else {
            return null;
        }
    }

    /**
     * Compares given text with regular expression and return number of the group
     * @param text - text to compare
     * @param regex - regular expression
     * @return index of the group.
     */
    public static int regexGetNumberMatchGroup(String text, String regex) {
        return regexGetNumberMatchGroup(text, regex, false);
    }

    /**
     * Compares given text with regular expression and return number of the group
     * @param text - text to compare
     * @param regex - regular expression
     * @param matchCase - match case
     * @return index of the group.
     */
    public static int regexGetNumberMatchGroup(String text, String regex, boolean matchCase) {
        int number = 0;
        Pattern p = regexGetPattern(regex, matchCase);
        Matcher m = p.matcher(text);
        while (m.find()) {
            m.group();
            number++;
        }
        return number;
    }

    //==============================================================================================
    // numbers & random

    /**
     * Get random integer
     * @param maximum - max value
     * @return - new integer
     */
    public static int getRandomInteger(int maximum) {
        return random.nextInt(maximum);
    }

    /**
     * Get random devimal number
     * @param precision - precision (quantiry of digits after comma)
     * @return - new decimal
     */
    public static String getRandomDecimalNumber(int precision) {
        double number = random.nextDouble() * precision;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(number);
    }

    /**
     * Get random integer number with specified length
     * @param length - length
     * @return new Integer
     */
    public static String getRandomIntegerNumber(int length) {
        int number = getRandomInteger(pow(RANDOM_POW, length) - 1) + 1;
        StringBuilder result = new StringBuilder(String.valueOf(number));
        while (result.length() < length) {
            result.append("0");
        }
        return result.toString();
    }

    /**
     * Get random literal code with specified length
     * @param length - length
     * @return new literal code
     */
    public static String getRandomLiteralCode(int length) {
        final String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            result.append(chars.substring(index, index + 1));
        }
        return result.toString();
    }

    /**
     * Get random alphanumerical code with specified length
     * @param length - length
     * @return new alphanumerical code
     */
    public static String getRandomAlphanumericalCode(int length) {
        final String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            result.append(chars.substring(index, index + 1));
        }
        return result.toString();
    }

    //==============================================================================================
    // dates

    /**
     * Gets current date in format "MM/dd/yyyy"
     * @return current date
     */
    public static String getCurrentDate() {
        return getCurrentDate(BASE_FORMAT);
    }

    /**
     * Gets current date in format "yyyy-MM-dd";
     * @return current date
     */
    public static String getCurrentDateDbFormat() {
        return getCurrentDate(DB_FORMAT);
    }

    /**
     * Get date in format yyyymmdd for SQL
     * @return
     */
    public static String getCurrentSQLDate(){
        return getCurrentDate(SQL_FORMAT);
    }

    /**
     * Get future date in the DB format
     * @param days - days in the future
     * @return date in the future
     */
    public static String getFutureDateDbFormat(int days) {
        Date resultDate = generateFuture(days);
        return new SimpleDateFormat(DB_FORMAT).format(resultDate);
    }
    /**
     * Get random date in the future with specified maximum days after current date
     * @param maximum - quantity of days in the future
     * @return - new date
     */
    public static String getRandomFutureDate(int maximum) {
        int days = getRandomInteger(maximum);
        // This is workaround for timezone problem
        // Setting it to Central Standard Time (CST) until JVM is fixed
        Date futureDate = generateFuture(days);
        return new SimpleDateFormat(BASE_FORMAT).format(futureDate);
    }

    /**
     * Get future date with specified date after current date
     * @param days - days after current date
     * @return - new date in the future
     */
    public static String getFutureDate(int days) {
        // This is workaround for timezone problem
        // Setting it to Central Standard Time (CST) until JVM is fixed
        Date resultDate = generateFuture(days);
        return new SimpleDateFormat(BASE_FORMAT).format(resultDate);
    }

    /**
     * Generate future days
     * @param days - days
     * @return date in the future
     */
    private static Date generateFuture(int days) {
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        GregorianCalendar gc = new GregorianCalendar();
        Date currentDate = new Date();
        gc.setTime(currentDate);
        gc.add(Calendar.DAY_OF_YEAR, days);
        return gc.getTime();
    }

    /**
     * Gets tomorrow date
     * @return tomorrow
     */
    public static String getTomorrowDate() {
        return getFutureDate(1);
    }

    /**
     * Gets days after week
     * @return day after week
     */
    public static String getNextWeekDate() {
        return getFutureDate(7);
    }

    /**
     * Gets yesterday date
     * @return yesterday
     */
    public static String getYesterdayDate() {
        return getDaysAgoDate(1);
    }

    /**
     * Get date in the past with specified days
     * @param days - quantity of days
     * @return date in the past
     */
    public static String getDaysAgoDate(int days) {
        return getDaysAgoDate(days, BASE_FORMAT);
    }

    /**
     * Get program outcomes exit date
     * @param days days
     * @return new date int the past
     */
    public static Date getProgramOutcomesExitDate(int days) {
        // This is workaround for timezone problem
        // Setting it to Central Standard Time (CST) until JVM is fixed
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        GregorianCalendar gc = new GregorianCalendar();
        Date currentDate = new Date();
        gc.setTime(currentDate);
        gc.add(Calendar.DAY_OF_YEAR, -days);
        return gc.getTime();
    }

    /**
     * Get days ago current date in specified format
     * @param days - days ago date
     * @param pattern - format date
     * @return new date in specified format
     */
    public static String getDaysAgoDate(int days, String pattern) {
        // This is workaround for timezone problem
        // Setting it to Central Standard Time (CST) until JVM is fixed
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        GregorianCalendar gc = new GregorianCalendar();
        Date currentDate = new Date();
        gc.setTime(currentDate);
        gc.add(Calendar.DAY_OF_YEAR, -days);
        Date resultDate = gc.getTime();
        return new SimpleDateFormat(pattern).format(resultDate);
    }

    /**
     * Gets some future date from predefined date
     * @param date - start date (predefined)
     * @param days - days past
     * @return - future date.
     */
    public static String getDaysNextDate(String date, Integer days) {
        Date definedDate = null;
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        GregorianCalendar gc = new GregorianCalendar();
        try {
            definedDate = new SimpleDateFormat(BASE_FORMAT).parse(date);
        } catch (ParseException e) {
            Logger.getInstance().info(e);
        }
        gc.setTime(definedDate);
        gc.add(Calendar.DAY_OF_YEAR, days);
        Date resultDate = gc.getTime();
        return new SimpleDateFormat(BASE_FORMAT).format(resultDate);
    }

    public static String getDaysNextDate(String date, Integer days, String format) {
        Date definedDate = null;
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        GregorianCalendar gc = new GregorianCalendar();
        try {
            definedDate = new SimpleDateFormat(BASE_FORMAT).parse(date);
        } catch (ParseException e) {
            Logger.getInstance().info(e);
        }
        gc.setTime(definedDate);
        gc.add(Calendar.DAY_OF_YEAR, days);
        Date resultDate = gc.getTime();
        return new SimpleDateFormat(format).format(resultDate);
    }


    /**
     * Get days ago and years ago current date
     * @param days - days ago date
     * @param years - years ago date
     * @return new date in the past
     */
    public static String getDaysAndYearsAgoDate(int days, int years) {
        // This is workaround for timezone problem
        // Setting it to Central Standard Time (CST) until JVM is fixed
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        GregorianCalendar gc = new GregorianCalendar();
        Date currentDate = new Date();
        gc.setTime(currentDate);
        gc.add(Calendar.YEAR, -years);
        gc.add(Calendar.DAY_OF_YEAR, -days);
        Date resultDate = gc.getTime();
        return new SimpleDateFormat(BASE_FORMAT).format(resultDate);
    }

    /**
     * Gets current date in specified format
     * @param pattern - format date
     * @return - current date in specified format.
     */
    public static String getCurrentDate(String pattern) {
        // This is workaround for timezone problem
        // Setting it to Central Standard Time (CST) until JVM is fixed
        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * Gets current time in milis
     * @return time in mls
     */
    public static String getTimestamp() {
        return getCurrentDate("yyyyMMddHHmmss");
    }

    /**
     * Parses date into specified format
     * @param oldDate - old date
     * @param from - date from
     * @param to - date to
     * @return new format
     */
    public static String changeDateFormat(String oldDate, String from, String to) {
        Date date = null;
        SimpleDateFormat parser = new SimpleDateFormat(from);
        try {
            date = parser.parse(oldDate);
        } catch (ParseException e) {
            Logger.getInstance().info(e.getMessage());
        }
        if (date == null) {
            return oldDate;
        }
        return new SimpleDateFormat(to).format(date);
    }

    //==============================================================================================
    // Math functions

    /**
     * Pow
     * @param base - base
     * @param exponent - exponent
     * @return new integer in pow
     */
    public static int pow(int base, int exponent) {
        return (int) Math.pow(base, exponent);
    }

    //==============================================================================================
    // Screenshot function

    /**
     * Gets screenshot
     * @param fileName name of the screen
     * @return path to screenshot
     */
    public static File captureScreenshot(String fileName) {
        File screen = null;
        try {
            WebDriver driver = Browser.getInstance().getDriver();
            driver = new Augmenter().augment(driver);
            screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screen, new File(String.format(
                    PATHTEMPLATE, fileName)));

        } catch (Exception e) {
            Logger.getInstance().info("Screenshot is not saved.");
            BaseEntity.getLogger().info(e);
        }

        return screen;
    }

    //==============================================================================================
    // WINGS Functions

    /**
     * Gets random value of ssn
     * @return ssn
     */
    public static String getRandomSSN() {
        String pattern = "^(?!000)(?!666)(?!9)\\d{3}[- ]?(?!00)\\d{2}[- ]?(?!0000)\\d{4}$";
        String ssn = CommonFunctions.getRandomIntegerNumber(SSN_DIGITS);

        while (!CommonFunctions.regexIsMatch(ssn, pattern)) {
            ssn = CommonFunctions.getRandomIntegerNumber(SSN_DIGITS);
        }
        return ssn;
    }


    /**
     * Gets number of wage weeks
     * @param startDate - date start
     * @return numbers of wage weeks
     */
    public static String getNumberOfWageWeeks(String startDate) {
        Date initialDate = parseInitialDate(startDate);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(initialDate);
        initialDate = gc.getTime();
        while (gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY) {
            gc.add(Calendar.DAY_OF_YEAR, 1);
            initialDate = gc.getTime();
        }

        Date currentDate = new Date();
        int count = 0;
        while (initialDate.compareTo(currentDate) <= 0) {
            count++;

            gc.add(Calendar.DAY_OF_YEAR, DAYS_IN_WEEK);
            initialDate = gc.getTime();
        }

        return String.valueOf(count);
    }

    /**
     * Gets first wage week
     * @param startDate - date start
     * @return first wage week
     */
    public static String getFirstWageWeek(String startDate) {
        Date initialDate = parseInitialDate(startDate);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(initialDate);
        while (gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY) {
            gc.add(Calendar.DAY_OF_YEAR, 1);
            initialDate = gc.getTime();
        }

        return new SimpleDateFormat(BASE_FORMAT).format(initialDate);
    }

    /**
     * Gets next wage week
     * @param startDate - start date
     * @return next wage week
     */
    public static String getNextWageWeek(String startDate) {
        Date initialDate = parseInitialDate(startDate);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(initialDate);
        gc.add(Calendar.DAY_OF_YEAR, DAYS_IN_WEEK);
        initialDate = gc.getTime();

        return new SimpleDateFormat(BASE_FORMAT).format(initialDate);
    }

    /**
     * Parses initial date
     * @param startDate - date to parse
     * @return - parsed date.
     */
    private static Date parseInitialDate(String startDate) {
        Date initialDate = null;
        SimpleDateFormat parser = new SimpleDateFormat(BASE_FORMAT);
        try {
            initialDate = parser.parse(startDate);
        } catch (ParseException e) {
            Logger.getInstance().error(startDate + " date parse failed");
            Logger.getInstance().error(e.getMessage());
        }
        return initialDate;
    }

    /**
     * This method is used for generating number in chose limit.
     * @param minValue - minValue
     * @param maxValue - maxValue
     * @param length - length of number
     * @return number
     */
    public static Integer getRandomIntegerInLimits(Integer minValue, Integer maxValue, Integer length) {
        Integer number;
        do {
                number = Integer.valueOf(CommonFunctions.getRandomIntegerNumber(length));
        } while (number < minValue || number > maxValue);

        return number;
    }
}
