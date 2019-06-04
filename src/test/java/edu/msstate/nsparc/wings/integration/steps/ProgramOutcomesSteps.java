package edu.msstate.nsparc.wings.integration.steps;

import framework.CommonFunctions;

import java.util.*;

public class ProgramOutcomesSteps extends  BaseWingsSteps {
    /**
     * Inputs start date, end date and a year for program outquarters.
     * @param startDate - start date
     * @param endDate - end date
     * @param year - year
     * @return start and end date with a year.
     */
    private static String[] inputDate(String startDate, String endDate, String year) {
        return new String[] {String.format("%1$s/%2$s", startDate, year), String.format("%1$s/%2$s", endDate, year)};
    }

    /**
     * This method is used for getting Program Outcomes quarters, that will be displayed on Program Outcomes form
     * @return String array with quarters
     * @param daysAgo Days before current date for Exit Date
     */
    public static String[] getProgramOutcomesQuarters(int daysAgo) {
        List<String> dates = new ArrayList<>();
        String[] datesToAdd = new String[2];
        Date exitDate = CommonFunctions.getProgramOutcomesExitDate(daysAgo);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(exitDate);

        int exitMonth = calendar.get(Calendar.MONTH) + 1; // adding 1 because it starts from 0
        int exitYear = calendar.get(Calendar.YEAR);

        // getting exit quarter
        String[] firstQuarter = {"01/01", "03/31"};
        String[] secondQuarter = {"04/01", "06/30"};
        String[] thirdQuarter = {"07/01", "09/30"};
        String[] fourthQuarter = {"10/01", "12/31"};

        int currentQuarter = defineExitQuarter(exitMonth) + 1;

        int currentYear = exitYear;
        // if exit quarter = 4, then we should increase year and reset quarter
        if (currentQuarter > 4) {
            currentQuarter = currentQuarter - 4;
            currentYear = currentYear + 1;
        }
        // creating Program Outcomes quarters
        for (int i = 0; i < 4; i++) {
            switch (currentQuarter) {
                // first quarter
                case 1:
                    datesToAdd = inputDate(firstQuarter[0], firstQuarter[1], String.valueOf(currentYear));
                    currentQuarter++;
                    break;

                // second quarter
                case 2:
                    datesToAdd = inputDate(secondQuarter[0], secondQuarter[1], String.valueOf(currentYear));
                    currentQuarter++;
                    break;

                // third quarter
                case 3:
                    datesToAdd = inputDate(thirdQuarter[0], thirdQuarter[1], String.valueOf(currentYear));
                    currentQuarter++;
                    break;

                // fourth quarter
                case 4:
                    datesToAdd = inputDate(fourthQuarter[0], fourthQuarter[1], String.valueOf(currentYear));
                    currentQuarter++;
                    break;

                // first quarter of the next year
                case 5:
                    currentQuarter = currentQuarter - 4;
                    currentYear++;
                    datesToAdd = inputDate(firstQuarter[0], firstQuarter[1], String.valueOf(currentYear));
                    currentQuarter++;
                    break;
                default:
                    break;
            }
            dates.add(datesToAdd[0]);
            dates.add(datesToAdd[1]);
        }

        return dates.toArray(new String[0]);
    }

    private static int defineExitQuarter(int exitMonth) {
        int exitQuarter;
        if (exitMonth <= 3) {
            exitQuarter = 1;
        } else if (exitMonth > 9) {
            exitQuarter = 4;
        } else if (exitMonth <= 6) {
            exitQuarter = 2;
        } else {
            exitQuarter = 3;
        }
        return exitQuarter;
    }
}
