package org.example.position.impl;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Impl.JpaExecutor;
import org.example.position.Position;
import org.example.position.PositionAccessService;
import org.example.position.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

    public final JpaExecutor<Position> positionAccessService;

    @Override
    public String add(List<Position> position) {
        for (Position position1 : position) {
            positionAccessService.add(position1);

        }
        return "Position added successfully";
    }

    @Override
    public Optional<Position> findById(Integer id) {
        if (id <0){
            return Optional.empty();
        }
        return positionAccessService.findById(id);
    }
}
