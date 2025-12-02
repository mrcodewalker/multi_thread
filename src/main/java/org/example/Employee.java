package org.example;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends Thread{
    private List<Order> orders;
    @Override
    public void run(){
        System.out.println("Nhan vien bat dau cong viec: ");
        for(Order order : orders){
            System.out.println("Dang xu ly order co id: "+order.getOrderId());
            try{
                Thread.sleep(order.getProcessingTime());
            } catch (InterruptedException e){
                System.out.println("Loi employee class: "+e.getMessage());
            }
        }
        System.out.println("Da hoan thanh cong viec!");
    }
}
