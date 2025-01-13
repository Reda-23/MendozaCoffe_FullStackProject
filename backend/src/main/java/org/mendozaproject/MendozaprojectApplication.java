package org.mendozaproject;

import org.mendozaproject.models.*;
import org.mendozaproject.enums.Schedule;
import org.mendozaproject.repos.UsersRepository;
import org.mendozaproject.services.*;
import org.mendozaproject.services.impl.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MendozaprojectApplication  {



    public static void main(String[] args) {
        SpringApplication.run(MendozaprojectApplication.class, args);
    }


    @Bean
    @Profile("!test")
   CommandLineRunner commandLineRunner(WaiterService waiterService,
                                       OrderService orderService,
                                       ItemService itemService,
                                       BillService billService,
                                       CustomerService customerService,
                                       UsersService usersService){
        return args -> {
          Waiter driss = waiterService.saveWaiter(new Waiter(null,"Driss","0647382981", Schedule.MORNING));
          Waiter mohssine = waiterService.saveWaiter(new Waiter(null,"Mohssine","0777830283", Schedule.EVENING));

            Customer youssef = customerService.saveCustomer(new Customer(null,"Youssef","youssefben30@gmail.com"));
            Item i1 = itemService.saveItem(new Item(null,"Café Noir",7.0,"apex"));
            Item i2 = itemService.saveItem(new Item(null,"Thé Vert",9.0,"apex"));
            Item i3 = itemService.saveItem(new Item(null,"Jus D'orange",11.0,"apex"));
            Item i4 = itemService.saveItem(new Item(null,"Jus Avocat",25.0,"apex"));
            Item i5 = itemService.saveItem(new Item(null,"Jus Banane",22.0,"apex"));
            Item i6 = itemService.saveItem(new Item(null,"Jus Panache",29.0,"apex"));
            Item i7 = itemService.saveItem(new Item(null,"Lait",8.0,"apex"));
            Item i8 = itemService.saveItem(new Item(null,"Croissant",18.0,"apex"));
            Item i9 = itemService.saveItem(new Item(null,"Croissant",18.0,"apex"));
            Item i10 = itemService.saveItem(new Item(null,"Croissant",18.0,"apex"));
            Item i11= itemService.saveItem(new Item(null,"Croissant",18.0,"apex"));
            Item i12 = itemService.saveItem(new Item(null,"Croissant",18.0,"apex"));
            Item i13 = itemService.saveItem(new Item(null,"Croissant",18.0,"apex"));


            List<Item> itemList = List.of(i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13);

            itemList.stream().map(i -> itemService.saveItem(i));





            usersService.register(new Users(null,"reda","reda@123","ADMIN"));
            usersService.register(new Users(null,"laila","laila@123","USER"));











        };
   }
}
