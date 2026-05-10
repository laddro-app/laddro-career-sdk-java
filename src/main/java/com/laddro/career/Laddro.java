package com.laddro.career;

import com.laddro.career.model.*;

public class Laddro {

    private final LaddroClient client;

    public Laddro(String apiKey) {
        this.client = new LaddroClient(apiKey);
    }

    public Laddro(String apiKey, String baseUrl) {
        this.client = new LaddroClient(apiKey, baseUrl);
    }

    public TemplateList listTemplates() throws LaddroException {
        return client.get("/v1/templates", TemplateList.class);
    }

    public TemplateDetail getTemplate(String templateId) throws LaddroException {
        return client.get("/v1/templates/" + templateId, TemplateDetail.class);
    }

    public FontList listFonts() throws LaddroException {
        return client.get("/v1/fonts", FontList.class);
    }

    public LanguageList listLanguages() throws LaddroException {
        return client.get("/v1/languages", LanguageList.class);
    }

    public ModelList listModels() throws LaddroException {
        return client.get("/v1/models", ModelList.class);
    }

    public ResumeList listResumes(int limit, int offset) throws LaddroException {
        return client.get("/v1/resumes?limit=" + limit + "&offset=" + offset, ResumeList.class);
    }

    public ResumeSummary getResume(String resumeId) throws LaddroException {
        return client.get("/v1/resumes/" + resumeId, ResumeSummary.class);
    }

    public byte[] renderResume(String resumeId, RenderOptions opts) throws LaddroException {
        return client.putBinary("/v1/resumes/" + resumeId + "/render", opts);
    }

    public byte[] tailor(TailorRequest request) throws LaddroException {
        return client.postBinary("/v1/tailor", request);
    }

    public byte[] exportPdf(ExportRequest request) throws LaddroException {
        return client.postBinary("/v1/export", request);
    }

    public CoverLetterList listCoverLetters(int limit, int offset) throws LaddroException {
        return client.get("/v1/cover-letters?limit=" + limit + "&offset=" + offset, CoverLetterList.class);
    }

    public CoverLetterSummary getCoverLetter(String id) throws LaddroException {
        return client.get("/v1/cover-letters/" + id, CoverLetterSummary.class);
    }

    public CreateCoverLetterResponse createCoverLetter(CreateCoverLetterRequest request) throws LaddroException {
        return client.post("/v1/cover-letters", request, CreateCoverLetterResponse.class);
    }

    public byte[] generateCoverLetter(GenerateCoverLetterRequest request) throws LaddroException {
        return client.postBinary("/v1/cover-letters/generate", request);
    }

    public byte[] renderCoverLetter(String id, RenderOptions opts) throws LaddroException {
        return client.putBinary("/v1/cover-letters/" + id + "/render", opts);
    }

    public SettingsResponse getSettings() throws LaddroException {
        return client.get("/v1/settings", SettingsResponse.class);
    }

    public SettingsResponse updateAiSettings(UpdateAISettingsRequest request) throws LaddroException {
        return client.put("/v1/settings/model", request, SettingsResponse.class);
    }

    public SettingsResponse deleteAiSettings() throws LaddroException {
        return client.delete("/v1/settings/model", SettingsResponse.class);
    }
}
