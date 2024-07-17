package com.egscapekr.user.controller;

import com.egscapekr.user.entity.Brand;
import com.egscapekr.user.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/brand")
public class BrandController {
    // /brand/about : Link format that non-login users also allow
    // /brand/~~ : Link format allowed only by logged-in users

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * API to perform game search.
     */
    @GetMapping("/about/search")
    public ResponseEntity<List<Brand>> Search(@RequestParam("keyword") String keyword) {
        List<Brand> brands = brandService.Search(keyword);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }
}
