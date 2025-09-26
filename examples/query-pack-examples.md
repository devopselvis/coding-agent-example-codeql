# CodeQL Query Pack Examples

This document provides examples of commonly used CodeQL query packs that can be passed to the reusable workflow.

## Built-in Query Packs

### Security-Focused Packs

```yaml
# SQL Injection queries
query-packs: 'codeql/java-queries:cwe-089'

# Cross-Site Scripting queries  
query-packs: 'codeql/java-queries:cwe-079'

# Command Injection queries
query-packs: 'codeql/java-queries:cwe-078'

# Path Traversal queries
query-packs: 'codeql/java-queries:cwe-022'

# All OWASP Top 10 related queries
query-packs: 'codeql/java-queries:owasp-top10'
```

### Quality and Maintainability

```yaml
# Code quality queries
query-packs: 'codeql/java-queries:quality'

# Maintainability queries
query-packs: 'codeql/java-queries:maintainability'

# Performance-related queries
query-packs: 'codeql/java-queries:performance'
```

### Combined Examples

```yaml
# Security + Quality analysis
query-packs: 'codeql/java-queries:security,codeql/java-queries:quality'

# Specific vulnerability types
query-packs: 'codeql/java-queries:cwe-079,codeql/java-queries:cwe-089,codeql/java-queries:cwe-078'

# Comprehensive analysis
query-packs: 'codeql/java-queries:security,codeql/java-queries:quality,codeql/java-queries:maintainability'
```

## Custom Query Packs

If your organization has custom query packs, you can specify them:

```yaml
# Organization-specific pack
query-packs: 'my-org/security-queries'

# Mix of custom and built-in packs
query-packs: 'my-org/security-queries,codeql/java-queries:cwe-089'

# Version-specific packs
query-packs: 'my-org/security-queries@1.2.0,codeql/java-queries:security'
```

## Query Suites vs Query Packs

**Query Suites** (use `query-suite` parameter):
- `default`: Standard security queries
- `security-extended`: Enhanced security analysis
- `security-and-quality`: Security + code quality

**Query Packs** (use `query-packs` parameter):
- More granular control
- Can specify multiple packs
- Can target specific vulnerability types
- Can include custom organizational packs

## Best Practices

1. **Start Simple**: Begin with `security-extended` suite
2. **Add Gradually**: Add specific query packs as needed  
3. **Performance**: More queries = longer analysis time
4. **Relevance**: Choose packs relevant to your codebase
5. **Testing**: Test new query packs on smaller repositories first

## Troubleshooting

**Pack Not Found Error**:
- Verify pack name spelling
- Ensure pack is publicly available
- Check if authentication is required

**Long Analysis Times**:
- Reduce number of query packs
- Use more specific packs instead of broad ones
- Consider running comprehensive analysis only on schedule

**No Results**:
- Verify the code contains detectable issues
- Check if the pack targets the right language
- Review CodeQL analysis logs for errors