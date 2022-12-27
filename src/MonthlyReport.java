import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MonthlyReport {
    public ArrayList<MonthDataString> monthDataList = new ArrayList<>();
    public String[] months = {"Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"};

    public ArrayList<MonthDataString> loadMonthlyReport(String path) {

        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");

            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int sumOfOne = Integer.parseInt(parts[3]);

            MonthDataString monthDataString = new MonthDataString(itemName, isExpense, quantity, sumOfOne);
            monthDataList.add(monthDataString);
        }
        return monthDataList;
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчетом. Возможно, файл не найден.\n");
            return null;
        }
    }

    public ArrayList<String> getMaxIncomeInfoPerMonth(ArrayList<MonthDataString> monthDataList) {
        int maxIncome = 0;
        String maxIncomeItem = "";
        ArrayList<String> profitInfo = new ArrayList<>();

        for (MonthDataString monthDataString : monthDataList) {
            if (!monthDataString.isExpense) {
                if (monthDataString.quantity * monthDataString.sumOfOne > maxIncome) {
                    maxIncome = monthDataString.quantity * monthDataString.sumOfOne;
                    maxIncomeItem = monthDataString.itemName;
                }
            }
        }
        profitInfo.add(maxIncomeItem);
        profitInfo.add(Integer.toString(maxIncome));

        return profitInfo;
    }

    public ArrayList<String> getMaxExpenseInfoPerMonth(ArrayList<MonthDataString> monthDataList) {
        int maxExpense = 0;
        String maxExpenseItem = "";
        ArrayList<String> expenseInfo = new ArrayList<>();

        for (MonthDataString monthDataString : monthDataList) {
            if (monthDataString.isExpense) {
                if (monthDataString.quantity * monthDataString.sumOfOne > maxExpense) {
                    maxExpense = monthDataString.quantity * monthDataString.sumOfOne;
                    maxExpenseItem = monthDataString.itemName;
                }
            }
        }
        expenseInfo.add(maxExpenseItem);
        expenseInfo.add(Integer.toString(maxExpense));
        return expenseInfo;
    }

    public void printMonthlyReportData(int monthNumber, ArrayList<MonthDataString> monthDataList) {
        String monthName = months[monthNumber - 1];
        String maxIncome = getMaxIncomeInfoPerMonth(monthDataList).get(1);
        String maxExpense = getMaxExpenseInfoPerMonth(monthDataList).get(1);
        String maxIncomeItem = getMaxIncomeInfoPerMonth(monthDataList).get(0);
        String maxExpenseItem = getMaxExpenseInfoPerMonth(monthDataList).get(0);

        System.out.println("\n" + monthName);
        System.out.println("Самый прибыльный товар-" + maxIncomeItem + ", сумма-" + maxIncome);
        System.out.println("Самый затратный товар-" + maxExpenseItem + ", сумма-" + maxExpense);
    }

    public int getTotalIncomePerMonth(ArrayList<MonthDataString> monthDataList) {
        int totalIncome = 0;

        for (MonthDataString monthDataString : monthDataList)
            if (!monthDataString.isExpense)
                totalIncome += monthDataString.quantity * monthDataString.sumOfOne;

        return totalIncome;
    }

    public int getTotalExpensePerMonth(ArrayList<MonthDataString> monthDataList) {
        int totalExpense = 0;

        for (MonthDataString monthDataString : monthDataList)
            if (monthDataString.isExpense)
                totalExpense += monthDataString.quantity * monthDataString.sumOfOne;

        return totalExpense;
    }
}



