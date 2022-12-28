import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class YearlyReport {
    public ArrayList<YearDataString> yearDataList = new ArrayList<>();

    public ArrayList<YearDataString> loadYearlyReport(String path) {
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");

            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            YearDataString yearDataString = new YearDataString(month, amount, isExpense);
            yearDataList.add(yearDataString);
        }
        return yearDataList;
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчетом. Возможно, файл не найден.\n");
            return null;
        }
    }

    public int getIncomePerMonthFromYearReport(int monthNumber, ArrayList<YearDataString> yearDataList) {
        int incomePerMonth = 0;
        int startAnalyzePositionOfYearReport = 2 * (monthNumber - 1);
        int endAnalyzePositionOfYearReport = 2 * (monthNumber - 1) + 1;

        for (int index = startAnalyzePositionOfYearReport; index <= endAnalyzePositionOfYearReport; index++)
            if (!yearDataList.get(index).isExpense)
                incomePerMonth = yearDataList.get(index).amount;

        return incomePerMonth;
    }

    public int getExpensePerMonthFromYearReport(int monthNumber, ArrayList<YearDataString> yearDataList) {
        int expensePerMonth = 0;
        int startAnalyzePositionOfYearReport = 2 * (monthNumber - 1);
        int endAnalyzePositionOfYearReport = 2 * (monthNumber - 1) + 1;

        for (int index = startAnalyzePositionOfYearReport; index <= endAnalyzePositionOfYearReport; index++)
            if (yearDataList.get(index).isExpense)
                expensePerMonth = yearDataList.get(index).amount;

        return expensePerMonth;
    }

    public int getProfitPerMonthFromYearReport(int monthNumber, ArrayList<YearDataString> yearDataList) {
        int income = getIncomePerMonthFromYearReport(monthNumber, yearDataList);
        int expense = getExpensePerMonthFromYearReport(monthNumber, yearDataList);
        return income - expense;
    }

    public double getAverageIncomePerYearFromYearReport(ArrayList<YearDataString> yearDataList) {
        int numOfMonths = yearDataList.size() / 2;
        int incomePerYear = 0;

        for (YearDataString yearDataString : yearDataList) {
            if (!yearDataString.isExpense)
                incomePerYear += yearDataString.amount;
        }

        return (double) incomePerYear / (double) numOfMonths;
    }

    public double getAverageExpensePerYearFromYearReport(ArrayList<YearDataString> yearDataList) {
        int numOfMonths = yearDataList.size() / 2;
        int expensePerYear = 0;

        for (YearDataString yearDataString : yearDataList) {
            if (yearDataString.isExpense)
                expensePerYear += yearDataString.amount;
        }

        return (double) expensePerYear / (double) numOfMonths;
    }

    public String getMonthName(int monthNumber) {
        String[] months = {"январь",
                "февраль",
                "март",
                "апрель",
                "май",
                "июнь",
                "июль",
                "август",
                "сентябрь",
                "октябрь",
                "ноябрь",
                "декабрь"};
        return months[monthNumber - 1];
    }

    public int getYear(String path) {
        return Integer.parseInt(path.substring(path.length() - 8, path.length() - 4));
    }

    public void printYearReportInfo(String path, ArrayList<YearDataString> yearDataList) {
        System.out.println("\n" + getYear(path));

        for (int i = 1; i <= 3; i++)
            System.out.println("Прибыль за " + getMonthName(i) + " составила " + getProfitPerMonthFromYearReport(i, yearDataList));

        System.out.println("Средний расход за все месяцы в году: " + String.format("%.1f", getAverageExpensePerYearFromYearReport(yearDataList)));
        System.out.println("Средний доход за все месяцы в году: " + String.format("%.1f", getAverageIncomePerYearFromYearReport(yearDataList)));
    }

}
