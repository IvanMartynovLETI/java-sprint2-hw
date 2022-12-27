import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Поехали!
        Scanner scanner = new Scanner(System.in);
        ReportManager reportManager = new ReportManager();
        String pathToMonthlyReports;
        String pathToYearReport = "";
        int command;

        while (true) {
            printMenu();
            command = scanner.nextInt();

            if (command == 1) {
                System.out.println("\nУкажите путь (полный или относительный) к одному из файлов месячных отчетов:");
                pathToMonthlyReports = scanner.next();
                reportManager.readAllMonthsReports(pathToMonthlyReports);
            } else if (command == 2) {
                System.out.println("\nУкажите путь (полный или относительный) к файлу годового отчета:");
                pathToYearReport = scanner.next();
                reportManager.readYearReport(pathToYearReport);
            } else if (command == 3) {
                if (reportManager.dataOfAllMonthsReports.isEmpty()) {
                    System.out.println("\nФайлы месячных отчетов еще не считаны, сверка отчетов невозможна.");
                    System.out.println("Считайте файлы месячных отчетов.");
                } else if (reportManager.dataOfYearReport.isEmpty()) {
                    System.out.println("\nФайл годового отчета еще не считан, сверка отчетов невозможна.");
                    System.out.println("Считайте файл годового отчета.");
                } else {
                    boolean isChecked = reportManager.reportCheck(reportManager.dataOfAllMonthsReports, reportManager.dataOfYearReport);
                    if (isChecked)
                        System.out.println("\nСверка годового и месячных отчетов прошла успешно.");

                }
            } else if (command == 4) {
                if (reportManager.dataOfAllMonthsReports.isEmpty()) {
                    System.out.println("\nФайлы месячных отчетов еще не считаны, вывод информации о всех месячных отчетах невозможен.");
                    System.out.println("Считайте файлы месячных отчетов.");
                } else {
                    reportManager.printAllMonthlyReportsInfo(reportManager.dataOfAllMonthsReports);
                }

            } else if (command == 5) {
                if (reportManager.dataOfYearReport.isEmpty()) {
                    System.out.println("\nФайл годового отчета еще не считан, вывод информации о годовом отчете невозможен.");
                    System.out.println("Считайте файл годового отчета.");
                } else {
                    reportManager.printYearReport(pathToYearReport, reportManager.dataOfYearReport);
                }

            } else if (command == 123) {
                System.out.println("\nВыход.");
                break;
            } else {
                System.out.println("\nИзвините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("\nЧто вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчёт.");
        System.out.println("3 - Сверить отчёты.");
        System.out.println("4 - Вывести информацию о всех месячных отчётах.");
        System.out.println("5 - Вывести информацию о годовом отчёте.");
        System.out.println("123 - Выход.\n");
    }
}
