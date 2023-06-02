package ru.easybot.testTusk.services.implimentations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.easybot.testTusk.models.entities.PC;
import ru.easybot.testTusk.repositories.PCsRepository;
import ru.easybot.testTusk.services.PCsService;
import ru.easybot.testTusk.util.exceptions.PC.PCNotFoundException;
import ru.easybot.testTusk.util.exceptions.PC.PCNotUpdatedException;

import java.util.List;

@Service
public class PCsServiceImpl implements PCsService {
    private final PCsRepository personalComputerRepository;

    @Autowired
    public PCsServiceImpl(PCsRepository personalComputerRepository) {
        this.personalComputerRepository = personalComputerRepository;
    }

    @Override
    @Transactional
    public PC createPC(PC pc) {
        personalComputerRepository.save(pc);
        return pc;
    }

    @Override
    @Transactional
    public PC updatePC(PC updatedPC) {
        if (updatedPC.getId() == null) {
            throw new PCNotUpdatedException("PC id should not be null");
        }
        PC pc = findPCById(updatedPC.getId());
        pc = (PC) prepareProductForUpdate(pc, updatedPC);
        if (updatedPC.getFormFactor() != null)
            pc.setFormFactor(updatedPC.getFormFactor());
        personalComputerRepository.save(pc);
        return pc;
    }

    @Override
    public List<PC> getAllPCs() {
        return personalComputerRepository.findAll();
    }

    @Override
    public PC findPCById(Long id) {
        return personalComputerRepository.findById(id).orElseThrow(() -> new PCNotFoundException("PC with this id not represented in database yet"));
    }
}
