package ru.easybot.testTusk.services.implimentations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.easybot.testTusk.models.entities.Display;
import ru.easybot.testTusk.repositories.DisplaysRepository;
import ru.easybot.testTusk.services.DisplaysService;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotFoundException;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotUpdatedException;

import java.util.List;

@Service
public class DisplaysServiceImpl implements DisplaysService {
    private final DisplaysRepository displaysRepository;

    @Autowired
    public DisplaysServiceImpl(DisplaysRepository displaysRepository) {
        this.displaysRepository = displaysRepository;
    }

    @Override
    @Transactional
    public Display createDisplay(Display display) {
        displaysRepository.save(display);
        return display;
    }

    @Override
    @Transactional
    public Display updateDisplay(Display updatedDisplay) {
        if (updatedDisplay.getId() == null) {
            throw new DisplayNotUpdatedException("Display id should not be null");
        }
        Display display = findDisplayById(updatedDisplay.getId());
        display = (Display) prepareProductForUpdate(display, updatedDisplay);
        if (updatedDisplay.getDiagonal() != null)
            display.setDiagonal(updatedDisplay.getDiagonal());
        displaysRepository.save(display);
        return display;
    }

    @Override
    public List<Display> getAllDisplays() {
        return displaysRepository.findAll();
    }

    @Override
    public Display findDisplayById(Long id) {
        return displaysRepository.findById(id).orElseThrow(() -> new DisplayNotFoundException("Display with this id not represented in database yet"));
    }
}
