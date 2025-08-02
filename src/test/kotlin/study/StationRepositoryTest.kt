package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private lateinit var stations: StationRepository

    @Test
    fun save() {
        val expected = Station(name = "pankow")
        val actual = stations.save(expected)
        assertThat(actual.id).isNotNull()
        assertThat(actual.name).isEqualTo(expected.name)
    }

    @Test
    fun findByName() {
        val expected = "pankow"
        stations.save(Station(name = expected))
        val actual = stations.findByName(expected)?.name
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun identity() {
        val station1 = stations.save(Station(name = "pankow"))
        //val station3 = stations.save(Station(name = "pankow"))
        val station2 = stations.findById(station1.id!!).get()
        assertThat(station1 === station2).isTrue() // for == id must be the same
    }

    @Test
    fun update() {
        val station1 = stations.save(Station("pankow"))
        station1.changeName("oranienburger")
        val station2 = stations.findByName("oranienburger")
        assertThat(station2).isNotNull()
    }
}
