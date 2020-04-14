package me.sun.arduino;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<ArduinoValue, Long> {

}
