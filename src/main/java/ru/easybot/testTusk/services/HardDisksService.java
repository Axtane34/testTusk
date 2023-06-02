package ru.easybot.testTusk.services;

import ru.easybot.testTusk.models.entities.HardDisk;

import java.util.List;

public interface HardDisksService extends ProductsService{
    HardDisk createHardDisk(HardDisk hardDisk);
    HardDisk updateHardDisk(HardDisk updatedHardDisk);
    List<HardDisk> getAllHardDisks();
    HardDisk findHardDiskById(Long id);
}
