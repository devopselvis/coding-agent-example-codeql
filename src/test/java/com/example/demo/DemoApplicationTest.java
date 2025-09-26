package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic integration test to verify the Spring Boot application context loads correctly.
 * This test ensures the application can start up without errors.
 * 
 * Note: This application contains intentional security vulnerabilities for demonstration
 * purposes. In real applications, you would have comprehensive security tests.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb-test",
    "logging.level.com.example.demo=WARN"
})
class DemoApplicationTest {

    /**
     * Test that the Spring Boot application context loads successfully.
     * This verifies that:
     * - All beans can be created
     * - Database initialization works
     * - No configuration errors exist
     */
    @Test
    void contextLoads() {
        // Spring Boot will automatically verify the context loads
        // If there are any issues with bean creation or configuration,
        // this test will fail
    }
}