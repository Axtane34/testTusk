package ru.easybot.testTusk.services;

import ru.easybot.testTusk.models.entities.Display;

import java.util.List;

public interface DisplaysService extends ProductsService{
    Display createDisplay(Display display);
    Display updateDisplay(Display updatedDisplay);
    List<Display> getAllDisplays();
    Display findDisplayById(Long id);
}
