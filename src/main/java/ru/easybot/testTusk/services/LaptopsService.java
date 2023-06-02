package ru.easybot.testTusk.services;

import ru.easybot.testTusk.models.entities.Laptop;

import java.util.List;

public interface LaptopsService extends ProductsService{
    Laptop createLaptop(Laptop laptop);
    Laptop updateLaptop(Laptop updatedLaptop);
    List<Laptop> getAllLaptops();
    Laptop findLaptopById(Long id);
}
