package com.laddro.career;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laddro.career.model.ArtifactMetadata;
import com.laddro.career.model.BinaryResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LaddroClient {

    private static final String DEFAULT_BASE_URL = "https://api.laddro.com";

    private final String baseUrl;
    private final String apiKey;
    private final HttpClient http;
    private final ObjectMapper mapper;

    public LaddroClient(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL);
    }

    public LaddroClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T get(String path, Class<T> responseType) throws LaddroException {
        var request = newRequest(path).GET().build();
        return execute(request, responseType);
    }

    public byte[] getBinary(String path) throws LaddroException {
        var request = newRequest(path).GET().build();
        return executeBinary(request);
    }

    public <T> T post(String path, Object body, Class<T> responseType) throws LaddroException {
        var request = newRequest(path)
                .POST(jsonBody(body))
                .header("Content-Type", "application/json")
                .build();
        return execute(request, responseType);
    }

    public byte[] postBinary(String path, Object body) throws LaddroException {
        var request = newRequest(path)
                .POST(jsonBody(body))
                .header("Content-Type", "application/json")
                .build();
        return executeBinaryDetailed(request).data();
    }

    public BinaryResponse postBinaryDetailed(String path, Object body) throws LaddroException {
        var request = newRequest(path)
                .POST(jsonBody(body))
                .header("Content-Type", "application/json")
                .build();
        return executeBinaryDetailed(request);
    }

    public <T> T put(String path, Object body, Class<T> responseType) throws LaddroException {
        var request = newRequest(path)
                .PUT(jsonBody(body))
                .header("Content-Type", "application/json")
                .build();
        return execute(request, responseType);
    }

    public byte[] putBinary(String path, Object body) throws LaddroException {
        var request = newRequest(path)
                .PUT(jsonBody(body))
                .header("Content-Type", "application/json")
                .build();
        return executeBinary(request);
    }

    public <T> T delete(String path, Class<T> responseType) throws LaddroException {
        var request = newRequest(path).DELETE().build();
        return execute(request, responseType);
    }

    private HttpRequest.Builder newRequest(String path) {
        var builder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(Duration.ofSeconds(120));
        if (apiKey != null && !apiKey.isEmpty()) {
            builder.header("x-api-key", apiKey);
        }
        return builder;
    }

    private HttpRequest.BodyPublisher jsonBody(Object body) {
        try {
            return HttpRequest.BodyPublishers.ofByteArray(mapper.writeValueAsBytes(body));
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize request body", e);
        }
    }

    private <T> T execute(HttpRequest request, Class<T> responseType) throws LaddroException {
        try {
            var response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() >= 400) {
                throw parseError(response);
            }
            return mapper.readValue(response.body(), responseType);
        } catch (LaddroException e) {
            throw e;
        } catch (Exception e) {
            throw new LaddroException("Request failed: " + e.getMessage(), 0, null);
        }
    }

    private byte[] executeBinary(HttpRequest request) throws LaddroException {
        return executeBinaryDetailed(request).data();
    }

    private BinaryResponse executeBinaryDetailed(HttpRequest request) throws LaddroException {
        try {
            var response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() >= 400) {
                throw parseError(response);
            }
            return new BinaryResponse(response.body(), artifactMetadata(response));
        } catch (LaddroException e) {
            throw e;
        } catch (Exception e) {
            throw new LaddroException("Request failed: " + e.getMessage(), 0, null);
        }
    }

    private LaddroException parseError(HttpResponse<byte[]> response) {
        try {
            var node = mapper.readTree(response.body());
            var message = node.has("error") ? node.get("error").asText() : "Unknown error";
            var code = node.has("code") ? node.get("code").asText() : null;
            return new LaddroException(message, response.statusCode(), code);
        } catch (Exception e) {
            return new LaddroException("HTTP " + response.statusCode(), response.statusCode(), null);
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    private ArtifactMetadata artifactMetadata(HttpResponse<byte[]> response) {
        var headers = response.headers();
        var contentType = headers.firstValue("content-type")
                .map(value -> value.split(";", 2)[0])
                .orElse(null);

        return new ArtifactMetadata(
                headers.firstValue("x-resume-id").orElse(null),
                headers.firstValue("x-cover-letter-id").orElse(null),
                contentDispositionFilename(headers.firstValue("content-disposition").orElse(null)),
                contentType
        );
    }

    private String contentDispositionFilename(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (String part : value.split(";")) {
            var trimmed = part.trim();
            if (!trimmed.toLowerCase().startsWith("filename") || !trimmed.contains("=")) {
                continue;
            }
            var filename = trimmed.substring(trimmed.indexOf("=") + 1)
                    .replaceFirst("^UTF-8''", "")
                    .replaceAll("^\"|\"$", "");
            return java.net.URLDecoder.decode(filename, java.nio.charset.StandardCharsets.UTF_8);
        }
        return null;
    }
}
