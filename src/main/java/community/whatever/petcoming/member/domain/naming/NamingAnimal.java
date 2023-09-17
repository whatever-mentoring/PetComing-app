package community.whatever.petcoming.member.domain.naming;

import lombok.Getter;

@Getter
public enum NamingAnimal {
    DOG("강아지"), CAT("고양이"), HAMSTER("햄스터"),
    RABBIT("토끼"), GOLDFISH("금붕어"), TURTLE("거북이"),
    CANARY("카나리아"), PARAKEET("앵무새"), FERRET("페럿"),
    GUINEA_PIG("기니피그"), SQUIRREL("다람쥐"), SWALLOW("제비"),
    SPARROW("참새"), CHICK("병아리"), HEDGEHOG("고슴도치"),
    DUCK("오리"), LIZARD("도마뱀"), PANDA("팬더"),
    KIWI("키위"), PENGUIN("펭귄"), MOLE("두더지"),
    MEERKAT("미어캣"), IGUANA("이구아나"), GERBIL("저빌"),
    CHIPMUNK("치프먼크"), SEAL("물개"), GECKO("도마뱀"),
    QUOKKA("쿼카"), LOVEBIRD("왈라비"), CHINCHILLA("친칠라");

    private final String korean;

    NamingAnimal(String korean) {
        this.korean = korean;
    }
}
