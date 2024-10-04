package ru.sstu.communities.services;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.sstu.communities.models.Community;
import ru.sstu.communities.models.CommunityMember;
import ru.sstu.communities.models.CommunityPost;
import ru.sstu.communities.repositories.CommunityPostRepository;
import ru.sstu.communities.repositories.CommunityRepository;
import ru.sstu.communities.repositories.CommunityMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=";

    public List<Community> showAllOwn(String login) {
        List<Community> communities = communityRepository.findAllByCreatorLogin(login);
        restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к списку своих сообществ: " + communities, String.class);
        return communities;
    }

    public List<Community> showAll(String memberLogin) {
        List<Community> communities = communityRepository.findAllByMemberLogin(memberLogin);
        restTemplate.postForObject(url + "false", "Пользователь " + memberLogin + " обратился к списку сообществ: " + communities, String.class);
        return communities;
    }

    public Community create(Community community) {
        Community createdCommunity = communityRepository.save(community);
        restTemplate.postForObject(url + "true", "Пользователь " + createdCommunity.getCreatorLogin() + " создал сообщество: " + createdCommunity, String.class);
        return createdCommunity;
    }

    public int delete(int id, String login) {
        List<CommunityMember> members = communityMemberRepository.findAllByCommunityId(id);
        List<CommunityPost> posts = communityPostRepository.findAllByCommunityId(id);
        Community deletedCommunity = communityRepository.deleteById(id);
        deletedCommunity.setMembers(members);
        deletedCommunity.setPosts(posts);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил сообщество: " + deletedCommunity, String.class);
        return deletedCommunity.getId();
    }

    public Community show(int id, String login) {
        Community community = communityRepository.findById(id);
        if (community != null) {
            restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к сообществу: " + community, String.class);
            community.setMembers(communityMemberRepository.findAllByCommunityId(id));
            community.setPosts(communityPostRepository.findAllByCommunityId(id));
            community.setMember(communityMemberRepository.findByMemberLoginAndCommunityId(login, id) != null);
        }
        return community;
    }

    public CommunityMember join(CommunityMember communityMember) {
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
        restTemplate.postForObject(url + "true", "Пользователь " + joinedCommunityMember.getMemberLogin() + " присоединился к сообществу: " + joinedCommunityMember, String.class);
        return joinedCommunityMember;
    }

    /*public boolean isMember(Principal principal, int communityId) {
        return communityMemberRepository.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }*/

    public CommunityMember leave(CommunityMember communityMember) {
        CommunityMember leftCommunityMember = communityMemberRepository.deleteByMemberLoginAndCommunityId(communityMember);
        restTemplate.postForObject(url + "true", "Пользователь " + leftCommunityMember.getMemberLogin() + " покинул сообщество: " + leftCommunityMember, String.class);
        return leftCommunityMember;
    }

    public int kickCommunityMember(int id, String login) {
        CommunityMember kickedCommunityMember = communityMemberRepository.deleteById(id);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " выгнал из сообщества: " + kickedCommunityMember, String.class);
        return kickedCommunityMember.getId();
    }

    public CommunityPost createPost(CommunityPost communityPost) {
        CommunityPost createdCommunityPost = communityPostRepository.save(communityPost);
        restTemplate.postForObject(url + "true", "Пользователь " + createdCommunityPost.getAuthorLogin() + " написал пост: " + createdCommunityPost, String.class);
        return createdCommunityPost;
    }

    public void deletePost(int id, String login) {
        CommunityPost deletedCommunityPost = communityPostRepository.deleteById(id);
        restTemplate.postForObject(url + "true", "Пользователь " + login + " удалил пост: " + deletedCommunityPost, String.class);
        //return deletedCommunityPost.getId();
    }

    public List<Community> find(String keyword, String login) {
        List<Community> communities = communityRepository.findAllLikeName(keyword.trim());
        restTemplate.postForObject(url + "false", "Пользователь " + login + " ввел ключевые слова" + keyword + " и выполнил поиск сообществ: " + communities, String.class);
        return communities;
    }

    public List<Community> getAll(String login) {
        List<Community> communities = communityRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех сообществ: " + communities, String.class);
        return communities;
    }

    public List<CommunityMember> getAllMembers(String login) {
        List<CommunityMember> communityMembers = communityMemberRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех участников сообществ: " + communityMembers, String.class);
        return communityMembers;
    }

    public List<CommunityPost> getAllPosts(String login) {
        List<CommunityPost> communityPosts = communityPostRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех постов сообществ: " + communityPosts, String.class);
        return communityPosts;
    }

}
