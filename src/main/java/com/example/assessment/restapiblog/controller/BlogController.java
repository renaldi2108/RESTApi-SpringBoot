package com.example.assessment.restapiblog.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assessment.restapiblog.data.model.Content;
import com.example.assessment.restapiblog.data.response.BaseResponse;
import com.example.assessment.restapiblog.repository.BlogRepository;

@RestController
@RequestMapping("/api/v1/blog-post")
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping
    public Map<String, Object> getAllBlogPost(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "asc") String sortBy) {
        Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortBy), "id");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Content> pageRes = blogRepository.findAll(pageable);

        return BaseResponse.createInstance().build(true, "success", pageRes.getContent(), 0);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getBlogPostById(@PathVariable Long id) {
        Optional<Content> data = blogRepository.findById(id);

        if(data.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()).getBody()!=null){
            return BaseResponse.createInstance().build(true, "success", data.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build()).getBody(), 0);
        } else {
            return BaseResponse.createInstance().build(false, "data not found", null, 1);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createBlogPost(@RequestBody Content data) {
        return BaseResponse.createInstance().build(blogRepository.save(data)!=null, "success", null, 1);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateBlogPost(@PathVariable Long id, @RequestBody Content data) {
        Optional<Content> existingBlogPost = blogRepository.findById(id);
        
        if (existingBlogPost.isPresent()) {
            data.setId(id);
            blogRepository.save(data);
            return BaseResponse.createInstance().build(true, "success", null, 1);
        } else {
            return BaseResponse.createInstance().build(false, "data not found", null, 1);
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteBlogPost(@PathVariable Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return BaseResponse.createInstance().build(true, "success", null, 1);
        } else {
            return BaseResponse.createInstance().build(false, "data not found", null, 1);
        }
    }
}