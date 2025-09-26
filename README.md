# CodeQL Reusable Workflow Demo

This repository demonstrates how to use GitHub Advanced Security CodeQL Code Scanning with reusable workflows that accept query pack suggestions. It includes a Java application with intentional security vulnerabilities to showcase CodeQL's detection capabilities.

## 🎯 Purpose

This demo addresses a common scenario where teams want to:
- Use reusable workflows for CodeQL analysis across multiple repositories
- Pass custom query packs to enhance security scanning
- Maintain consistency in security analysis while allowing customization

## 📁 Repository Structure

```
├── src/main/java/com/example/demo/          # Java application source code
│   ├── DemoApplication.java                 # Main Spring Boot application
│   ├── VulnerableController.java            # REST controller with security vulnerabilities
│   └── DatabaseInitializer.java             # Database setup component
├── src/main/resources/
│   └── application.properties               # Spring Boot configuration
├── .github/workflows/
│   ├── codeql-reusable.yml                 # Reusable CodeQL workflow
│   ├── codeql-analysis.yml                 # Calling workflow with query pack examples
│   └── ci.yml                              # Basic CI workflow
├── pom.xml                                  # Maven build configuration
└── README.md                               # This documentation
```

## 🔧 Components

### Java Application
A simple Spring Boot web application that intentionally contains security vulnerabilities:

- **SQL Injection (CWE-89)**: Direct string concatenation in SQL queries
- **Cross-Site Scripting (CWE-79)**: Unescaped user input in HTML output
- **Path Traversal (CWE-22)**: User input directly used in file paths
- **Command Injection (CWE-78)**: User input passed to system commands

⚠️ **WARNING**: This application contains deliberate security vulnerabilities and should NEVER be deployed to production.

### Reusable Workflow (`.github/workflows/codeql-reusable.yml`)

A flexible, reusable workflow that:
- Accepts custom query packs as input parameters
- Supports different query suites (default, security-extended, security-and-quality)
- Handles Java project builds automatically (Maven/Gradle)
- Provides comprehensive error handling and logging
- Uploads analysis results as artifacts

**Key Input Parameters:**
- `query-packs`: Comma-separated list of CodeQL query packs
- `query-suite`: CodeQL query suite to use
- `language`: Programming language (default: java)
- `java-version`: Java version for builds (default: 11)
- `build-command`: Custom build command override

### Calling Workflow (`.github/workflows/codeql-analysis.yml`)

Demonstrates three different analysis approaches:
1. **Standard Analysis**: Basic security scanning with default queries
2. **Enhanced Analysis**: Additional query packs for comprehensive scanning
3. **Manual Analysis**: User-triggered analysis with custom parameters

## 🚀 Usage

### Basic Usage

The workflows will automatically run on:
- Pushes to `main` or `develop` branches
- Pull requests to `main` branch

### Manual Triggering

You can manually trigger enhanced analysis:

1. Go to **Actions** tab in your GitHub repository
2. Select **CodeQL Security Analysis** workflow
3. Click **Run workflow**
4. Optionally specify custom query packs and suite

### Custom Query Packs

Examples of query pack specifications:

```yaml
# Specific vulnerability types
query-packs: 'codeql/java-queries:cwe-079,codeql/java-queries:cwe-089'

# Security and quality analysis
query-packs: 'codeql/java-queries:security,codeql/java-queries:quality'

# Third-party query packs (if available)
query-packs: 'my-org/custom-security-queries,codeql/java-queries:cwe-078'
```

### Available Query Suites

- `default`: Standard security queries
- `security-extended`: Enhanced security analysis (recommended)
- `security-and-quality`: Security + code quality analysis

## 🔍 Expected CodeQL Findings

When you run CodeQL analysis on this repository, you should see alerts for:

1. **SQL Injection** in `VulnerableController.getUser()` and `searchUsers()`
2. **Cross-Site Scripting** in `VulnerableController.welcomeUser()`  
3. **Path Traversal** in `VulnerableController.readFile()`
4. **Command Injection** in `VulnerableController.pingHost()`

## 🛠️ Building Locally

To build and run the application locally:

```bash
# Build the application
mvn clean compile

# Package the application
mvn package

# Run the application (optional - contains vulnerabilities!)
mvn spring-boot:run
```

The application will start on `http://localhost:8080` with the following endpoints:
- `/api/users/{userId}` - SQL injection vulnerability
- `/api/search?name=<name>` - SQL injection vulnerability  
- `/api/welcome?username=<name>` - XSS vulnerability
- `/api/files/{filename}` - Path traversal vulnerability
- `/api/ping?host=<host>` - Command injection vulnerability

## 📚 Learning Resources

### CodeQL Documentation
- [CodeQL Documentation](https://codeql.github.com/docs/)
- [CodeQL Query Packs](https://docs.github.com/en/code-security/codeql-cli/using-the-codeql-cli/publishing-and-using-codeql-packs)
- [GitHub Advanced Security](https://docs.github.com/en/get-started/learning-about-github/about-github-advanced-security)

### Reusable Workflows
- [Reusing Workflows](https://docs.github.com/en/actions/using-workflows/reusing-workflows)
- [Workflow Syntax](https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions)

## 🔧 Troubleshooting

### Common Issues

**Issue**: "Cannot find query pack"
- **Solution**: Ensure query pack names are correct and accessible to your repository
- **Check**: Verify the query pack exists in the CodeQL registry
- **Alternative**: Use built-in query suites instead of custom packs

**Issue**: Build failures in reusable workflow
- **Solution**: Check the `build-command` parameter or ensure auto-detection works
- **Debug**: Enable verbose logging by setting `CODEQL_ACTION_DEBUG: true`

**Issue**: No security alerts generated
- **Solution**: Verify the code contains detectable vulnerabilities
- **Check**: Ensure CodeQL is analyzing the correct language and files

### Debugging Tips

1. **Enable Debug Logging**: Set `CODEQL_ACTION_DEBUG: true` in the workflow
2. **Check Build Logs**: Review the "Build application" step for compilation errors
3. **Verify Query Packs**: Ensure specified query packs are publicly available
4. **Review Permissions**: Confirm the workflow has `security-events: write` permission

## 🤝 Contributing

This is a demonstration repository. If you find issues or have suggestions for improvement:

1. Open an issue describing the problem or enhancement
2. Include steps to reproduce any issues
3. Provide context about your use case

## ⚠️ Security Notice

This repository contains intentionally vulnerable code for educational purposes. Do not:
- Deploy this application to production environments
- Use the vulnerable code patterns in real applications
- Expose this application to untrusted networks

The vulnerabilities are designed to demonstrate CodeQL's detection capabilities and should be treated as examples of what NOT to do in secure coding practices.

## 📄 License

This project is provided as-is for educational and demonstration purposes.