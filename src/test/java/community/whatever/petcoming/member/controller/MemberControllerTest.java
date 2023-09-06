package community.whatever.petcoming.member.controller;

import community.whatever.petcoming.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private final String MEMBER_API_URL = "/api/v1/member/";
    private final String DOCUMENT_IDENTIFIER_PREFIX = "Member/";

    @Test
    @DisplayName("인증 체크 테스트")
    void authCheckTest() throws Exception {

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(MEMBER_API_URL + "auth-check"))
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                        MockMvcRestDocumentation.document(DOCUMENT_IDENTIFIER_PREFIX + "인증체크",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}