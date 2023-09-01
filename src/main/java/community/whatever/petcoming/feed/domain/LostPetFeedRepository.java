package community.whatever.petcoming.feed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LostPetFeedRepository extends JpaRepository<LostPetFeed, Long> {
}
