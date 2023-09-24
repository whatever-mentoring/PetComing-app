package community.whatever.petcoming.feed.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LostPetFeedRepository extends JpaRepository<LostPetFeed, Long> {

    List<LostPetFeed> findByIdLessThan(Long lastFeedId, Pageable pageable);

    @Modifying
    @Query("UPDATE LostPetFeed f SET f.viewCount = f.viewCount + 1 WHERE f.id = :id")
    void increaseViewCountById(Long id);

    @Query("SELECT f FROM LostPetFeed f WHERE " +
            "(:lastFeedId IS NULL OR f.id < :lastFeedId) AND " +
            "(:keyword IS NULL OR f.title LIKE %:keyword%) AND " +
            "(:gender IS NULL OR f.animalGender = :animalGender) AND " +
            "(:animalType IS NULL OR f.animalType = :animalType)")
    List<LostPetFeed> findWithFilters(
            @Param("lastFeedId") Long lastFeedId,
            @Param("keyword") String keyword,
            @Param("animalGender") AnimalGender animalGender,
            @Param("animalType") AnimalType animalType,
            Pageable pageable);
}
