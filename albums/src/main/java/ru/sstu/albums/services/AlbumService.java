package ru.sstu.albums.services;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.sstu.albums.models.Album;
import ru.sstu.albums.models.Photo;
import ru.sstu.albums.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=";

    private Photo toPhotoEntity(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setName(file.getName());
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setBytes(file.getBytes());
        return photo;
    }

    public Album create(String name, /*List<*/MultipartFile[]/*> */files, String login) throws IOException {
        Album album = new Album();
        album.setName(name);
        album.setUserLogin(login);
        Album createdAlbum = albumRepository.save(album);
        //createPhotos(files, createdAlbum.getId(), createdAlbum.getUserLogin());
        restTemplate.postForObject(url + "true", "Пользователь " + login + " добавил альбом: " + createdAlbum, String.class);
        return createdAlbum;
    }

    public List<Album> showAll(String login) {
        List<Album> albums = albumRepository.findAllByUserLogin(login);
        restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к списку альбомов: " + albums, String.class);
        return albums;
    }

    public Album show(int id, String login) {
        Album album = albumRepository.findById(id);
        if (album != null) {
            restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к альбому: " + album, String.class);
            album.setPhotos(photoRepository.findAllByAlbumId(id));
        }
        return album;
    }

    public int delete(int id, String login) {
        List<Photo> photos = photoRepository.findAllByAlbumId(id);
        for (Photo photo : photos) {
            int photoId = photo.getId();
            photo.setTags(photoTagRepository.findAllByPhotoId(photoId));
            photo.setRatings(photoRatingRepository.findAllByPhotoId(photoId));
            photo.setComments(photoCommentRepository.findAllByPhotoId(photoId));
        }
        Album deletedAlbum = albumRepository.deleteById(id);
        deletedAlbum.setPhotos(photos);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил альбом: " + deletedAlbum, String.class);
        return deletedAlbum.getId();
    }

    public List<Photo> createPhotos(List<MultipartFile> files, int albumId, String login) throws IOException {
        if (Objects.requireNonNull(files.getFirst().getOriginalFilename()).isEmpty())
            return null;
        List<Photo> photos = new ArrayList<>();
        List<Photo> createdPhotos = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            photos.add(toPhotoEntity(files.get(i)));
            photos.get(i).setAlbumId(albumId);
            Photo createdPhoto = photoRepository.save(photos.get(i));
            createdPhoto.setAlbum(albumRepository.findById(createdPhoto.getAlbumId()));
            createdPhotos.add(createdPhoto);
        }
        restTemplate.postForObject(url + "true", "Пользователь " + login + " добавил фото: " + createdPhotos, String.class);
        return createdPhotos;
    }

    public List<Album> find(String keyword, String login) {
        List<Album> albums = albumRepository.findAllLikeName(keyword.trim());
        restTemplate.postForObject(url + "false", "Пользователь " + login + " ввел ключевые слова" + keyword + " и выполнил поиск альбомов: " + albums, String.class);
        return albums;
    }

    public List<Album> getAll(String login) {
        List<Album> albums = albumRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех альбомов: " + albums, String.class);
        return albums;
    }

}
