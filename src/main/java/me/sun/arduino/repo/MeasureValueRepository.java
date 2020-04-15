package me.sun.arduino.repo;

import me.sun.arduino.domain.MeasureValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeasureValueRepository extends JpaRepository<MeasureValue, Long> {

    @Query(value = "select m from MeasureValue m join fetch m.user u where u.id = :id",
            countQuery = "select count(m) from MeasureValue m where m.user.id = :id")
    Page<MeasureValue> findMeasureValueWithUserByUsername(@Param("id") Long userId, Pageable pageable);
}
