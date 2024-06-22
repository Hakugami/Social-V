package org.spring.FriendService.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.FriendService.models.dtos.UserModelDTO;
import org.spring.FriendService.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {


    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public ResponseEntity<List<UserModelDTO>> getRecommendations(@RequestParam String userId, @RequestParam int limit) {
        List<UserModelDTO> recommendedFriends = recommendationService.getRecommendedFriends(userId, limit);
        return ResponseEntity.ok(recommendedFriends);
    }

    @GetMapping("/second-degree-connections")
    public List<UserModelDTO> getSecondDegreeConnections(@RequestParam String userId, @RequestParam int limit) {
        return recommendationService.getSecondDegreeConnections(userId, limit);
    }
}
