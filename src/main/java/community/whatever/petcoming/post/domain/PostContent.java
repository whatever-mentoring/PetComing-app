package community.whatever.petcoming.post.domain;

import community.whatever.petcoming.common.domain.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "post_content")
@Entity
public class PostContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 5000)
    private String content; // 댕글냥글(내용), 찾아주세요(상세 내용)

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
