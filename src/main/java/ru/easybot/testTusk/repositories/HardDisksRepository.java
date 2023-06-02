package ru.easybot.testTusk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.easybot.testTusk.models.entities.HardDisk;

@Repository
public interface HardDisksRepository extends JpaRepository<HardDisk, Long> {
}
