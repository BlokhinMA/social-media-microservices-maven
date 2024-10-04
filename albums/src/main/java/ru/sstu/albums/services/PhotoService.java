package ru.sstu.albums.services;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.sstu.albums.models.*;
import ru.sstu.albums.repositories.*;
import org.springframework.stereotype.Service;
import ru.sstu.albums.repositories.PhotoRepository;
import ru.sstu.albums.repositories.PhotoCommentRepository;
import ru.sstu.albums.repositories.PhotoRatingRepository;
import ru.sstu.albums.repositories.PhotoTagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=";

    public Photo showEntity(int id) {
        return photoRepository.findEntityById(id);
    }

    public Photo show(int id, String login) {
        Photo photo = photoRepository.findById(id);
        if (photo != null) {
            photo.setTags(photoTagRepository.findAllByPhotoId(id));
            photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(login, id));
            Double rating = photoRatingRepository.calculateAverageRatingByPhotoId(id);
            if (rating != null)
                photo.setRating((int) Math.round(rating));
            else photo.setRating(-1);
            photo.setComments(photoCommentRepository.findAllByPhotoId(id));
            photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
            restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к фото: " + photo, String.class);
        }
        return photo;
    }

    public int delete(int id, String login) {
        List<PhotoTag> tags = photoTagRepository.findAllByPhotoId(id);
        List<PhotoRating> ratings = photoRatingRepository.findAllByPhotoId(id);
        List<PhotoComment> comments = photoCommentRepository.findAllByPhotoId(id);
        Photo deletedPhoto = photoRepository.deleteById(id);
        deletedPhoto.setTags(tags);
        deletedPhoto.setRatings(ratings);
        deletedPhoto.setComments(comments);
        deletedPhoto.setAlbum(albumRepository.findById(deletedPhoto.getAlbumId()));
        restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил фото: " + deletedPhoto, String.class);
        return deletedPhoto.getId();
    }

    public PhotoTag createTag(PhotoTag photoTag, String login) {
        if (photoTagRepository.findByTagAndPhotoId(photoTag) != null)
            return null;
        PhotoTag createdPhotoTag = photoTagRepository.save(photoTag);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " добавил тег: " + createdPhotoTag, String.class);
        return createdPhotoTag;
    }

    public int deleteTag(int id, String login) {
        PhotoTag deletedPhotoTag = photoTagRepository.deleteById(id);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил тег: " + deletedPhotoTag, String.class);
        return deletedPhotoTag.getId();
    }

    public RatingResponse createRating(PhotoRating photoRating) {
        PhotoRating createdPhotoRating = photoRatingRepository.save(photoRating);
        restTemplate.postForObject(url + "true", "Пользователь " + createdPhotoRating.getRatingUserLogin() + " добавил оценку: " + createdPhotoRating, String.class);
        return new RatingResponse((int) Math.round(photoRatingRepository.calculateAverageRatingByPhotoId(createdPhotoRating.getPhotoId())), createdPhotoRating.getId());
    }

    public int updateRating(PhotoRating photoRating, String login) {
        if (Objects.equals(photoRatingRepository.findById(photoRating.getId()).getRatingUserLogin(), login)) {
            PhotoRating updatedPhotoRating = photoRatingRepository.updateRatingById(photoRating);
            restTemplate.postForObject(url + "true", "Пользователь " + updatedPhotoRating.getRatingUserLogin() + " обновил оценку: " + updatedPhotoRating, String.class);
            return (int) Math.round(photoRatingRepository.calculateAverageRatingByPhotoId(updatedPhotoRating.getPhotoId()));
        }
        return -2;
    }

    public int deleteRating(int id, String login) {
        if (Objects.equals(photoRatingRepository.findById(id).getRatingUserLogin(), login)) {
            PhotoRating deletedPhotoRating = photoRatingRepository.deleteById(id);
            restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил оценку: " + deletedPhotoRating, String.class);
            Double rating = photoRatingRepository.calculateAverageRatingByPhotoId(deletedPhotoRating.getPhotoId());
            if (rating != null)
                return (int) Math.round(rating);
            return -1;
        }
        return -2;
    }

    public PhotoComment createComment(PhotoComment photoComment) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return null;
        PhotoComment createdPhotoComment = photoCommentRepository.save(photoComment);
        restTemplate.postForObject(url + "true", "Пользователь " + createdPhotoComment.getCommentingUserLogin() + " добавил комментарий: " + createdPhotoComment, String.class);
        return createdPhotoComment;
    }

    public int deleteComment(int id, String login) {
        PhotoComment deletedPhotoComment = photoCommentRepository.deleteById(id);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил комментарий: " + deletedPhotoComment, String.class);
        return deletedPhotoComment.getId();
    }

    public List<Photo> find(String searchTerm, String word, String login) {
        if (searchTerm != null && !searchTerm.isEmpty() && word != null && !word.isEmpty())
            switch (searchTerm) {
                case "creationTimeStamp" -> {
                    List<Photo> photos = photoRepository.findAllLikeCreationTimeStamp(word);
                    forLogs(login, photos);
                    return photos;
                }
                case "tags" -> {
                    List<Photo> photos = photoRepository.findAllLikeTag(word);
                    forLogs(login, photos);
                    return photos;
                }
                case "comments" -> {
                    List<Photo> photos = photoRepository.findAllLikeComment(word);
                    forLogs(login, photos);
                    return photos;
                }
            }
        return null;
    }

    private void forLogs(String login, List<Photo> photos) {
        List<Photo> photos1 = new ArrayList<>(photos);
        restTemplate.postForObject(url + "false", "Пользователь " + login + " выполнил поиск фото: " + photos1, String.class);
    }

    public List<Photo> getAll(String login) {
        List<Photo> photos = photoRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех фото: " + photos, String.class);
        return photos;
    }

    public List<PhotoTag> getAllTags(String login) {
        List<PhotoTag> photoTags = photoTagRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех тегов фото: " + photoTags, String.class);
        return photoTags;
    }

    public List<PhotoRating> getAllRatings(String login) {
        List<PhotoRating> photoRatings = photoRatingRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех постов сообществ: " + photoRatings, String.class);
        return photoRatings;
    }

    public List<PhotoComment> getAllComments(String login) {
        List<PhotoComment> photoComments = photoCommentRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех комментариев фото: " + photoComments, String.class);
        return photoComments;
    }

}
