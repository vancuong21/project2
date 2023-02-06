package jmaster.io.project2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


// cách khác : ko dùng anotation
@RestController
@RequestMapping("/api/cache")
public class RestCacheController {
    @Autowired
    CacheManager cacheManager;

    @GetMapping("/")
    public List<String> getCache() {
        List<String> cacheNames = cacheManager.getCacheNames().stream().collect(Collectors.toList());
        return cacheNames;
    }

    @DeleteMapping("/{name}")
    public void deleteCache(@PathVariable("name") String name) {
        Cache cache = cacheManager.getCache(name);
        if (cache != null)
            cache.clear();
    }
}
