/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.sy99;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.web.ResponseEntry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Berlin
 */
@RestController
@RequestMapping("/sy99")
public class SY99 {

    @PostMapping("/deleteBatch")
    public ResponseEntry deleteBatch(@RequestBody final List<Long> ids) {
        return ResponseEntry.SUCCESS;
    }

    @PostMapping("/save")
    public ResponseEntry save(@RequestBody final ObjectNode body) {
        return ResponseEntry.SUCCESS;
    }

    @PostMapping("/import")
    public ResponseEntry fileUpload(@RequestParam("file") MultipartFile multipart) {
        return ResponseEntry.SUCCESS;
    }
}
