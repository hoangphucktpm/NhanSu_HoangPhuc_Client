package client;


import dao.CadidateDao;
import dao.PositionDao;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {

    private static final String URL = "rmi://HOANGPHUC:6541/";

    public static void main(String[] args) {
        try {

            CadidateDao candidateDao = (CadidateDao) Naming.lookup(URL + "candidateDao");
            PositionDao positionDao = (PositionDao) Naming.lookup(URL + "positionDao");
            Scanner scanner = new Scanner(System.in);
            int option;

            while (true) {
                System.out.println("1. Tìm ra danh sách ứng viên theo công ty");
                System.out.println("2. Tìm ra danh sách ứng viên có thời gian làm việc lâu nhất");
                System.out.println("3. Tìm ra danh sách vị trí công việc theo tên và mức lương");
                System.out.println("4. Thoát");
                System.out.println("Chọn chức năng: ");
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        candidateDao.listCadidatesByCompanies().forEach((k, v) -> System.out.println(k + " số lượng công ty: " + v));
                        break;
                    case 2:
                        candidateDao.listCadidatesWithLongestWorking().forEach((k, v) -> System.out.println(k + " thời gian làm việc: " + v));
                        break;
                    case 3:
                        System.out.println("Nhập tên vị trí công việc: ");
                        String name = scanner.nextLine();
                        System.out.println("Nhập mức lương từ: ");
                        double salaryFrom = scanner.nextDouble();
                        scanner.nextLine(); // consume leftover newline
                        System.out.println("Nhập mức lương đến: ");
                        double salaryTo = scanner.nextDouble();
                        scanner.nextLine(); // consume leftover newline
                       // Nếu không tìm thấy thì in ra thông báo không tìm thấy
                        if (positionDao.listPosition(name, salaryFrom, salaryTo).isEmpty()) {
                            System.out.println("Không tìm thấy vị trí công việc");
                        }

                        positionDao.listPosition(name, salaryFrom, salaryTo).forEach(x -> System.out.println(x));
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Chức năng không tồn tại");
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


