# Laddro Career SDK for Java

Java/Kotlin SDK for the [Laddro Career API](https://api.laddro.com/reference).

## Install

Maven:
```xml
<dependency>
    <groupId>com.laddro</groupId>
    <artifactId>career-sdk</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

```java
import com.laddro.career.Laddro;
import com.laddro.career.model.*;

var laddro = new Laddro("laddro_live_...");

// List resumes
var resumes = laddro.listResumes(20, 0);
for (var r : resumes.items) {
    System.out.println(r.title);
}

// Tailor a resume
var request = new TailorRequest("Senior Frontend Engineer");
request.jobUrl = "https://jobs.example.com/sfe";
byte[] pdf = laddro.tailor(request);
Files.write(Path.of("tailored.pdf"), pdf);

// Export with template
var export = new ExportRequest("resume-id");
export.templateId = "GRAPHITE";
byte[] exported = laddro.exportPdf(export);

// Browse templates
var templates = laddro.listTemplates();
```

## Links

- [laddro.com](https://laddro.com)
- [API Reference](https://api.laddro.com/reference)
- [Docs](https://docs.laddro.com)
- [GitHub](https://github.com/laddro-app)

## License

MIT
