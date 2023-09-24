package community.whatever.petcoming.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.LostPetFeedFullResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedSubmitRequest;
import community.whatever.petcoming.feed.service.LostPetFeedService;
import community.whatever.petcoming.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(LostPetFeedRestController.class)
class LostPetFeedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LostPetFeedService lostPetFeedService;

    @MockBean
    private MemberService memberService;

    private final String LOST_PET_FEED_URL = "/api/v1/feed/lost-pet/";
    private final String DOCUMENT_IDENTIFIER_PREFIX = "LostPetFeed/";

    @BeforeEach
    public void setUp() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "1234567890");
        OidcIdToken idToken = new OidcIdToken("asdf", Instant.now(), Instant.now().plusSeconds(60), claims);
        OAuth2UserAuthority authority = new OAuth2UserAuthority("ROLE_USER", claims);
        OidcUser oidcUser = new DefaultOidcUser(Collections.singletonList(authority), idToken);

        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(oidcUser, Collections.emptyList(), "oidc"));
    }

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
                .lostArea("평택역")
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .viewCount(100L)
                .likeCount(100L)
                .build();
        list.add(response);

        String keyword = "고양이";
        AnimalGender animalGender = AnimalGender.MALE;
        AnimalType animalType = AnimalType.CAT;

        BDDMockito.given(lostPetFeedService.getLostPetFeedInfoList(
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any(Integer.class),
                        ArgumentMatchers.any(FeedsSortOption.class),
                        ArgumentMatchers.eq(keyword),
                        ArgumentMatchers.eq(animalGender),
                        ArgumentMatchers.eq(animalType)))
                .willReturn(list);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(LOST_PET_FEED_URL)
                        .param("size", "10")
                        .param("sort", "LATEST")
                        .param("keyword", keyword)
                        .param("animalGender", animalGender.name())
                        .param("animalType", animalType.name())
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "목록_첫조회",
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
                .lostArea("평택역")
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .viewCount(100L)
                .likeCount(100L)
                .build();
        list.add(response);

        String keyword = "고양이";
        AnimalGender animalGender = AnimalGender.MALE;
        AnimalType animalType = AnimalType.CAT;

        BDDMockito.given(lostPetFeedService.getLostPetFeedInfoList(
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any(Integer.class),
                        ArgumentMatchers.any(FeedsSortOption.class),
                        ArgumentMatchers.eq(keyword),
                        ArgumentMatchers.eq(animalGender),
                        ArgumentMatchers.eq(animalType)))
                .willReturn(list);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(LOST_PET_FEED_URL)
                        .param("last-feed", "1")
                        .param("size", "10")
                        .param("sort", "POPULAR")
                        .param("keyword", keyword)
                        .param("gender", animalGender.name())
                        .param("animalType", animalType.name())
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
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp");

        LostPetFeedFullResponse fullResponse = LostPetFeedFullResponse.builder()
                .feedId(targetFeedId)
                .specialNote("고양이가 귀여워요")
                .authorName("집사1")
                .animalType(AnimalType.CAT)
                .animalGender(AnimalGender.MALE)
                .breed("코리안 숏헤어")
                .lostArea("평택역")
                .viewCount(100L)
                .likeCount(100L)
                .imageUrls(imageUrls)
                .details("이 고양이는 매우 귀여운 고양이입니다. 찾아주세요")
                .createDate(LocalDateTime.now())
                .build();

        BDDMockito.given(lostPetFeedService.getLostPetFeedFull(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(fullResponse);

        //when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get(LOST_PET_FEED_URL + "{feedId}", targetFeedId))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "전체피드조회",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("feedId").description("피드 Identifier")
                                )
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("댕글냥글 피드 작성")
    void 댕글냥글_피드_작성() throws Exception {
        //given
        BDDMockito.doNothing().when(lostPetFeedService).submitFeed(ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());
        BDDMockito.when(memberService.findIdByProviderIdAndSubject(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(1L);

        LostPetFeedSubmitRequest dto = LostPetFeedSubmitRequest.builder()
                .specialNote("특이사항입니다.")
                .details("세부내용입니다.")
                .animalType(AnimalType.DOG)
                .animalGender(AnimalGender.MALE)
                .breed("말라뮤트")
                .lostArea("평택역")
                .contact("010-1234-5678")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String dtoJson = objectMapper.writeValueAsString(dto);

        MockMultipartFile dtoPart = new MockMultipartFile("dto", "", "application/json", dtoJson.getBytes());
        MockMultipartFile file1 = new MockMultipartFile("images", "file1.png", "image/png", "some-image".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("images", "file2.png", "image/png", "some-image".getBytes());

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.multipart(LOST_PET_FEED_URL + "/submit")
                        .file(dtoPart)
                        .file(file1)
                        .file(file2)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "submit-feed",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("피드 좋아요")
    public void likeFeed() throws Exception {
        // Given
        BDDMockito.when(lostPetFeedService.likeFeed(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(1L);
        BDDMockito.when(memberService.findIdByProviderIdAndSubject(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(1L);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(LOST_PET_FEED_URL + "/1/like")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "like-feed",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    @DisplayName("피드 좋아요 취소")
    public void unlikeFeed() throws Exception {
        // Given
        BDDMockito.when(lostPetFeedService.unlikeFeed(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(0L);
        BDDMockito.when(memberService.findIdByProviderIdAndSubject(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(1L);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(LOST_PET_FEED_URL + "/1/unlike")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "unlike-feed",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

    @Test
    @DisplayName("피드 삭제")
    public void deleteFeed() throws Exception {
        BDDMockito.doNothing().when(lostPetFeedService).deleteFeed(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
        BDDMockito.when(memberService.findIdByProviderIdAndSubject(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(1L);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(LOST_PET_FEED_URL + "/1/delete") //"/{feedId}/delete
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "delete-feed",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}