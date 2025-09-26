package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Component to initialize the database with sample data for demonstration.
 * This creates a simple users table and populates it with test data.
 */
@Component
public class DatabaseInitializer {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Initialize the database schema and sample data after bean construction
     */
    @PostConstruct
    public void initializeDatabase() {
        try {
            // Create users table
            jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "email VARCHAR(255)" +
                ")"
            );
            
            // Insert sample data
            jdbcTemplate.update(
                "INSERT INTO users (id, name, email) VALUES (?, ?, ?)",
                1, "John Doe", "john.doe@example.com"
            );
            
            jdbcTemplate.update(
                "INSERT INTO users (id, name, email) VALUES (?, ?, ?)",
                2, "Jane Smith", "jane.smith@example.com"
            );
            
            jdbcTemplate.update(
                "INSERT INTO users (id, name, email) VALUES (?, ?, ?)",
                3, "Bob Johnson", "bob.johnson@example.com"
            );
            
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}