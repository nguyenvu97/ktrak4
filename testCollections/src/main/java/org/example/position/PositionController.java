package org.example.position;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manager")
@RequiredArgsConstructor
public class PositionController {
    public final PositionService positionService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody List<Position> positions){
        return ResponseEntity.ok().body(positionService.add(positions));
    }
    @GetMapping
    public ResponseEntity<?>findById(@RequestParam Integer id){
        return ResponseEntity.ok().body(positionService.findById(id));
    }
}
