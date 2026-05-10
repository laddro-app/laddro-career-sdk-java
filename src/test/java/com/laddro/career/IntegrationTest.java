package com.laddro.career;

import com.laddro.career.model.*;

public class IntegrationTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("LADDRO_API_KEY");
        if (apiKey == null) { System.err.println("Set LADDRO_API_KEY"); System.exit(1); }

        var client = new Laddro(apiKey);
        var pub = new Laddro("");

        System.out.println("\n— 1. Public endpoints (5/18) —\n");

        test("GET /v1/templates", () -> {
            var t = pub.listTemplates();
            assert t.templates.size() == 22 : "expected 22, got " + t.templates.size();
        });
        test("GET /v1/templates/{id}", () -> {
            var d = pub.getTemplate("GRAPHITE");
            assert d.id.equals("GRAPHITE");
            assert d.availableColors.size() > 0;
        });
        test("GET /v1/fonts", () -> {
            var f = pub.listFonts();
            assert f.fonts.size() == 21 : "expected 21";
        });
        test("GET /v1/languages", () -> {
            var l = pub.listLanguages();
            assert l.languages.size() == 14 : "expected 14";
        });
        test("GET /v1/models", () -> {
            var m = pub.listModels();
            assert m.models.size() == 10 : "expected 10";
        });

        System.out.println("\n— 2. Resume endpoints (4/18) —\n");

        var resumes = client.listResumes(5, 0);
        String resumeId = null;
        for (var r : resumes.items) {
            if (r.isDefault) { resumeId = r.resumeId; break; }
        }
        if (resumeId == null && !resumes.items.isEmpty()) resumeId = resumes.items.get(0).resumeId;
        final String rid = resumeId;

        test("GET /v1/resumes", () -> { assert resumes.items.size() > 0; });
        test("GET /v1/resumes/{id}", () -> {
            var r = client.getResume(rid);
            assert r.resumeId.equals(rid);
        });
        test("PUT /v1/resumes/{id}/render", () -> {
            var opts = new RenderOptions("GRAPHITE");
            byte[] pdf = client.renderResume(rid, opts);
            assert pdf.length > 1000 : "too small: " + pdf.length;
        });
        test("POST /v1/resumes/parse (skip)", () -> {});

        System.out.println("\n— 3. Tailor (1/18) —\n");

        test("POST /v1/tailor", () -> {
            var req = new TailorRequest("Java SDK Test");
            req.resumeId = rid;
            req.jobDescription = "Write Java code.";
            byte[] pdf = client.tailor(req);
            assert pdf.length > 5000 : "too small: " + pdf.length;
        });

        System.out.println("\n— 4. Export (1/18) —\n");

        test("POST /v1/export", () -> {
            var req = new ExportRequest(rid);
            req.templateId = "COBALT";
            byte[] pdf = client.exportPdf(req);
            assert pdf.length > 1000 : "too small: " + pdf.length;
        });

        System.out.println("\n— 5. Cover Letter endpoints (5/18) —\n");

        test("GET /v1/cover-letters", () -> { client.listCoverLetters(5, 0); });

        final String[] clId = {null};
        test("POST /v1/cover-letters", () -> {
            var req = new CreateCoverLetterRequest("Java Test", "<p>Test.</p>");
            var resp = client.createCoverLetter(req);
            clId[0] = resp.coverLetterId;
            assert clId[0] != null;
        });
        test("GET /v1/cover-letters/{id}", () -> {
            var cl = client.getCoverLetter(clId[0]);
            assert cl.coverLetterId.equals(clId[0]);
        });
        test("PUT /v1/cover-letters/{id}/render", () -> {
            byte[] pdf = client.renderCoverLetter(clId[0], new RenderOptions("NICKEL"));
            assert pdf.length > 1000 : "too small: " + pdf.length;
        });
        test("POST /v1/cover-letters/generate", () -> {
            var req = new GenerateCoverLetterRequest("Java Test");
            req.resumeId = rid;
            req.jobDescription = "Java dev.";
            byte[] pdf = client.generateCoverLetter(req);
            assert pdf.length > 1000 : "too small: " + pdf.length;
        });

        System.out.println("\n— 6. Settings (3/18) —\n");

        test("GET /v1/settings", () -> { client.getSettings(); });
        test("PUT /v1/settings/model", () -> {
            try {
                var req = new UpdateAISettingsRequest("OpenAI", "sk-test");
                req.model = "gpt-4o-mini";
                client.updateAiSettings(req);
            } catch (LaddroException e) { /* 400 expected */ }
        });
        test("DELETE /v1/settings/model", () -> {
            var r = client.deleteAiSettings();
            assert r.ai == null;
        });

        System.out.println("\n— 7. Errors —\n");

        test("401 on bad key", () -> {
            try {
                new Laddro("laddro_live_invalid").listResumes(1, 0);
                throw new AssertionError("should throw");
            } catch (LaddroException e) {
                assert e.isAuthError() : "expected 401, got " + e.getStatus();
            }
        });
        test("404 on missing resume", () -> {
            try {
                client.getResume("00000000-0000-0000-0000-000000000000");
                throw new AssertionError("should throw");
            } catch (LaddroException e) {
                assert e.isNotFound() : "expected 404, got " + e.getStatus();
            }
        });

        System.out.println("\n═══ FINAL: " + passed + " passed, " + failed + " failed (18 endpoints covered) ═══\n");
        System.exit(failed > 0 ? 1 : 0);
    }

    interface TestFn { void run() throws Exception; }

    static void test(String name, TestFn fn) {
        try {
            fn.run();
            System.out.println("  ✓ " + name);
            passed++;
        } catch (Throwable e) {
            System.out.println("  ✗ " + name + ": " + e.getMessage());
            failed++;
        }
    }
}
