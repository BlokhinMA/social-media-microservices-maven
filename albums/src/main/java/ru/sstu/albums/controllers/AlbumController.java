package ru.sstu.albums.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.albums.services.AlbumService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping()
    public ResponseEntity<?> showAll(@RequestParam String login) {
        return ResponseEntity.ok(albumService.showAll(login));
    }

    @PostMapping("/add_album")
    public ResponseEntity<?> create(@RequestParam String name, @RequestBody /*List<*/MultipartFile[]/*> */files, @RequestParam String login) throws IOException {
        return ResponseEntity.ok(albumService.create(name, files, login));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable int id, @RequestParam String login) {
        return ResponseEntity.ok(albumService.show(id, login));
    }

    @PostMapping("/create_photos")
    public ResponseEntity<?> createPhotos(@RequestBody List<MultipartFile> files, @RequestParam int albumId, @RequestParam String login) throws IOException {
        return ResponseEntity.ok(albumService.createPhotos(files, albumId, login));
    }

    @DeleteMapping("/delete_album")
    public ResponseEntity<?> deleteAlbum(@RequestParam int id, @RequestParam String login) {
        return ResponseEntity.ok(albumService.delete(id, login));
    }

    @GetMapping("/find_albums")
    public ResponseEntity<?> findAlbums(@RequestParam String keyword, @RequestParam String login) {
        return ResponseEntity.ok(albumService.find(keyword, login));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam String login) {
        return ResponseEntity.ok(albumService.getAll(login));
    }

}
