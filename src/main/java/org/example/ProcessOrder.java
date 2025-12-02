package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProcessOrder {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 10));
        orders.add(new Order(2, 20));
        orders.add(new Order(3, 30));
        orders.add(new Order(4, 40));
        orders.add(new Order(5, 50));
        orders.add(new Order(6, 60));
        orders.add(new Order(7, 70));
        orders.add(new Order(8, 80));
        orders.add(new Order(9, 90));
        int numberOfOrders = orders.size(); // 9
        int choiceData = 0;
        System.out.println("Ban muon chon option nao?");
        System.out.println("1. Nhap du lieu tu ban phim");
        System.out.println("2. Su dung mock data");
        choiceData = scanner.nextInt();
        if (choiceData == 1) {
            orders.clear();
            System.out.print("Nhap so don hang muon tao: ");
            numberOfOrders = scanner.nextInt();
            for (int i = 0; i < numberOfOrders; i++) {
                System.out.print("Nhap order id: ");
                int orderId = scanner.nextInt();
                System.out.print("Nhap thoi gian xu ly: ");
                int processingTime = scanner.nextInt();
                orders.add(new Order(orderId, processingTime));
            }
        } else if (choiceData == 2) {
            System.out.println("Mock data da duoc khoi tao: " + orders.size());
        }
        System.out.println("Tong so don hang: " + numberOfOrders);
        System.out.print("Nhap so nhan vien ( Thread ): ");
        int numberOfEmployees = scanner.nextInt();
        System.out.println("Ban muon chon option nao?");
        System.out.println(String.format("1. Chia deu tat ca goi hang cho %d nhan vien va bat dau xu ly", numberOfEmployees));
        System.out.println(String.format("2. Chia random goi hang cho %d nhan vien, thuc hien sort theo size goi hang tu it nhat xu ly truoc...", numberOfEmployees));
        int choice = scanner.nextInt();
        if (choice == 1) {

            List<Employee> employees = new ArrayList<>();
            int ordersPerEmployee = numberOfOrders / numberOfEmployees; // 9 / 5 = 1
            int remainder = numberOfOrders % numberOfEmployees; // 9 % 5 = 4
            int orderIndex = 0;
            for (int i = 0; i < numberOfEmployees; i++) {
                ArrayList<Order> employeeOrders = new ArrayList<>();
                int ordersForThisEmployee = ordersPerEmployee;
                if (i < remainder) {
                    ordersForThisEmployee++;
                }

                for (int j = 0; j < ordersForThisEmployee; j++) { // 2 2 2 2 1
                    employeeOrders.add(orders.get(orderIndex)); // 1 2 3 4 5 6 7 8 9
                    orderIndex++;
                }

                Employee employee = new Employee(employeeOrders);
                employees.add(employee);
            }
            System.out.println("===========================================================");
            for (int i = 0; i < employees.size(); i++) {
                System.out.println(String.format("Nhan vien %d co %d goi hang co cac id la: %s",
                 i, employees.get(i).getOrders().size(), 
                 employees.get(i).getOrders().stream().map(Order::getOrderId).collect(Collectors.toList())));
            }
            // Về việc xử lý thread không có join thì nó sẽ không await thread trước hoàn thành, nên thread sẽ chạy song song
            // Có thể 1 xong trước 7 xong trước,... tức là không theo trật tự hoặc quy luật nào cả
            // Tất cả worker đều được chạy song song
            int index = 0;
            for (Employee employee : employees) {
                System.out.println("Nhan vien thu " + (index++) + " da bat dau xu ly");
                System.out.println("===========================================================");
                employee.start();
                // Còn nếu muốn chạy theo đúng thứ tự và thread sau hoạt động khi cần async await thread trước thì dùng join()

//                try {
//                    employee.join();
//                } catch (InterruptedException e) {
//                    System.out.println("Loi khi waiting for employee: " + e.getMessage());
//                }
            }
            System.out.println("Tat ca nhan vien da xu ly xong");
           
        } else if (choice == 2) {
            Map<Integer, List<Order>> afterDivided = new HashMap<>(); // random index cua nhan vien, cache vao map
            // init empty list orders cho tung nhan vien
            for (int i = 0; i < numberOfEmployees; i++) {
                afterDivided.put(i, new ArrayList<>());
            }
            
            Random random = new Random();
            // Random tu 0 -> n-1 index de them order[i] hien tai vao
            for (int i = 0; i < numberOfOrders; i++) {
                Integer randomEmployeeIndex = random.nextInt(numberOfEmployees);
                Order order = orders.get(i);
                afterDivided.get(randomEmployeeIndex).add(order);
            }
            
            List<Employee> employees = new ArrayList<>();
            // mapping tu Map sang List nhan vien
            for (int i = 0; i < numberOfEmployees; i++) {
                List<Order> employeeOrders = afterDivided.get(i);
                Employee employee = new Employee(employeeOrders);
                employees.add(employee);
            }
            // Sort by list orders size tu be toi lon
            employees.sort(Comparator.comparingInt(emp -> emp.getOrders().size()));
            System.out.println("===========================================================");
            System.out.println("Danh sach nhan vien sau khi sort (tu it den nhieu don hang):");
            for (int i = 0; i < employees.size(); i++) {
                System.out.println(String.format("Nhan vien thu %d (xu ly thu %d) co %d goi hang co cac id la: %s", 
                    i, i + 1, employees.get(i).getOrders().size(),
                        employees.get(i).getOrders().stream().map(Order::getOrderId).collect(Collectors.toList())));
            }
            System.out.println("===========================================================");
            System.out.println("Bat dau xu ly tu nhan vien co it don hang nhat:");
            int index = 0;
            for (Employee employee : employees) {
                System.out.println("Nhan vien thu " + (index++) + " da bat dau xu ly");
                System.out.println("===========================================================");
                employee.start();
                try {
                    employee.join(); 
                } catch (InterruptedException e) {
                    System.out.println("Loi khi waiting for employee: " + e.getMessage());
                }
            }
            System.out.println("Tat ca nhan vien da xu ly xong");
            scanner.close();
        }
    }
}
