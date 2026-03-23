package com.restauranthub.multitenant_restaurant_api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:docker_profile_test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.datasource.driver-class-name=org.h2.Driver" })
@ActiveProfiles("docker")
class DockerProfileApplicationTest {

	@Autowired
	private Environment environment;

	@Test
	void shouldLoadDockerProfileWithContainerFriendlySettings() {
		assertArrayEquals(new String[] { "docker" }, environment.getActiveProfiles());
		assertEquals("org.h2.Driver", environment.getProperty("spring.datasource.driver-class-name"));
		assertEquals("false", environment.getProperty("spring.h2.console.enabled"));
		assertEquals("restaurant_hub", environment.getProperty("spring.flyway.default-schema"));
	}
}
