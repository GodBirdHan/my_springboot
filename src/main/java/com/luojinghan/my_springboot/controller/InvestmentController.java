package com.luojinghan.my_springboot.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 投研模块 REST API。
 * 该控制器直接归属于 my_web 后端，前端登录成功后访问同一服务，避免 iframe 或第二套应用造成状态割裂。
 * 当前使用内存仓储保证开发环境开箱即用；生产环境可替换为 JPA Repository。
 */
@RestController
@RequestMapping("/api/investment")
@CrossOrigin(origins = "http://localhost:5173")
public class InvestmentController {
    private final AtomicLong ids = new AtomicLong(2);
    private final Map<Long, ResearchProfile> research = new ConcurrentHashMap<>();
    private final Map<Long, Valuation> valuations = new ConcurrentHashMap<>();
    private final Map<Long, LogicRecord> logic = new ConcurrentHashMap<>();

    public InvestmentController() {
        research.put(1L, new ResearchProfile(1, "300750.SZ", "宁德时代", "电力设备", "产能与研发投入形成长期竞争壁垒。", "researching", Instant.now().toString()));
        research.put(2L, new ResearchProfile(2, "600519.SH", "贵州茅台", "食品饮料", "品牌定价权与渠道健康度是核心观察变量。", "watching", Instant.now().toString()));
    }

    @GetMapping("/research")
    public List<ResearchProfile> listResearch() {
        return research.values().stream().sorted(Comparator.comparing(ResearchProfile::id)).toList();
    }

    @PostMapping("/research")
    public ResearchProfile saveResearch(@Valid @RequestBody ResearchRequest req) {
        long id = req.id() == null ? ids.incrementAndGet() : req.id();
        var p = new ResearchProfile(id, req.ticker(), req.companyName(), req.sector() == null ? "未分类" : req.sector(), req.thesis() == null ? "" : req.thesis(), req.status() == null ? "researching" : req.status(), Instant.now().toString());
        research.put(id, p);
        return p;
    }

    @DeleteMapping("/research/{id}")
    public Map<String, Object> deleteResearch(@PathVariable long id) {
        research.remove(id);
        return Map.of("success", true);
    }

    @GetMapping("/valuations")
    public List<Valuation> listValuations() {
        return valuations.values().stream().sorted(Comparator.comparing(Valuation::id).reversed()).toList();
    }

    @PostMapping("/valuations")
    public Valuation saveValuation(@RequestBody ValuationRequest req) {
        long id = req.id() == null ? ids.incrementAndGet() : req.id();
        var v = new Valuation(id, req.researchId(), req.model(), req.name(), req.assumptions(), req.output(), Instant.now().toString());
        valuations.put(id, v);
        return v;
    }

    @DeleteMapping("/valuations/{id}")
    public Map<String, Object> deleteValuation(@PathVariable long id) {
        valuations.remove(id);
        return Map.of("success", true);
    }

    @GetMapping("/logic")
    public List<LogicRecord> listLogic() {
        return logic.values().stream().sorted(Comparator.comparing(LogicRecord::id).reversed()).toList();
    }

    @PostMapping("/logic")
    public LogicRecord saveLogic(@RequestBody LogicRequest req) {
        long id = req.id() == null ? ids.incrementAndGet() : req.id();
        var r = new LogicRecord(id, req.researchId(), req.ticker(), req.title(), req.bullCase(), req.riskCase(), req.linkedMetrics(), req.valuationNote(), req.sentiment() == null ? 50 : req.sentiment(), req.decision() == null ? "watch" : req.decision(), Instant.now().toString());
        logic.put(id, r);
        return r;
    }

    @DeleteMapping("/logic/{id}")
    public Map<String, Object> deleteLogic(@PathVariable long id) {
        logic.remove(id);
        return Map.of("success", true);
    }

    @GetMapping("/macro")
    public Map<String, Object> macro() {
        return Map.of("status", "example", "updatedAt", Instant.now().toString(), "metrics", List.of(Map.of("name", "GDP 增速", "value", "5.0%"), Map.of("name", "社融增速", "value", "8.4%")));
    }

    public record ResearchRequest(Long id, @NotBlank String ticker, @NotBlank String companyName, String sector,
                                  String thesis, String status) {
    }

    public record ValuationRequest(Long id, Long researchId, String model, String name, String assumptions,
                                   String output) {
    }

    public record LogicRequest(Long id, Long researchId, String ticker, String title, String bullCase, String riskCase,
                               String linkedMetrics, String valuationNote, Integer sentiment, String decision) {
    }

    public record ResearchProfile(long id, String ticker, String companyName, String sector, String thesis,
                                  String status, String updatedAt) {
    }

    public record Valuation(long id, Long researchId, String model, String name, String assumptions, String output,
                            String updatedAt) {
    }

    public record LogicRecord(long id, Long researchId, String ticker, String title, String bullCase, String riskCase,
                              String linkedMetrics, String valuationNote, int sentiment, String decision,
                              String recordAt) {
    }
}
