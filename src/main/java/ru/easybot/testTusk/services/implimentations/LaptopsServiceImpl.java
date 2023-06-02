package ru.easybot.testTusk.services.implimentations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.easybot.testTusk.models.entities.Laptop;
import ru.easybot.testTusk.repositories.LaptopsRepository;
import ru.easybot.testTusk.services.LaptopsService;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotFoundException;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotUpdatedException;

import java.util.List;

@Service
public class LaptopsServiceImpl implements LaptopsService {
    private final LaptopsRepository laptopsRepository;

    @Autowired
    public LaptopsServiceImpl(LaptopsRepository laptopsRepository) {
        this.laptopsRepository = laptopsRepository;
    }

    @Override
    @Transactional
    public Laptop createLaptop(Laptop laptop) {
        laptopsRepository.save(laptop);
        return laptop;
    }

    @Override
    @Transactional
    public Laptop updateLaptop(Laptop updatedLaptop) {
        if (updatedLaptop.getId() == null) {
            throw new LaptopNotUpdatedException("Laptop id should not be null");
        }
        Laptop laptop = findLaptopById(updatedLaptop.getId());
        laptop = (Laptop) prepareProductForUpdate(laptop, updatedLaptop);
        if (updatedLaptop.getLaptopSize() != null) {
            laptop.setLaptopSize(updatedLaptop.getLaptopSize());
        }
        laptopsRepository.save(laptop);
        return laptop;
    }

    @Override
    public List<Laptop> getAllLaptops() {
        return laptopsRepository.findAll();
    }

    @Override
    public Laptop findLaptopById(Long id) {
        return laptopsRepository.findById(id).orElseThrow(() -> new LaptopNotFoundException("Laptop with this id not represented in database yet"));
    }
}
