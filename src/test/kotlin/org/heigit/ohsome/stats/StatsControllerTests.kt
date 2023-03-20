package org.heigit.ohsome.stats

import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(StatsController::class)
class StatsControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc


    //language=JSON
    private val expected = """{
          "changesets": 65009011,
          "users": 3003842,
          "roads": 45964973.0494135,
          "buildings": 844294167,
          "edits": 1095091515,
          "latest": "2023-03-20T10:55:38.000Z",
          "hashtag": "*"
      }"""


    @Test
    fun `stats should return a static map of stats values`() {

        this.mockMvc
            .perform(get("/stats"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().json(expected, false))

    }

}