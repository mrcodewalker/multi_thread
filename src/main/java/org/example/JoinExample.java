package org.example;

public class JoinExample {
    public static void main(String[] args) {
        System.out.println("=== VÍ DỤ KHÔNG CÓ JOIN() ===");
        withoutJoin();
        
        try {
            Thread.sleep(2000); // Đợi một chút
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== VÍ DỤ CÓ JOIN() ===");
        withJoin();
    }
    
    // Không có join() - Main thread kết thúc trước
    public static void withoutJoin() {
        Thread worker = new Thread(() -> {
            for(int i = 1; i <= 5; i++) {
                System.out.println("Worker đang làm việc: " + i);
                try {
                    Thread.sleep(500); // Giả lập công việc
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Worker hoàn thành!");
        });
        
        worker.start();
        System.out.println("Main thread kết thúc (KHÔNG đợi worker)");
        // Main thread kết thúc ngay, worker có thể chưa xong
    }
    
    // Có join() - Main thread đợi worker xong
    public static void withJoin() {
        Thread worker = new Thread(() -> {
            for(int i = 1; i <= 5; i++) {
                System.out.println("Worker đang làm việc: " + i);
                try {
                    Thread.sleep(500); // Giả lập công việc
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Worker hoàn thành!");
        });
        
        worker.start();
        try {
            worker.join(); // Main thread ĐỢI worker xong
            System.out.println("Main thread kết thúc (SAU KHI worker xong)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

