package org.spring.postservice.utils;

import org.spring.postservice.models.Dtos.PostResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RankingUtil {

	public Double calculateRanking(PostResponse post) {
		return post.getLikes().getNumberOfLikes() * 0.5 + post.getComments().size() * 0.5;
	}

	public List<PostResponse> rankPosts(List<PostResponse> posts) {
		posts.sort((p1, p2) -> {
			Double rank1 = calculateRanking(p1);
			Double rank2 = calculateRanking(p2);
			return rank2.compareTo(rank1);
		});
		return posts;
	}
}