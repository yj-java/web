package org.yj.web.es.controller;

import cn.hutool.core.util.IdUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yj.web.common.Result;
import org.yj.web.es.DocumentEntity;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private ElasticsearchClient esClient;

    @PostMapping("/addDocument")
    public Result addDocument(DocumentEntity documentEntity) throws IOException {
        long snowflakeNextId = IdUtil.getSnowflakeNextId();
        documentEntity.setId(snowflakeNextId);
        CreateResponse client = esClient.create(e -> e.index("client").id(snowflakeNextId + "").document(documentEntity));
        return Result.success(client);
    }

    @GetMapping("/deleteDocument")
    public Result deleteDocument(String id) throws IOException {
        DeleteResponse client = esClient.delete(x -> x.index("client").id(id));
        return Result.success(client);
    }

    @PostMapping("/updateDocument")
    public Result updateDocument(DocumentEntity documentEntity) throws IOException {
        UpdateResponse<DocumentEntity> update = esClient.update(x -> x.index("client").id(documentEntity.getId().toString()).doc(documentEntity), DocumentEntity.class);
        return Result.success(update);
    }

    @GetMapping("/getDocument")
    public Result getDocument(DocumentEntity query) throws IOException {
        /*
         * Term Queries: 用于精确匹配某个字段的特定值，如.term(t -> t.field("status").value("published"))。
         * Match Queries: 如上面例子所示，用于全文匹配，支持模糊匹配。
         * Bool Queries: 构建布尔组合查询，可以包含must（必须匹配）、should（应该匹配）、must_not（不能匹配）等子句，例如.bool(b -> b.must(m1).filter(f1))。
         * Range Queries: 用于筛选字段值在一定范围内的文档，如.range(r -> r.field("age").gte(18).lte(30))。
         * Exists Query: 检查字段是否存在，如.exists(e -> e.field("email"))。
         * Term Range Queries: 类似于Range Query，但用于精确的词项范围，如.termRange(tr -> tr.field("price").gte(JsonData.of(100)).lte(JsonData.of(200)))。
         * Match All Query: 匹配所有文档，相当于不做任何过滤，如.matchAll()。
         */
        SearchResponse<DocumentEntity> response = esClient.search(sr -> sr.index("client") // 指定索引名
                .query(q -> q.match(t -> t.field("name").query(query.getName()))), DocumentEntity.class); // 指定结果类型

        List<DocumentEntity> search = response.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
        return Result.success(search);
    }
}
