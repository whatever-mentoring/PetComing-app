package community.whatever.petcoming.post.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "find_post_content")
public class FindPostContent extends PostContent {

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType animalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender")
    private AnimalGender animalGender;

    @Column(name = "breed", length = 32)
    private String breed; // 품종

    @Column(name = "lost_area", length = 127)
    private String lostArea;

    @Column(name = "contact", length = 32)
    private String contact;
}
