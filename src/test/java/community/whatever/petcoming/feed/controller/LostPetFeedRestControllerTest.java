package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.LostPetFeedFullResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import community.whatever.petcoming.feed.service.LostPetFeedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(LostPetFeedRestController.class)
class LostPetFeedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LostPetFeedService lostPetFeedService;

    private final String LOST_PET_FEED_URL = "/api/v1/feed/lost-pet/";
    private final String DOCUMENT_IDENTIFIER_PREFIX = "LostPetFeed/";

    @Test
    @DisplayName("찾아주세요 피드 목록 조회. 첫 조회")
    void 첫조회_LostPetFeedInfoList() throws Exception {

        //given
        List<LostPetFeedInfoResponse> list = new ArrayList<>();
        LostPetFeedInfoResponse response = LostPetFeedInfoResponse.builder()
                .feedId(10L)
                .specialNote("고양이가 귀여워요")
                .authorName("집사1")
                .animalType(AnimalType.CAT)
                .animalGender(AnimalGender.MALE)
                .breed("코리안 숏헤어")
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .viewCount(100L)
                .likeCount(100L)
                .build();
        list.add(response);

        BDDMockito.given(lostPetFeedService.getLostPetFeedInfoList(
                        ArgumentMatchers.any(Integer.class),
                        ArgumentMatchers.any(FeedsSortOption.class)))
                .willReturn(list);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(LOST_PET_FEED_URL)
                        .param("size", "10")
                        .param("sort", "LATEST")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX+"목록_첫조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("찾아주세요 피드 목록 조회. 재조회")
    void 재조회_getLostPetFeedInfoList() throws Exception {

        //given
        List<LostPetFeedInfoResponse> list = new ArrayList<>();
        LostPetFeedInfoResponse response = LostPetFeedInfoResponse.builder()
                .feedId(10L)
                .specialNote("고양이가 귀여워요")
                .authorName("집사1")
                .animalType(AnimalType.CAT)
                .animalGender(AnimalGender.MALE)
                .breed("코리안 숏헤어")
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .viewCount(100L)
                .likeCount(100L)
                .build();
        list.add(response);

        BDDMockito.given(lostPetFeedService.getLostPetFeedInfoList(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.any(Integer.class),
                        ArgumentMatchers.any(FeedsSortOption.class))
                )
                .willReturn(list);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(LOST_PET_FEED_URL)
                        .param("last-feed", "1")
                        .param("size", "10")
                        .param("sort", "POPULAR")
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "목록_재조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("찾아주세요 피드 상세정보 조회")
    void 찾아주세요_피드_상세정보_조회() throws Exception {

        //given
        Long targetFeedId = 10L;

        LostPetFeedFullResponse fullResponse = LostPetFeedFullResponse.builder()
                .feedId(targetFeedId)
                .specialNote("고양이가 귀여워요")
                .authorName("집사1")
                .animalType(AnimalType.CAT)
                .animalGender(AnimalGender.MALE)
                .breed("코리안 숏헤어")
                .viewCount(100L)
                .likeCount(100L)
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .details("이 고양이는 매우 귀여운 고양이입니다. 찾아주세요")
                .createDate(LocalDateTime.now())
                .build();

        BDDMockito.given(lostPetFeedService.getLostPetFeedFull(ArgumentMatchers.any()))
                .willReturn(fullResponse);

        //when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get(LOST_PET_FEED_URL + "{feedId}", targetFeedId))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "전체피드조회",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("feedId").description("피드 Identifier")
                                )
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}