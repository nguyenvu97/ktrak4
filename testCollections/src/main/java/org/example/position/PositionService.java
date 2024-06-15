package org.example.position;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    String add(List<Position> positionList);

    Optional<Position> findById(Integer id);
}
