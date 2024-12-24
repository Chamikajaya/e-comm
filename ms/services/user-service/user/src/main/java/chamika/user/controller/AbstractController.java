package chamika.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// *  Generic class for handling CRUD operations
public abstract class AbstractController<T, ID, CreateDTO, UpdateDTO, ResponseDTO> {

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody CreateDTO createDTO) {
        return ResponseEntity.ok(createEntity(createDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> get(@PathVariable ID id) {
        return ResponseEntity.ok(getEntity(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable ID id, @RequestBody UpdateDTO updateDTO) {
        return ResponseEntity.ok(updateEntity(id, updateDTO));
    }

    // *  Abstract methods to be implemented by the concrete class - UserController
    protected abstract ResponseDTO createEntity(CreateDTO createDTO);

    protected abstract ResponseDTO getEntity(ID id);

    protected abstract ResponseDTO updateEntity(ID id, UpdateDTO updateDTO);
}