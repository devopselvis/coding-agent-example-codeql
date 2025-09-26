package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * REST Controller with intentional security vulnerabilities for CodeQL demonstration.
 * 
 * WARNING: This code contains deliberate security vulnerabilities and should NEVER
 * be used in production. It is designed solely for educational purposes to demonstrate
 * how CodeQL can detect common security issues.
 * 
 * Vulnerabilities included:
 * 1. SQL Injection
 * 2. Cross-Site Scripting (XSS)  
 * 3. Path Traversal
 * 4. Command Injection
 */
@RestController
@RequestMapping("/api")
public class VulnerableController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * SQL Injection vulnerability: Direct concatenation of user input into SQL query
     * CodeQL should detect this as CWE-89: SQL Injection
     */
    @GetMapping("/users/{userId}")
    public List<Map<String, Object>> getUser(@PathVariable String userId) {
        // VULNERABLE: Direct string concatenation creates SQL injection risk
        String sql = "SELECT * FROM users WHERE id = '" + userId + "'";
        return jdbcTemplate.queryForList(sql);
    }
    
    /**
     * Another SQL Injection vulnerability with search functionality
     * CodeQL should detect this as CWE-89: SQL Injection
     */
    @GetMapping("/search")
    public List<Map<String, Object>> searchUsers(@RequestParam String name) {
        // VULNERABLE: User input directly embedded in SQL query
        String sql = "SELECT * FROM users WHERE name LIKE '%" + name + "%'";
        return jdbcTemplate.queryForList(sql);
    }
    
    /**
     * Cross-Site Scripting (XSS) vulnerability
     * CodeQL should detect this as CWE-79: Cross-site Scripting
     */
    @GetMapping("/welcome")
    public void welcomeUser(@RequestParam String username, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // VULNERABLE: User input directly written to HTML output without encoding
        out.println("<html><body>");
        out.println("<h1>Welcome " + username + "!</h1>");
        out.println("</body></html>");
    }
    
    /**
     * Path Traversal vulnerability
     * CodeQL should detect this as CWE-22: Path Traversal
     */
    @GetMapping("/files/{filename}")
    public String readFile(@PathVariable String filename) {
        try {
            // VULNERABLE: User input used directly in file path
            java.nio.file.Path path = java.nio.file.Paths.get("/app/data/" + filename);
            return java.nio.file.Files.readString(path);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }
    
    /**
     * Command Injection vulnerability
     * CodeQL should detect this as CWE-78: Command Injection
     */
    @GetMapping("/ping")
    public String pingHost(@RequestParam String host) {
        try {
            // VULNERABLE: User input directly used in system command
            Process process = Runtime.getRuntime().exec("ping -c 1 " + host);
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (Exception e) {
            return "Error executing ping: " + e.getMessage();
        }
    }
}