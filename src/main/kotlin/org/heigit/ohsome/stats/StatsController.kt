package org.heigit.ohsome.stats

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime


@RestController
class StatsController {


    @Autowired
    lateinit var repo: StatsRepo


    @Operation(summary = "Returns live data from DB")
    @GetMapping("/stats/{hashtag}")
    fun statsDB(
        @Parameter(description = "the hashtag to query for - case-insensitive and without the leading '#'")
        @PathVariable
        hashtag: String,

        @Parameter(description = "the (inclusive) start date for the query in ISO format (e.g. 2020-01-01T00:00:00Z)")
        @RequestParam("startdate", required = false)
        @DateTimeFormat(iso = ISO.DATE_TIME)
        startDate: OffsetDateTime?,

        @Parameter(description = "the (exclusive) end date for the query in ISO format (e.g. 2020-01-01T00:00:00Z)")
        @RequestParam("enddate", required = false)
        @DateTimeFormat(iso = ISO.DATE_TIME)
        endDate: OffsetDateTime?
    ): Map<String, Any> {

        return this.repo.getStats(hashtag) + echoRequestParameters(startDate, endDate)

    }


    fun echoRequestParameters(startDate: OffsetDateTime?, endDate: OffsetDateTime?): Map<String, OffsetDateTime> {
        var extraMap = emptyMap<String, OffsetDateTime>()

        startDate?.let {
            extraMap = extraMap + mapOf("startdate" to startDate)
        }

        endDate?.let {
            extraMap = extraMap + mapOf("enddate" to endDate)
        }

        return extraMap
    }


    // static data taken from http://osm-stats-production-api.azurewebsites.net/stats at 2pm, 20 March 2023
    @Operation(summary = "Returns a static snapshot of OSM statistics (for now)")
    @GetMapping("/stats_static")
    fun stats() = mapOf(
        "changesets" to 65009011,
        "users" to 3003842,
        "roads" to 45964973.0494135,
        "buildings" to 844294167,
        "edits" to 1095091515,
        "latest" to "2023-03-20T10:55:38.000Z",
        "hashtag" to "*"
    )


}
