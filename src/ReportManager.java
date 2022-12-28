import java.util.ArrayList;
import java.util.HashMap;

public class ReportManager {
    public HashMap<Integer, ArrayList<MonthDataString>> dataOfAllMonthsReports = new HashMap<>();
    public HashMap<Integer, ArrayList<YearDataString>> dataOfYearReport = new HashMap<>();

    public void readAllMonthsReports(String path) {
        String pathSubString = path.substring(0, path.length() - 5);
        String pathString;
        int monthNumber;

        for (int i = 1; i <= 3; i++) {
            pathString = pathSubString + i + ".csv";
            monthNumber = Integer.parseInt(pathString.substring(pathString.length() - 6, pathString.length() - 4));
            MonthlyReport monthlyReport = new MonthlyReport();
            dataOfAllMonthsReports.put(monthNumber, monthlyReport.loadMonthlyReport(pathString));
        }
    }

    public void readYearReport(String path) {
        YearlyReport yearlyReport = new YearlyReport();
        int year = yearlyReport.getYear(path);
        dataOfYearReport.put(year, yearlyReport.loadYearlyReport(path));
    }

    public void printAllMonthlyReportsInfo(HashMap<Integer, ArrayList<MonthDataString>> dataOfAllMonthsReports) {
        MonthlyReport monthlyReport = new MonthlyReport();

        for (int monthNumber = 1; monthNumber <= 3; monthNumber++) {
            monthlyReport.printMonthlyReportData(monthNumber, dataOfAllMonthsReports.get(monthNumber));
        }
    }

    public void printYearReport(String path, HashMap<Integer, ArrayList<YearDataString>> dataOfYearReport) {
        YearlyReport yearlyReport = new YearlyReport();
        new YearlyReport().printYearReportInfo(path, dataOfYearReport.get(yearlyReport.getYear(path)));
    }

    public boolean checkIncomeInMonthAndYearReports(int monthNumber, int year, HashMap<Integer, ArrayList<MonthDataString>> dataOfAllMonthsReports, HashMap<Integer, ArrayList<YearDataString>> dataOfYearReport) {
        boolean isCheckedOk = true;
        int totalIncomePerMonthFromYearReport;
        int totalIncomeFromMonthReport;
        MonthlyReport monthlyReport = new MonthlyReport();
        totalIncomePerMonthFromYearReport = new YearlyReport().getIncomePerMonthFromYearReport(monthNumber, dataOfYearReport.get(year));
        totalIncomeFromMonthReport = monthlyReport.getTotalIncomePerMonth(dataOfAllMonthsReports.get(monthNumber));

        if (totalIncomePerMonthFromYearReport != totalIncomeFromMonthReport) {
            System.out.println("\nОбнаружена ошибка за " + monthlyReport.months[monthNumber - 1].toLowerCase() + " месяц.");
            System.out.println("Доходы за " + monthlyReport.months[monthNumber - 1].toLowerCase() + " в годовом и месячном отчетах не соответствуют друг другу.");
            isCheckedOk = false;
        }
        return isCheckedOk;
    }

    public boolean checkExpenseInMonthAndYearReports(int monthNumber, int year, HashMap<Integer, ArrayList<MonthDataString>> dataOfAllMonthsReports, HashMap<Integer, ArrayList<YearDataString>> dataOfYearReport) {
        boolean isCheckedOk = true;
        int totalExpensePerMonthFromYearReport;
        int totalExpenseFromMonthReport;
        MonthlyReport monthlyReport = new MonthlyReport();
        totalExpensePerMonthFromYearReport = new YearlyReport().getExpensePerMonthFromYearReport(monthNumber, dataOfYearReport.get(year));
        totalExpenseFromMonthReport = monthlyReport.getTotalExpensePerMonth(dataOfAllMonthsReports.get(monthNumber));

        if (totalExpensePerMonthFromYearReport != totalExpenseFromMonthReport) {
            System.out.println("\nОбнаружена ошибка за " + monthlyReport.months[monthNumber - 1].toLowerCase() + " месяц.");
            System.out.println("Расходы за " + monthlyReport.months[monthNumber - 1].toLowerCase() + " в годовом и месячном отчетах не соответствуют друг другу.");
            isCheckedOk = false;
        }
        return isCheckedOk;
    }

    public boolean reportCheck(HashMap<Integer, ArrayList<MonthDataString>> dataOfAllMonthsReports, HashMap<Integer, ArrayList<YearDataString>> dataOfYearReport) {
        int year = 2021;
        boolean isCheckedOkTotal = true;
        boolean isCheckedOkPerMonth;
        ArrayList<YearDataString> yearReportToBeAnalyzed = dataOfYearReport.get(year);
        int monthCount = yearReportToBeAnalyzed.size() / 2;

        for (int monthNumber = 1; monthNumber <= monthCount; monthNumber++) {
            isCheckedOkPerMonth = checkIncomeInMonthAndYearReports(monthNumber, year, dataOfAllMonthsReports, dataOfYearReport) &&
                    checkExpenseInMonthAndYearReports(monthNumber, year, dataOfAllMonthsReports, dataOfYearReport);
            isCheckedOkTotal &= isCheckedOkPerMonth;
        }
        return isCheckedOkTotal;
    }
}

