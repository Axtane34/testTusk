package ru.easybot.testTusk.services;

import ru.easybot.testTusk.models.entities.PC;

import java.util.List;

public interface PCsService extends ProductsService{
    PC createPC(PC pc);
    PC updatePC(PC updatedPC);
    List<PC> getAllPCs();
    PC findPCById(Long id);
}
