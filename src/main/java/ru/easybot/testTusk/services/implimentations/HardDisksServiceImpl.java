package ru.easybot.testTusk.services.implimentations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.easybot.testTusk.models.entities.HardDisk;
import ru.easybot.testTusk.repositories.HardDisksRepository;
import ru.easybot.testTusk.services.HardDisksService;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotFoundException;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotUpdatedException;

import java.util.List;

@Service
public class HardDisksServiceImpl implements HardDisksService {
    private final HardDisksRepository hardDisksRepository;

    @Autowired
    public HardDisksServiceImpl(HardDisksRepository hardDisksRepository) {
        this.hardDisksRepository = hardDisksRepository;
    }

    @Override
    @Transactional
    public HardDisk createHardDisk(HardDisk hardDisk) {
        hardDisksRepository.save(hardDisk);
        return hardDisk;
    }

    @Override
    @Transactional
    public HardDisk updateHardDisk(HardDisk updatedHardDisk) {
        if (updatedHardDisk.getId() == null) {
            throw new HardDiskNotUpdatedException("Hard disk id should not be null");
        }
        HardDisk hardDisk = findHardDiskById(updatedHardDisk.getId());
        hardDisk = (HardDisk) prepareProductForUpdate(hardDisk, updatedHardDisk);
        if (updatedHardDisk.getCapacity() != null)
            hardDisk.setCapacity(updatedHardDisk.getCapacity());
        hardDisksRepository.save(hardDisk);
        return hardDisk;
    }

    @Override
    public List<HardDisk> getAllHardDisks() {
        return hardDisksRepository.findAll();
    }

    @Override
    public HardDisk findHardDiskById(Long id) {
        return hardDisksRepository.findById(id).orElseThrow(() -> new HardDiskNotFoundException("Hard disk with this id not represented in database yet"));
    }
}
